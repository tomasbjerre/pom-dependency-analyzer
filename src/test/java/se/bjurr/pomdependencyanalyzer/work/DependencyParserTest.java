package se.bjurr.pomdependencyanalyzer.work;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import se.bjurr.pomdependencyanalyzer.work.graph.DependencyParser;

public class DependencyParserTest {

  @Test
  public void testWithClassifier() {
    final DependencyParser withClassifier =
        new DependencyParser("org.sonatype.sisu:sisu-guice:jar:no_aop:3.1.6:provided");
    assertThat(withClassifier.getDependency().getGroupId()).isEqualTo("org.sonatype.sisu");
    assertThat(withClassifier.getDependency().getArtifactId()).isEqualTo("sisu-guice");
    assertThat(withClassifier.getDependency().getVersion()).isEqualTo("3.1.6");
    assertThat(withClassifier.getDependency().getType()).isEqualTo("jar");
    assertThat(withClassifier.getDependency().findClassifier().get()).isEqualTo("no_aop");
    assertThat(withClassifier.getDependency().getScope()).isEqualTo("provided");

    assertThat(withClassifier.toString())
        .isEqualTo(
            "DependencyParser [dependency=Dependency [groupId=org.sonatype.sisu, artifactId=sisu-guice, version=3.1.6, classifier=no_aop, type=jar, scope=provided]]");
  }

  @Test
  public void testWithoutClassifier() {
    final DependencyParser withClassifier =
        new DependencyParser("org.slf4j:slf4j-api:jar:1.6.2:provided");
    assertThat(withClassifier.getDependency().getGroupId()).isEqualTo("org.slf4j");
    assertThat(withClassifier.getDependency().getArtifactId()).isEqualTo("slf4j-api");
    assertThat(withClassifier.getDependency().getVersion()).isEqualTo("1.6.2");
    assertThat(withClassifier.getDependency().getType()).isEqualTo("jar");
    assertThat(withClassifier.getDependency().findClassifier().orElse(null)).isEqualTo(null);
    assertThat(withClassifier.getDependency().getScope()).isEqualTo("provided");
  }

  @Test
  public void testWithoutScope() {
    final DependencyParser withClassifier =
        new DependencyParser("org.eclipse.aether:aether-impl:jar:1.0.2.v20150114");
    assertThat(withClassifier.getDependency().getGroupId()).isEqualTo("org.eclipse.aether");
    assertThat(withClassifier.getDependency().getArtifactId()).isEqualTo("aether-impl");
    assertThat(withClassifier.getDependency().getVersion()).isEqualTo("1.0.2.v20150114");
    assertThat(withClassifier.getDependency().getType()).isEqualTo("jar");
    assertThat(withClassifier.getDependency().findClassifier().orElse(null)).isEqualTo(null);
    assertThat(withClassifier.getDependency().getScope()).isEqualTo("compile");
  }
}
