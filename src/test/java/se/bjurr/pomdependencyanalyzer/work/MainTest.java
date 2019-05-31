package se.bjurr.pomdependencyanalyzer.work;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.bjurr.pomdependencyanalyzer.data.Dependency;
import se.bjurr.pomdependencyanalyzer.main.Main;

public class MainTest {
  private Path tempDirWithPrefix;

  @Before
  public void before() throws IOException {
    tempDirWithPrefix = Files.createTempDirectory("tempfolder");
  }

  @After
  public void after() throws IOException {
    Files.walk(tempDirWithPrefix)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
  }

  @Test
  public void testHelp() throws Exception {
    final String[] args = {
      "-h" //
    };
    Main.main(args);
  }

  // @Test
  public void testDot() throws Exception {
    final String[] args = {
      "-d",
      "/home/bjerre/.m2/repository/se/bjurr/violations/violations-git-lib/1.18/violations-git-lib-1.18.pom.dot" //
    };
    Main.main(args);
  }

  @Test
  public void testRunWithMain() throws Exception {
    final URL resource = MainTest.class.getResource("/aether-impl-1.0.2.v20150114.pom.dot");
    final String dotFile = resource.getFile();

    final String[] args = {
      "-sf",
      tempDirWithPrefix.toFile().getAbsolutePath(),
      "-d",
      dotFile, //
      "-ci",
      "true", //
      "-md",
      "gitrepo",
      "ssh://git/repo.git", //
      "-md",
      "jenkinsJob",
      "jobs/folder/pipeline" //
    };
    Main.main(args);
  }

  @Test
  public void testRunWithMain2() throws Exception {
    final URL resource = MainTest.class.getResource("/violation-comments-lib-1.87.pom.dot");
    final String dotFile = resource.getFile();

    final String[] args = {
      "-sf",
      tempDirWithPrefix.toFile().getAbsolutePath(),
      "-d",
      dotFile, //
      "-ci",
      "true" //
    };
    Main.main(args);
    final String dependenciesFile =
        tempDirWithPrefix.toFile().getAbsolutePath()
            + "/se/bjurr/violations/violation-comments-lib/1.87/dependencies.json";
    final byte[] bytes = Files.readAllBytes(Paths.get(dependenciesFile));
    final String content = new String(bytes, StandardCharsets.UTF_8);
    final List<Dependency> list =
        new Gson().fromJson(content, new TypeToken<ArrayList<Dependency>>() {}.getType());
    assertThat(list).hasSize(3);
  }
}
