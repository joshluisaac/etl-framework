package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.util.LogStats;

public abstract class AbstractAsyncExecutorService implements IAsyncExecutorService {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractAsyncExecutorService.class);

  IReadWriteServiceProvider rwProvider;
  IAsyncBatchService asyncService;

  public AbstractAsyncExecutorService(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService) {
    this.rwProvider = rwProvider;
    this.asyncService = asyncService;
  }

  
  @Override
  public <T> void invoke(final String src, List<T> list, final List<String> sqlQuery, final int thread, final int commitSize) {
    Iterator<T> itr = list.iterator();
    asyncService.execute(itr, new IRunnableProcess<T>() {
      @Override
      public void process(final List<T> threadData) {
        long queryStart = System.currentTimeMillis();
        try (final SqlSession sqlSession = rwProvider.getBatchSqlSession(src);) {
          for (int i = 0; i < threadData.size(); i++) {
            for (String query : sqlQuery) {
              sqlSession.update(query, threadData.get(i));
            }
          }
          sqlSession.commit();
          long queryEnd = System.currentTimeMillis();
          LogStats.logQueryStatistics("parallelStream", sqlQuery.get(0), queryStart, queryEnd);
        } catch (PersistenceException persEx) {
          LOG.error("Failed to execute update statement: {}", sqlQuery.get(0), persEx.getCause());
          throw persEx;
        }
      }
    }, thread, commitSize);
  }

  @Override
  public <T> void processEntries(final Map<String, CrudProcessHolder> map, final List<T> list) {
    for (Map.Entry<String, CrudProcessHolder> entry : map.entrySet()) {
      CrudProcessHolder holder = entry.getValue();
      int recordCount = list.size();
      if (recordCount > 0)
        invoke(holder.getDataSource(), list, holder.getChildQuery(), holder.getThread(), holder.getCommitSize());
      holder.setRecordCount(recordCount);
    }
  }
  
}
