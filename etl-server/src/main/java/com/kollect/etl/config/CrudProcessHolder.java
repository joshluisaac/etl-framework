package com.kollect.etl.config;

import java.util.Map;

public class CrudProcessHolder {
  
  private String queryName;
  private int recordCount;
  private int avgRunningTime;
  private int thread;
  private int commitSize;
  private int updateCount;
  private Map<String, String> childQuery;
  
  public CrudProcessHolder(final String queryName, final int thread, final int commitSize) {
    this.queryName = queryName;
    this.thread = thread;
    this.commitSize = commitSize;
  }

  public String getQueryName() {
    return queryName;
  }

  public void setQueryName(String queryName) {
    this.queryName = queryName;
  }

  public int getRecordCount() {
    return recordCount;
  }

  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }

  public int getAvgRunningTime() {
    return avgRunningTime;
  }

  public void setAvgRunningTime(int avgRunningTime) {
    this.avgRunningTime = avgRunningTime;
  }

  public int getThread() {
    return thread;
  }

  public void setThread(int thread) {
    this.thread = thread;
  }

  public int getCommitSize() {
    return commitSize;
  }

  public void setCommitSize(int commitSize) {
    this.commitSize = commitSize;
  }

  public int getUpdateCount() {
    return updateCount;
  }

  public void setUpdateCount(int updateCount) {
    this.updateCount = updateCount;
  }

  public Map<String, String> getChildQuery() {
    return childQuery;
  }

  public void setChildQuery(Map<String, String> childQuery) {
    this.childQuery = childQuery;
  }

  @Override
  public String toString() {
    return "Holder [queryName=" + queryName + ", recordCount=" + recordCount + ", avgRunningTime=" + avgRunningTime
        + ", thread=" + thread + ", commitSize=" + commitSize + ", updateCount=" + updateCount + ", childQuery="
        + childQuery + "]";
  }
  

  
  
  

}
