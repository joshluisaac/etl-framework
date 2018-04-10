package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.DaoFactory;
import com.kollect.etl.dataaccess.DaoProvider;



@Service
public class ReadWriteServiceProvider implements IReadWriteServiceProvider {
  
  private static final Logger LOG = LoggerFactory.getLogger(ReadWriteServiceProvider.class);
  private DaoProvider dao;
  private DaoFactory d;
  private String defaultDataSource;
  

  @Autowired
  public ReadWriteServiceProvider(DaoProvider dao, DaoFactory d, @Value("${app.datasource}") String dataSource) {
    this.dao = dao;
    this.d = d;
    this.defaultDataSource = dataSource;
    LOG.info("Connected to default database {}",defaultDataSource);
    LOG.info("{}",d.hashCode());
  }
  
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#getBatchSqlSession()
   */
  @Override
  public SqlSession getBatchSqlSession() {
    
    //return dao.getBatchSqlSession();
    return getBatchSqlSession(defaultDataSource);
  }
  
  
  @Override
  public SqlSession getBatchSqlSession(String source) {
    
    //return dao.getBatchSqlSession();
    return d.getDaoSource(source).getBatchSqlSession();
  }
  
  
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#updateQuery(java.lang.String, java.lang.Object)
   */
  @Override
  public int updateQuery(final String queryName, Object args){
    return updateQuery(defaultDataSource, queryName, args);

  }
  
  

  @Override
  public int updateQuery(String source, String queryName, Object args) {
    try {
      //return this.dao.updateQuery(queryName,args);
      return d.getDaoSource(source).updateQuery(queryName,args);
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
    return insertQuery(defaultDataSource, queryName, args);
  }

  @Override
  public int insertQuery(String source, String queryName, Object args) {
    try {
      //return this.dao.insertQuery(queryName,args);
      return d.getDaoSource(source).insertQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  public <T> List<T> executeQuery(final String queryName, Object args){
    return executeQuery(defaultDataSource, queryName, args);
  }
  
  
  /* (non-Javadoc)
   * @see com.kollect.etl.service.IReadWriteServiceProvider#executeQuery(java.lang.String, java.lang.Object)
   */
  @Override
  public <T> List<T> executeQuery(final String source, final String queryName, Object args){
    try {
      //return this.dao.executeQuery(queryName,args);
      return d.getDaoSource(source).executeQuery(queryName,args);
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
    return executeQueryItr(defaultDataSource, queryName, args);
  }
  
  @Override
  public <T> Iterator<T> executeQueryItr(String source, String queryName, Object args) {
    try {
      //return this.dao.executeQueryItr(queryName,args);
      return d.getDaoSource(source).executeQueryItr(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  
  

}
