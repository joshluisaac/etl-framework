package com.kollect.etl.util;

import java.util.Iterator;

public class IteratorRecordDispenser extends RecordDispenser {
  public IteratorRecordDispenser(Iterator i, int lines, String source) {
    records = i;
    perRequest = lines;
    sourceName = source;
  }

  public String getType() {
    return "ITERATOR";
  }

  public void terminate() {
    // Do nothing
  }
}