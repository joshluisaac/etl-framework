package com.kollect.etl.query.processor;

import com.kollect.etl.dataaccess.AbstractSqlSessionProvider;

public class QueryContext {
  
  IQueryStrategy iQueryStrategy;
  String batchName;
  
  public QueryContext(IQueryStrategy iQueryStrategy, String batchName){
    this.iQueryStrategy = iQueryStrategy;
    this.batchName = batchName;
  }
  
  public void executeStrategyQuery(){
    Object objParams = iQueryStrategy.getObjectParams();
    String dbSources = iQueryStrategy.getDbSources();
    String[] dbList = iQueryStrategy.getDbList(dbSources);
    String queryNames = iQueryStrategy.getQueryNames(batchName);
    String[] queryList = iQueryStrategy.getQueryNameList(queryNames);
    for (int i = 0; i < queryList.length; i++) {
      for (int j = 0; j < dbList.length; j++) {
        this.iQueryStrategy.execute(new AbstractSqlSessionProvider(dbList[j]), queryList[i], objParams);
      }
    }
  }

}





