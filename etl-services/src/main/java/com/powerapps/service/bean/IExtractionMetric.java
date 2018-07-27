package com.powerapps.service.bean;

public interface IExtractionMetric {

  String getDbName();

  void setDbName(String dbName);

  String getFileName();

  void setFileName(String fileName);

  long getQueryRunningTime();

  void setQueryRunningTime(int queryRunningTime);

  long getWritingRunningTime();

  void setWritingRunningTime(int writingRunningTime);

  long getBytesWritten();

  void setBytesWritten(int bytesWritten);

  long getRecordCount();

  void setRecordCount(int recordCount);

}