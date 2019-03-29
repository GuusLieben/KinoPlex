package nl.avans.kinoplex.domain;

import java.util.Map;

public abstract class DomainObject {

  public abstract String getId();

  public abstract Map<String, Object> storeToMap();
}
