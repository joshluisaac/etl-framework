package com.powerapps.service.bean;

public class ExtractionMetric implements IExtractionMetric {
  
  private String dbName, fileName;
  private long queryRunningTime;
  private long writingRunningTime;
  private long bytesWritten;
  private long recordCount;
  
  public ExtractionMetric(String dbName, String fileName, long queryRunningTime, long writingRunningTime, long bytesWritten,
      long recordCount) {
    super();
    this.dbName = dbName;
    this.fileName = fileName;
    this.queryRunningTime = queryRunningTime;
    this.writingRunningTime = writingRunningTime;
    this.bytesWritten = bytesWritten;
    this.recordCount = recordCount;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getDbName()
   */
  @Override
  public String getDbName() {
    return dbName;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setDbName(java.lang.String)
   */
  @Override
  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getFileName()
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setFileName(java.lang.String)
   */
  @Override
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getQueryRunningTime()
   */
  @Override
  public long getQueryRunningTime() {
    return queryRunningTime;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setQueryRunningTime(int)
   */
  @Override
  public void setQueryRunningTime(int queryRunningTime) {
    this.queryRunningTime = queryRunningTime;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getWritingRunningTime()
   */
  @Override
  public long getWritingRunningTime() {
    return writingRunningTime;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setWritingRunningTime(int)
   */
  @Override
  public void setWritingRunningTime(int writingRunningTime) {
    this.writingRunningTime = writingRunningTime;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getBytesWritten()
   */
  @Override
  public long getBytesWritten() {
    return bytesWritten;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setBytesWritten(int)
   */
  @Override
  public void setBytesWritten(int bytesWritten) {
    this.bytesWritten = bytesWritten;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#getRecordCount()
   */
  @Override
  public long getRecordCount() {
    return recordCount;
  }

  /* (non-Javadoc)
   * @see com.powerapps.com.kollect.etl.service.bean.IExtractionMetric#setRecordCount(int)
   */
  @Override
  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }

  @Override
  public String toString() {
    return "ExtractionMetric [dbName=" + dbName + ", fileName=" + fileName + ", queryRunningTime=" + queryRunningTime
        + ", writingRunningTime=" + writingRunningTime + ", bytesWritten=" + bytesWritten + ", recordCount="
        + recordCount + "]";
  }
  
  
  
  
  

}
