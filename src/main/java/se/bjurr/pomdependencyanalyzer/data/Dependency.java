package se.bjurr.pomdependencyanalyzer.data;

import java.util.Optional;

public class Dependency implements Comparable<Dependency> {

  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String classifier;
  private final String type;
  private final String scope;

  public Dependency(
      final String groupId,
      final String artifactId,
      final String version,
      final String classifier,
      final String type,
      final String scope) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.classifier = classifier;
    this.type = type;
    this.scope = scope;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public Optional<String> findClassifier() {
    return Optional.ofNullable(classifier);
  }

  public String getGroupId() {
    return groupId;
  }

  public String getScope() {
    return scope;
  }

  public String getType() {
    return type;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (artifactId == null ? 0 : artifactId.hashCode());
    result = prime * result + (classifier == null ? 0 : classifier.hashCode());
    result = prime * result + (groupId == null ? 0 : groupId.hashCode());
    result = prime * result + (scope == null ? 0 : scope.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
    result = prime * result + (version == null ? 0 : version.hashCode());
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
    final Dependency other = (Dependency) obj;
    if (artifactId == null) {
      if (other.artifactId != null) {
        return false;
      }
    } else if (!artifactId.equals(other.artifactId)) {
      return false;
    }
    if (classifier == null) {
      if (other.classifier != null) {
        return false;
      }
    } else if (!classifier.equals(other.classifier)) {
      return false;
    }
    if (groupId == null) {
      if (other.groupId != null) {
        return false;
      }
    } else if (!groupId.equals(other.groupId)) {
      return false;
    }
    if (scope == null) {
      if (other.scope != null) {
        return false;
      }
    } else if (!scope.equals(other.scope)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    return true;
  }

  public int hashCodeVertex() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (artifactId == null ? 0 : artifactId.hashCode());
    result = prime * result + (classifier == null ? 0 : classifier.hashCode());
    result = prime * result + (groupId == null ? 0 : groupId.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
    result = prime * result + (version == null ? 0 : version.hashCode());
    return result;
  }

  public boolean equalsVertex(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Dependency other = (Dependency) obj;
    if (artifactId == null) {
      if (other.artifactId != null) {
        return false;
      }
    } else if (!artifactId.equals(other.artifactId)) {
      return false;
    }
    if (classifier == null) {
      if (other.classifier != null) {
        return false;
      }
    } else if (!classifier.equals(other.classifier)) {
      return false;
    }
    if (groupId == null) {
      if (other.groupId != null) {
        return false;
      }
    } else if (!groupId.equals(other.groupId)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Dependency [groupId="
        + groupId
        + ", artifactId="
        + artifactId
        + ", version="
        + version
        + ", classifier="
        + classifier
        + ", type="
        + type
        + ", scope="
        + scope
        + "]";
  }

  @Override
  public int compareTo(final Dependency o) {
    return this.toString().compareTo(o.toString());
  }
}
