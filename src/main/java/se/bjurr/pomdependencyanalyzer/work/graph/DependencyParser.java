package se.bjurr.pomdependencyanalyzer.work.graph;

import se.bjurr.pomdependencyanalyzer.data.Dependency;

public class DependencyParser {
  private final Dependency dependency;

  public DependencyParser(final String dependencyString) {
    if (dependencyString == null || dependencyString.isEmpty()) {
      throw new RuntimeException("Incorrect dependency: " + dependencyString);
    }
    try {
      final String[] parts = dependencyString.split(":");
      final String groupId = part(parts, 0, null);
      final String artifactId = part(parts, 1, null);
      final String type = part(parts, 2, null);
      String classifier;
      String version;
      String scope;
      if (parts.length <= 5) {
        classifier = null;
        version = part(parts, 3, null);
        scope = part(parts, 4, "compile");
      } else {
        classifier = part(parts, 3, null);
        version = part(parts, 4, null);
        scope = part(parts, 5, "compile");
      }
      this.dependency = new Dependency(groupId, artifactId, version, classifier, type, scope);
    } catch (final Exception e) {
      throw new RuntimeException("Cannot parse " + dependencyString);
    }
  }

  private String part(final String[] parts, final int index, final String orValue) {
    if (parts.length <= index) {
      if (orValue == null) {
        throw new RuntimeException("Cannot find part " + index);
      } else {
        return orValue;
      }
    }
    return parts[index];
  }

  public Dependency getDependency() {
    return dependency;
  }

  @Override
  public String toString() {
    return "DependencyParser [dependency=" + dependency + "]";
  }
}
