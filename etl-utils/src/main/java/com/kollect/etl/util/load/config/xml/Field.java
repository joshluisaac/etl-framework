package com.kollect.etl.util.load.config.xml;

public class Field {

  boolean isExternal;
  boolean isKey;
  boolean isOptional;
  String name;
  int defaultValue;
  int locationStart;
  int locationEnd;
  String handlerName;
  boolean isLookUp;
  String lookUpQuery;

  public Field(boolean isExternal, boolean isKey, boolean isOptional, String name, int defaultValue, int locationStart,
      int locationEnd, String handlerName, boolean isLookUp, String lookUpQuery) {

    this.isExternal = false;
    this.isKey = false;
    this.isOptional = false;
    this.name = name;
    this.defaultValue = defaultValue;
    this.locationStart = locationStart;
    this.locationEnd = locationEnd;
    this.handlerName = handlerName;
    this.isLookUp = isLookUp;
    this.lookUpQuery = lookUpQuery;

  }

  /**
   * @return the isExternal
   */
  public boolean isExternal() {
    return isExternal;
  }

  /**
   * @return the isKey
   */
  public boolean isKey() {
    return isKey;
  }

  /**
   * @return the isOptional
   */
  public boolean isOptional() {
    return isOptional;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the defaultValue
   */
  public int getDefaultValue() {
    return defaultValue;
  }

  /**
   * @return the locationStart
   */
  public int getLocationStart() {
    return locationStart;
  }

  /**
   * @return the locationEnd
   */
  public int getLocationEnd() {
    return locationEnd;
  }

  /**
   * @return the handlerName
   */
  public String getHandlerName() {
    return handlerName;
  }

  /**
   * @return the isLookUp
   */
  public boolean isLookUp() {
    return isLookUp;
  }

  /**
   * @return the lookUpQuery
   */
  public String getLookUpQuery() {
    return lookUpQuery;
  }

}
