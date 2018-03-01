package com.kollect.etl.util;

import java.util.List;

public class Composite {
  
  List<String> compositeList;
  int length;
  
  public Composite(List<String> compositeList, int length) {
    this.compositeList = compositeList;
    this.length = length;
  }
  
  public List<String> getCompositeList() {
    return compositeList;
  }
  public void setCompositeList(List<String> compositeList) {
    this.compositeList = compositeList;
  }
  public int getLength() {
    return length;
  }
  public void setLength(int length) {
    this.length = length;
  }
  
  

}
