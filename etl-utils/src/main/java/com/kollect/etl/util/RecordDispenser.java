package com.kollect.etl.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class RecordDispenser<T> implements IRecordDispenser<T> {

  private static final Logger LOG = LoggerFactory.getLogger(RecordDispenser.class);

  protected int perRequest;
  protected int currentRecord = 1;
  protected Object mutex = new Object();
  protected Iterator<T> records = null;
  protected String sourceName = "UNKNOWN";

  public int dispenseRecords(final List<T> fillMe) throws IOException {
    return dispenseLinesPrivately(fillMe);
  }

  protected int dispenseLinesPrivately(final List<T> fillMe) throws IOException {
    fillMe.clear();
    if (records == null || !records.hasNext())
      return 0;
    synchronized (mutex) {
      if (records == null || !records.hasNext())
        return 0;
      int startRecord = currentRecord;
      while (records.hasNext() && (currentRecord - startRecord) < perRequest) {
        fillMe.add(records.next());
        currentRecord++;
      }
      if (!records.hasNext())
        records = null;
        LOG.info("Dispensed " + startRecord + " - " + (currentRecord - 1) + " to " + Thread.currentThread().getName());
      return startRecord;
    }
  }

  public String getSourceName() {
    return sourceName;
  }

  public int getCurrentRecordNo() {
    return currentRecord;
  }
}