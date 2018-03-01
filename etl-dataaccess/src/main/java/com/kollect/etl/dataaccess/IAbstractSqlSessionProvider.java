package com.kollect.etl.dataaccess;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public interface IAbstractSqlSessionProvider {
  
  public <T>  List<T> queryObject(final String queryName, final Object object);
  public Iterator<Object> queryMultipleObjects(final String queryName, final Object object);
  public <T> Iterator<T> query (final String queryName, final Object object) throws SQLException;
  public Object querySingleObject(final String queryName, final Object object );
  public int insert(final String queryName, final Object object);
  public int update(final String queryName, final Object object);
  public int delete(final String queryName, final Object object);

}
