package com.kollect.etl.entity;

public class ExtractionMetric {
  
  String dbName,fileName;
  long queryRunningTime,writingRunningTime,bytesWritten,recordCount;
  
  
  
  
  public ExtractionMetric(String dbName, String fileName, long queryRunningTime, long writingRunningTime,
      long bytesWritten, long recordCount) {
    this.dbName = dbName;
    this.fileName = fileName;
    this.queryRunningTime = queryRunningTime;
    this.writingRunningTime = writingRunningTime;
    this.bytesWritten = bytesWritten;
    this.recordCount = recordCount;
  }
  
  public String getDbName() {
    return dbName;
  }
  public void setDbName(String dbName) {
    this.dbName = dbName;
  }
  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public long getQueryRunningTime() {
    return queryRunningTime;
  }
  public void setQueryRunningTime(long queryRunningTime) {
    this.queryRunningTime = queryRunningTime;
  }
  public long getWritingRunningTime() {
    return writingRunningTime;
  }
  public void setWritingRunningTime(long writingRunningTime) {
    this.writingRunningTime = writingRunningTime;
  }
  public long getBytesWritten() {
    return bytesWritten;
  }
  public void setBytesWritten(long bytesWritten) {
    this.bytesWritten = bytesWritten;
  }
  public long getRecordCount() {
    return recordCount;
  }
  public void setRecordCount(long recordCount) {
    this.recordCount = recordCount;
  }

  @Override
  public String toString() {
    return "ExtractionMetric [dbName=" + dbName + ", fileName=" + fileName + ", queryRunningTime=" + queryRunningTime
        + ", writingRunningTime=" + writingRunningTime + ", bytesWritten=" + bytesWritten + ", recordCount="
        + recordCount + "]";
  }
  
  

}
