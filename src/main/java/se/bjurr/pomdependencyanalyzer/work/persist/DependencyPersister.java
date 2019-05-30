package se.bjurr.pomdependencyanalyzer.work.persist;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import se.bjurr.pomdependencyanalyzer.data.Dependency;
import se.bjurr.pomdependencyanalyzer.data.Metadata;
import se.bjurr.pomdependencyanalyzer.work.graph.ResolvedDependencies;

public class DependencyPersister {
  private static final String DEPENDENTS_JSON = "dependents.json";
  private static final String METADATA_JSON = "metadata.json";
  private static final String DEPENDENCIES_JSON = "dependencies.json";
  private static final String PARSED_JSON = "parsed.json";

  private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  private final ResolvedDependencies resolvedDependencies;
  private final List<Metadata> metadataList;
  private final String storageFolder;

  public DependencyPersister(
      final ResolvedDependencies resolvedDependencies,
      final String storageFolder,
      final List<Metadata> metadataList) {
    this.resolvedDependencies = resolvedDependencies;
    this.metadataList = metadataList;
    this.storageFolder = storageFolder;
  }

  public void persist() {
    final Path folderPath = setupFolderPath(resolvedDependencies.getParsed());

    write(GSON.toJson(metadataList), folderPath, METADATA_JSON);
    write(GSON.toJson(resolvedDependencies.getParsed()), folderPath, PARSED_JSON);

    if (!resolvedDependencies.getParsed().getVersion().endsWith("-SNAPSHOT")
        && folderPath.resolve(DEPENDENCIES_JSON).toFile().exists()) {
      // Assuming only SNAPSHOT versions change
      return;
    }

    final List<Dependency> prevDependencies = getDependencies(folderPath, DEPENDENCIES_JSON);
    prevDependencies.removeAll(resolvedDependencies.getDependencies());
    for (final Dependency dep : prevDependencies) {
      removeDependent(dep, resolvedDependencies.getParsed());
    }

    write(GSON.toJson(resolvedDependencies.getDependencies()), folderPath, DEPENDENCIES_JSON);

    for (final Dependency dep : resolvedDependencies.getDependencies()) {
      updateDependents(dep, resolvedDependencies.getParsed());
    }
  }

  private void updateDependents(final Dependency dep, final Dependency parsed) {
    final Path depFolderPath = setupFolderPath(dep);
    final List<Dependency> dependents = getDependencies(depFolderPath, DEPENDENTS_JSON);
    if (dependents.contains(parsed)) {
      return;
    }
    dependents.add(parsed);
    write(GSON.toJson(dependents), depFolderPath, DEPENDENTS_JSON);
    if (!depFolderPath.resolve(PARSED_JSON).toFile().exists()) {
      // So that we can always look at parsed.json to get the details, for any artifact.
      write(GSON.toJson(dep), depFolderPath, PARSED_JSON);
    }
    if (!depFolderPath.resolve(DEPENDENCIES_JSON).toFile().exists()) {
      // So that we can always look at dependencies.
      write(GSON.toJson(new ArrayList<>()), depFolderPath, DEPENDENCIES_JSON);
    }
  }

  private void removeDependent(final Dependency dep, final Dependency parsed) {
    final Path depFolderPath = setupFolderPath(dep);
    final List<Dependency> dependents = getDependencies(depFolderPath, DEPENDENTS_JSON);
    dependents.remove(parsed);
    write(GSON.toJson(dependents), depFolderPath, DEPENDENTS_JSON);
  }

  private List<Dependency> getDependencies(
      final Path depFolderPath, final String depententsFilename) {
    try {
      final Path dependentsFile = depFolderPath.resolve(depententsFilename);
      List<Dependency> dependents = new ArrayList<Dependency>();
      if (dependentsFile.toFile().exists()) {
        final String dependentsJson = Files.toString(dependentsFile.toFile(), UTF_8);
        dependents = GSON.fromJson(dependentsJson, new TypeToken<List<Dependency>>() {}.getType());
      }
      return dependents;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Path setupFolderPath(final Dependency parsed) {
    final Path folderPath =
        Paths.get(
            storageFolder
                + "/"
                + parsed.getGroupId().replace(".", "/")
                + "/"
                + parsed.getArtifactId()
                + "/"
                + parsed.getVersion());

    final File folderFile = folderPath.toFile();
    // System.out.println("Storing in " + folderFile.getAbsolutePath());
    folderFile.mkdirs();
    return folderPath;
  }

  private void write(final String metadataJson, final Path folderPath, final String filename) {
    try {
      final File file = folderPath.resolve(filename).toFile();
      System.out.println("Writing: " + file.getAbsolutePath());
      Files.write(metadataJson.getBytes(UTF_8), file);
    } catch (final IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
