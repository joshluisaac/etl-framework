package com.kollect.etl.dataaccess;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkUpdateTest {
  
  private static final Logger LOG = LoggerFactory.getLogger(BulkUpdateTest.class);
  
  private IAbstractSqlSessionProvider daoProvider;
  private Service service;
  
  @Before
  public void run_one_per_method() {
    ;
    service = new Service(daoProvider = new AbstractSqlSessionProvider("postgres"));
  }
  
  @Test(expected = PersistenceException.class)
  public void testUpdateThrowsPersistenceException() {
    service.update();
  }
  
  @Test(expected = PersistenceException.class)
  public void testExecuteQuery() {
    service.queryMultipleObjects();
  }

}
