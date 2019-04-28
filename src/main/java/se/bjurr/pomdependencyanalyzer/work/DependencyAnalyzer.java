package se.bjurr.pomdependencyanalyzer.work;

import java.util.List;
import se.bjurr.pomdependencyanalyzer.data.Metadata;
import se.bjurr.pomdependencyanalyzer.data.PomAnalyzerArguments;
import se.bjurr.pomdependencyanalyzer.work.graph.DependencyGraph;
import se.bjurr.pomdependencyanalyzer.work.graph.ResolvedDependencies;
import se.bjurr.pomdependencyanalyzer.work.persist.DependencyPersister;

public class DependencyAnalyzer {

  public DependencyAnalyzer() {}

  public void start() throws Throwable {
    final DependencyGraph dependencyGraph =
        new DependencyGraph(
            PomAnalyzerArguments.INSTANCE.getDotFile(),
            PomAnalyzerArguments.INSTANCE.isCreateImage());
    final ResolvedDependencies resolvedDependencies = dependencyGraph.getResolvedDependencies();
    final String storageFolder = PomAnalyzerArguments.INSTANCE.getStorageFolder();
    final List<Metadata> metadataList = PomAnalyzerArguments.INSTANCE.getMetadataList();
    final DependencyPersister persister =
        new DependencyPersister(resolvedDependencies, storageFolder, metadataList);
    persister.persist();
  }
}
