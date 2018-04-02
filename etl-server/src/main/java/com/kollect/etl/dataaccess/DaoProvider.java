package com.kollect.etl.dataaccess;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class DaoProvider {
  
  private IAbstractSqlSessionProvider sqlSessionProvider;

  public DaoProvider(@Value("${app.datasource}") String dataSource) {
      sqlSessionProvider = new AbstractSqlSessionProvider(dataSource);
  }
  
  public SqlSession getBatchSqlSession() {
    return sqlSessionProvider.getBatchExecutionSqlSession();
  }
  
  public int insertQuery (final String queryName, Object object) throws PersistenceException {
    return sqlSessionProvider.insert(queryName, object);
}
  
  public int updateQuery (final String queryName, Object object) throws PersistenceException {
    return sqlSessionProvider.update(queryName, object);
}
  
  public <T> List<T> executeQuery(final String queryName, Object args)  throws PersistenceException {
    return sqlSessionProvider.queryObject(queryName, args);
  }
  
  public <T> Iterator<T> executeQueryItr(final String queryName, Object args) throws PersistenceException {
    return sqlSessionProvider.query(queryName, args);
  }
  

}
