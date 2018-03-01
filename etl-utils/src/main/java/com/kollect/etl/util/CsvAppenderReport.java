package com.kollect.etl.util;

public class CsvAppenderReport implements ICsvAppenderReport {

  String fileName;
  long fileCount;
  long recordCount;
  long numberOfBytes;
  
  
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#getFileName()
   */
  @Override
  public String getFileName() {
    return fileName;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#setFileName(java.lang.String)
   */
  @Override
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#getFileCount()
   */
  @Override
  public long getFileCount() {
    return fileCount;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#setFileCount(int)
   */
  @Override
  public void setFileCount(long fileCount) {
    this.fileCount = fileCount;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#getRecordCount()
   */
  @Override
  public long getRecordCount() {
    return recordCount;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#setRecordCount(int)
   */
  @Override
  public void setRecordCount(long recordCount) {
    this.recordCount = recordCount;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#getNumberOfBytes()
   */
  @Override
  public long getNumberOfBytes() {
    return numberOfBytes;
  }
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#setNumberOfBytes(int)
   */
  @Override
  public void setNumberOfBytes(long numberOfBytes) {
    this.numberOfBytes = numberOfBytes;
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.util.ICsvFileAppenderStats#toString()
   */
  @Override
  public String toString() {
    return "CsvFileAppenderStats [fileName=" + fileName + ", fileCount=" + fileCount + ", recordCount=" + recordCount
        + ", numberOfBytes=" + numberOfBytes + "]";
  }
  
  
  
  

}
