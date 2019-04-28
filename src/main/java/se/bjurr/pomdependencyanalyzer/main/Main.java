package se.bjurr.pomdependencyanalyzer.main;

import static se.softhouse.jargo.Arguments.booleanArgument;
import static se.softhouse.jargo.Arguments.fileArgument;
import static se.softhouse.jargo.Arguments.helpArgument;
import static se.softhouse.jargo.Arguments.stringArgument;
import static se.softhouse.jargo.CommandLineParser.withArguments;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import se.bjurr.pomdependencyanalyzer.data.Metadata;
import se.bjurr.pomdependencyanalyzer.data.PomAnalyzerArguments;
import se.bjurr.pomdependencyanalyzer.work.DependencyAnalyzer;
import se.softhouse.jargo.Argument;
import se.softhouse.jargo.ArgumentException;
import se.softhouse.jargo.ParsedArguments;

public class Main {

  private static final String USER_HOME = "<user home>";

  public static void main(final String args[]) throws Exception {
    final Argument<?> helpArgument = helpArgument("-h", "--help");

    final Argument<String> storageFolderArgument =
        stringArgument("-sf", "--storage-folder") //
            .description("This is where it will store files.") //
            .defaultValue(USER_HOME + "/.m2/repository") //
            .build();
    final Argument<Boolean> createImageArgument =
        booleanArgument("-ci", "--create-image") //
            .description("Create an image showing the result of analysis.") //
            .defaultValue(false) //
            .build();
    final Argument<File> dotFileArgument =
        fileArgument("-d", "--dot") //
            .description(
                "This is the output file from 'mvn dependency:tree -Doutput=file.dot -DoutputType=dot'") //
            .build();
    final Argument<List<List<String>>> metadataArgument =
        stringArgument("-md", "--metadata")
            .description(
                "These key/values will be stored together with the artifact. Can be used to record things like artifacts git repo or artifacts Jenkins job URL.") //
            .arity(2) //
            .repeated() //
            .build();

    try {
      final ParsedArguments arg =
          withArguments(
                  helpArgument,
                  storageFolderArgument,
                  dotFileArgument,
                  createImageArgument,
                  metadataArgument) //
              .parse(args);

      final String storageFolder =
          arg.get(storageFolderArgument).replace(USER_HOME, System.getProperty("user.home"));
      final File dotFile = arg.get(dotFileArgument);
      final boolean createImage = arg.get(createImageArgument);
      final List<Metadata> metadataList =
          arg.get(metadataArgument)
              .stream()
              .map((it) -> new Metadata(it.get(0), it.get(1)))
              .collect(Collectors.toList());

      System.out.println("From: " + dotFile);
      System.out.println("Storage folder: " + storageFolder);
      System.out.println("Crete image: " + createImage);
      System.out.println("Metadata: " + metadataList);

      PomAnalyzerArguments.INSTANCE =
          new PomAnalyzerArguments(dotFile, storageFolder, createImage, metadataList);

      final DependencyAnalyzer pomAnalyzer = new DependencyAnalyzer();

      pomAnalyzer.start();

    } catch (final ArgumentException exception) {
      System.out.println(exception.getMessageAndUsage());
      if (!Arrays.asList(args).contains("-h")) {
        System.exit(1);
      }
    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }
}
