package com.kollect.etl.util;

public interface ICsvAppenderReport {

  String getFileName();

  void setFileName(String fileName);

  long getFileCount();

  void setFileCount(long fileCount);

  long getRecordCount();

  void setRecordCount(long recordCount);

  long getNumberOfBytes();

  void setNumberOfBytes(long numberOfBytes);

  String toString();

}