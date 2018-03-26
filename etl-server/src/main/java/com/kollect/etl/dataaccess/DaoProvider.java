package com.kollect.etl.dataaccess;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;


@Repository
public class DaoProvider {
  
  private IAbstractSqlSessionProvider sqlSessionProvider;

  public DaoProvider() {
      sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
  }
  
  public int updateQuery (final String queryName, Object object) throws PersistenceException {
    return sqlSessionProvider.update(queryName, object);
}
  
  public List<Object> executeQuery(final String queryName, Object args)  throws PersistenceException {
    return sqlSessionProvider.queryObject(queryName, args);
  }
  
  public <T> Iterator<T> executeQueryItr(final String queryName, Object args) throws PersistenceException {
    return sqlSessionProvider.query(queryName, args);
  }
  
  public void batchInsert(final List<Object> modelList, final String queryName) throws PersistenceException  {
    sqlSessionProvider.batchInsert(modelList, queryName);
  }
  
  public void batchUpdate(final List<Object> modelList, final String queryName) throws PersistenceException  {
    sqlSessionProvider.batchUpdate(modelList, queryName);
  }

}
