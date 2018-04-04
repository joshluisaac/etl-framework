package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.util.LogStats;

public abstract class AbstractAsyncExecutorService implements IAsyncExecutorService {
  
  private static final Logger LOG = LoggerFactory.getLogger(AbstractAsyncExecutorService.class);
  
  IReadWriteServiceProvider rwProvider;
  IAsyncBatchService asyncService;
  
  
  public AbstractAsyncExecutorService(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService) {
    this.rwProvider = rwProvider; 
    this.asyncService = asyncService;
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IAsyncExecutorService#invoke(java.util.List, java.lang.String, int, int)
   */
  @Override
  public <T> void invoke (List<T> list, final String updateQuery, final int thread, final int commitSize ) {
    Iterator<T> itr = list.iterator();
    asyncService.execute(itr, new IRunnableProcess<TransactionLoad>() {
      @Override
      public void process(List<TransactionLoad> threadData) {
        long queryStart = System.currentTimeMillis();
        try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
          for (int i = 0; i < threadData.size(); i++) {
            sqlSession.update(updateQuery, threadData.get(i));
          }
          sqlSession.commit();
          long queryEnd = System.currentTimeMillis();
          LogStats.logQueryStatistics("parallelStream", updateQuery, queryStart, queryEnd);
        } catch (PersistenceException persEx) {
          LOG.error("Failed to execute update statement: {}", updateQuery, persEx.getCause());
          throw persEx;
        }
      }
    }, thread, commitSize);
  }
  
  
  @Override
  public <T> void cycleAndProcessEntries(final Map<String, CrudProcessHolder> map, final List<T> list) {
    for (Map.Entry<String, CrudProcessHolder> entry : map.entrySet()) {
        CrudProcessHolder holder = entry.getValue();
        final int thread = holder.getThread();
        final int commitSize = holder.getCommitSize();
        final String updateQuery = holder.getChildQuery().get(0);
        int recordCount = list.size();
        if(recordCount > 0) invoke(list, updateQuery, thread, commitSize);
        holder.setRecordCount(recordCount);
    }
}
  
  @Override
  public void cycleAndProcessEntries(final Map<String, CrudProcessHolder> map) {
    for (Map.Entry<String, CrudProcessHolder> entry : map.entrySet()) {
        CrudProcessHolder holder = entry.getValue();
        final int thread = holder.getThread();
        final int commitSize = holder.getCommitSize();
        final String queryName = holder.getQueryName();
        final String updateQuery = holder.getChildQuery().get(0);
        List<TransactionLoad> list = rwProvider.executeQuery(queryName, null);
        int recordCount = list.size();
        if(recordCount > 0) invoke(list, updateQuery, thread, commitSize);
        holder.setRecordCount(recordCount);
    }
}
  
  
  

}
