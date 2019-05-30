package se.bjurr.pomdependencyanalyzer.work.graph;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
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

  @Test
  public void testZeroDependenciesCanBeParsed() throws IOException, Throwable {
    final File dotFile =
        new File(
            DependencyGraphTest.class
                .getResource("/java-method-invocation-builder-annotations-1.0.pom.dot")
                .toURI());
    final DependencyGraph dg = new DependencyGraph(dotFile, false);
    final Set<Dependency> graph = dg.getResolvedDependencies().getDependencies();
    final Dependency parsed = dg.getResolvedDependencies().getParsed();
    assertThat(graph).hasSize(0);
    assertThat(parsed).isNull();
  }
}
