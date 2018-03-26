package com.kollect.etl.util;

import java.io.IOException;
import java.util.List;

public interface IRecordDispenser<T> {

  public static final String ALL_LINES = "lines";
  public static final String COMMITS_FAILED = "commitFails";
  public static final String UNKNOWN_FAILED_LINES = "unknownFailed";
  public static final String DATA_FAILED_LINES = "dataFailed";
  public static final String PARSE_FAILED_LINES = "parseFailed";
  public static final String FILTER_ON_FAILED_LINES = "filterOnFailed";
  public static final String CAPTURED_EXCEPTIONS = "capturedExceptions";
  public static final String FAILED_RECORDS = "failedRecords";
  public static final String DB_REQUEST_TOO_LONG = "dbRequestTooLong";

  /**
   * @param fillMe
   * @return returns the first row number of the records that are being filled in
   * @throws Exception
   */
  public int dispenseRecords(List<T> fillMe) throws IOException;

  public int getCurrentRecordNo();

  public String getSourceName();

  public String getType();

  public void terminate();
}