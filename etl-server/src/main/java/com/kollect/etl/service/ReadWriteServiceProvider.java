package com.kollect.etl.service;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.IDaoFactory;

import java.util.Iterator;
import java.util.List;



@Service
public class ReadWriteServiceProvider implements IReadWriteServiceProvider {
  
  private static final Logger LOG = LoggerFactory.getLogger(ReadWriteServiceProvider.class);
  private IDaoFactory daoFactory;
  private String defaultDataSource;
  

  @Autowired
  public ReadWriteServiceProvider(IDaoFactory daoFactory, @Value("${app.datasource_mahb_prod2}") String dataSource) {
    this.daoFactory = daoFactory;
    this.defaultDataSource = dataSource;
    LOG.info("Connected to default database {}",defaultDataSource);
    LOG.info("Hash constr code {}",daoFactory.hashCode());
  }

  
  @Override
  public SqlSession getBatchSqlSession() {
    return getBatchSqlSession(defaultDataSource);
  }
  
  
  @Override
  public SqlSession getBatchSqlSession(String source) {
    return daoFactory.getDaoSource(source).getBatchSqlSession();
  }

  @Override
  public int updateQuery(final String queryName, Object args){
    return updateQuery(defaultDataSource, queryName, args);

  }
  
  

  @Override
  public int updateQuery(String source, String queryName, Object args) {
    try {
      return daoFactory.getDaoSource(source).updateQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }

  @Override
  public int insertQuery(final String queryName, Object args){
    return insertQuery(defaultDataSource, queryName, args);
  }

  @Override
  public int insertQuery(String source, String queryName, Object args) {
    try {
      return daoFactory.getDaoSource(source).insertQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  public <T> List<T> executeQuery(final String queryName, Object args){
    return executeQuery(defaultDataSource, queryName, args);
  }

  @Override
  public <T> List<T> executeQuery(final String source, final String queryName, Object args){
    LOG.info("Hash code {}",daoFactory.hashCode());
    try {
      return daoFactory.getDaoSource(source).executeQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
  
  @Override
  public <T> Iterator<T> executeQueryItr(final String queryName, Object args){
    return executeQueryItr(defaultDataSource, queryName, args);
  }
  
  @Override
  public <T> Iterator<T> executeQueryItr(String source, String queryName, Object args) {
    try {
      return daoFactory.getDaoSource(source).executeQueryItr(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }

}
