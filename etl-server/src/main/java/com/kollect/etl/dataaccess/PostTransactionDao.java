package com.kollect.etl.dataaccess;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;


@Repository
public class PostTransactionDao {
  
  private IAbstractSqlSessionProvider sqlSessionProvider;

  public PostTransactionDao() {
      sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
  }

  public int updatePostTransaction (final String queryName, Object object) {
      return sqlSessionProvider.update(queryName, object);
  }
  
  public int updateQuery (final String queryName, Object object) throws PersistenceException {
    return sqlSessionProvider.update(queryName, object);
}
  
  public List<Object> executeQuery(final String queryName, Object args) {
    return sqlSessionProvider.queryObject(queryName, args);
  }

}
