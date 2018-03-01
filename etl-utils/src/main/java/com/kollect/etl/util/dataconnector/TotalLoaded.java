package com.kollect.etl.util.dataconnector;

public class TotalLoaded {
  
  String key;
  int loaded;
  int eligible;
  int duration;
  int rowsPerSec;
  
  public TotalLoaded(String key, int loaded, int eligible, int duration, int rowsPerSec ) {
    this.key = key;
    this.loaded = loaded;
    this.eligible = eligible;
    this.duration = duration;
    this.rowsPerSec = rowsPerSec;
  }

  public String getKey() {
    return key;
  }

  public int getLoaded() {
    return loaded;
  }

  public int getEligible() {
    return eligible;
  }

  public int getDuration() {
    return duration;
  }

  public int getRowsPerSec() {
    return rowsPerSec;
  }
  


}
