package com.kollect.etl.extractor;

import java.util.List;

public class Query<T> {
  
  long queryDiff, writeDiff, numberOfBytes,rowsWritten;
  String queryName;
  List<T> data;
  
  
  public List<T> getData() {
    return data;
  }
  public void setData(List<T> data) {
    this.data = data;
  }
  public long getQueryDiff() {
    return queryDiff;
  }
  public void setQueryDiff(long queryDiff) {
    this.queryDiff = queryDiff;
  }
  public long getWriteDiff() {
    return writeDiff;
  }
  public void setWriteDiff(long writeDiff) {
    this.writeDiff = writeDiff;
  }
  public long getNumberOfBytes() {
    return numberOfBytes;
  }
  public void setNumberOfBytes(long numberOfBytes) {
    this.numberOfBytes = numberOfBytes;
  }
  public long getRowsWritten() {
    return rowsWritten;
  }
  public void setRowsWritten(long rowsWritten) {
    this.rowsWritten = rowsWritten;
  }
  public String getQueryName() {
    return queryName;
  }
  public void setQueryName(String queryName) {
    this.queryName = queryName;
  }
  
}
