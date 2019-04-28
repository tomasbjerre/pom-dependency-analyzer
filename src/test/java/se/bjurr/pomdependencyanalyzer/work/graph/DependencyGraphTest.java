package se.bjurr.pomdependencyanalyzer.work.graph;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.junit.Test;
import se.bjurr.pomdependencyanalyzer.data.Dependency;

public class DependencyGraphTest {

  @Test
  public void testTransitiveProvidedNotIncluded() throws IOException, Throwable {
    final File dotFile =
        new File(
            DependencyGraphTest.class.getResource("/aether-impl-1.0.2.v20150114.pom.dot").toURI());
    final DependencyGraph dg = new DependencyGraph(dotFile, false);
    final Iterator<Dependency> graph = dg.getResolvedDependencies().getDependencies().iterator();
    while (graph.hasNext()) {
      assertThat(graph.next().getArtifactId()).isNotEqualTo("guava");
    }
  }
}
