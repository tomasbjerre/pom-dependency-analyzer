package se.bjurr.pomdependencyanalyzer.work.graph;

import se.bjurr.pomdependencyanalyzer.data.Dependency;

/** The nodes. */
public class DependencyVertex {

  private final Dependency dependency;

  public DependencyVertex(final Dependency dependency) {
    this.dependency = dependency;
  }

  public Dependency getDependency() {
    return dependency;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (dependency == null ? 0 : dependency.hashCodeVertex());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DependencyVertex other = (DependencyVertex) obj;
    if (dependency == null) {
      if (other.dependency != null) {
        return false;
      }
    } else if (!dependency.equalsVertex(other.dependency)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return dependency.getGroupId()
        + "\n:"
        + dependency.getArtifactId()
        + "\n:"
        + dependency.getType()
        + (dependency.findClassifier().isPresent() ? "\n:" + dependency.findClassifier().get() : "")
        + "\n:"
        + dependency.getVersion();
  }
}
