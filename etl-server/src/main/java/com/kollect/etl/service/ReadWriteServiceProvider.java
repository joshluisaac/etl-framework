package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.DaoProvider;



@Service
public class ReadWriteServiceProvider implements IReadWriteServiceProvider {
  
  private DaoProvider dao;
  
  @Autowired
  public ReadWriteServiceProvider(DaoProvider dao) {
    this.dao = dao;
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(ReadWriteServiceProvider.class);
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#getBatchSqlSession()
   */
  @Override
  public SqlSession getBatchSqlSession() {
    return dao.getBatchSqlSession();
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#updateQuery(java.lang.String, java.lang.Object)
   */
  @Override
  public int updateQuery(final String queryName, Object args){
    try {
      return this.dao.updateQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#insertQuery(java.lang.String, java.lang.Object)
   */
  @Override
  public int insertQuery(final String queryName, Object args){
    try {
      return this.dao.insertQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#executeQuery(java.lang.String, java.lang.Object)
   */
  @Override
  public <T> List<T> executeQuery(final String queryName, Object args){
    try {
      return this.dao.executeQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#executeQueryItr(java.lang.String, java.lang.Object)
   */
  @Override
  public <T> Iterator<T> executeQueryItr(final String queryName, Object args){
    try {
      return this.dao.executeQueryItr(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  
  

}
