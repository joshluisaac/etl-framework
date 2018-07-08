package com.kollect.etl.config;


public class RouteItem {
  
  int index;
  String name, description, endPoint;
  boolean isActive;
  
  
  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getEndPoint() {
    return endPoint;
  }
  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }
  public boolean isActive() {
    return isActive;
  }
  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }
  @Override
  public String toString() {
    return "Route [index=" + index + ", name=" + name + ", description=" + description + ", endPoint=" + endPoint
        + ", isActive=" + isActive + "]";
  }
  
  

}
