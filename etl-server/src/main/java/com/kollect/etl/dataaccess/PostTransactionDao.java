package com.kollect.etl.dataaccess;

import java.util.List;

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
  
  public List<Object> getAccount(final String queryName, Object args) {
    return sqlSessionProvider.queryObject(queryName, args);
  }

}
