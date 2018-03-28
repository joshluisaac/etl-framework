package com.kollect.etl.dataaccess;

import java.util.Iterator;
import java.util.List;

public interface IAbstractSqlSessionProvider {
  
  public <T>  List<T> queryObject(final String queryName, final Object object);
  public Iterator<Object> queryMultipleObjects(final String queryName, final Object object);
  public <T> Iterator<T> query (final String queryName, final Object object);
  public Object querySingleObject(final String queryName, final Object object );
  public int insert(final String queryName, final Object object);
  public int update(final String queryName, final Object object);
  public int delete(final String queryName, final Object object);
  public void batchInsert(final List<Object> modelList, final String queryName);
  public void batchUpdate(final List<Object> modelList, final String queryName);
<<<<<<< HEAD
  //public void batchInsert2(final List<Object> list, final String queryName);
=======
  public void batchUpdate(final List<Object> modelList, final String queryName, final boolean giantQuery);
  public void batchInvoice(final List<Object> modelList, final String queryName, final boolean giantQuery);
  

>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
  

}
