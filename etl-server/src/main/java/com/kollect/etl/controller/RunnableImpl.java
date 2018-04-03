package com.kollect.etl.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.IRunnableProcess;
import com.kollect.etl.util.LogStats;

public class RunnableImpl implements IRunnableProcess<TransactionLoad> {
  
  private IReadWriteServiceProvider rwProvider;

  @Override
  public void process(List<TransactionLoad> rows) {
    final String updateQuery = "updateIsInvoiceAndIsCredit";
    
    long queryStart = System.currentTimeMillis();
    try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
      for (int i = 0; i < rows.size(); i++) {
        sqlSession.update(updateQuery, rows.get(i));
      }
      sqlSession.commit();
      long queryEnd = System.currentTimeMillis();
      LogStats.logQueryStatistics("parallelStream", updateQuery, queryStart, queryEnd);
    }
  }

}
