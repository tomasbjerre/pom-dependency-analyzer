package se.bjurr.pomdependencyanalyzer.work.graph;

/** The relation between 2 vertexes. */
public class DependencyEdge {

  /** test,compile,provided... */
  private final String scope;

  public DependencyEdge(final String scope) {
    this.scope = scope;
  }

  public String getScope() {
    return scope;
  }

  @Override
  public String toString() {
    return scope;
  }
}
