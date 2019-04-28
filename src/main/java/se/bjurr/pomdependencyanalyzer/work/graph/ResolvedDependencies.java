package se.bjurr.pomdependencyanalyzer.work.graph;

import java.util.Set;
import se.bjurr.pomdependencyanalyzer.data.Dependency;

public class ResolvedDependencies {

  private final Set<Dependency> dependencies;
  private final Dependency parsed;

  public ResolvedDependencies(final Dependency parsed, final Set<Dependency> dependencies) {
    this.dependencies = dependencies;
    this.parsed = parsed;
  }

  public Set<Dependency> getDependencies() {
    return dependencies;
  }

  public Dependency getParsed() {
    return parsed;
  }

  @Override
  public String toString() {
    return "Dependencies [dependencies=" + dependencies + ", parsed=" + parsed + "]";
  }
}
