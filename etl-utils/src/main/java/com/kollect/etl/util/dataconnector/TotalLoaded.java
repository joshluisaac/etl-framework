package com.kollect.etl.util.dataconnector;

import com.google.common.base.MoreObjects;

public class TotalLoaded {
  
  String key;
  int loaded;
  int eligible;
  int duration;
  int rowsPerSec;
  String context;
  
  double percentageLoaded, percentageRejected;
  
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
  
  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }
  
  

  public double getPercentageLoaded() {
    return percentageLoaded;
  }

  public void setPercentageLoaded() {
    if (this.loaded <= 0) {
      this.percentageLoaded = 0.0;
    }
    else {
      this.percentageLoaded = ((double)this.loaded/this.eligible) * 100;
    }

  }

  public double getPercentageRejected() {
    return percentageRejected;
  }

  public void setPercentageRejected() {
    if ((this.eligible - this.loaded) <= 0) {
      this.percentageLoaded = 0.0;
    } else {
      this.percentageRejected = ((double)(this.eligible - this.loaded)/this.eligible) * 100;
    }

  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("key", key)
            .add("loaded", loaded)
            .add("eligible", eligible)
            .add("duration", duration)
            .add("rowsPerSec", rowsPerSec)
            .add("context", context)
            .add("percentageLoaded", percentageLoaded)
            .add("percentageRejected", percentageRejected)
            .toString();
  }
}
