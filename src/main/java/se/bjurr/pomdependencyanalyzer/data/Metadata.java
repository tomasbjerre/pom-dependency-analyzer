package se.bjurr.pomdependencyanalyzer.data;

public class Metadata {

  private final String key;
  private final String value;

  public Metadata(final String key, final String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Metadata [key=" + key + ", value=" + value + "]";
  }
}
