package se.bjurr.pomdependencyanalyzer.data;

import java.io.File;
import java.util.List;

public class PomAnalyzerArguments {
  public static PomAnalyzerArguments INSTANCE;

  private final File dotFile;
  private final String storageFolder;
  private final boolean createImage;

  private final List<Metadata> metadataList;

  public PomAnalyzerArguments(
      final File dotFile,
      final String storageFolder,
      final boolean createImage,
      final List<Metadata> metadataList) {
    this.dotFile = dotFile;
    this.storageFolder = storageFolder;
    this.createImage = createImage;
    this.metadataList = metadataList;
  }

  public List<Metadata> getMetadataList() {
    return metadataList;
  }

  public boolean isCreateImage() {
    return createImage;
  }

  public String getStorageFolder() {
    return storageFolder;
  }

  public File getDotFile() {
    return dotFile;
  }
}
