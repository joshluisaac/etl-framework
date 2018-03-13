package com.kollect.etl.dataaccess;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoMockTest {
  
  private static final Logger LOG = LoggerFactory.getLogger(DaoMockTest.class);
  
  private IAbstractSqlSessionProvider daoProvider;
  private Service service;
  
  
  @Before
  public void setUp_run_once_per_method() {
    System.out.println("Running mocito....");
    daoProvider = Mockito.mock(AbstractSqlSessionProvider.class);
    service = new Service(daoProvider);
  }
  
  @Test
  public void test_query_as400iseries() {
   service.queryObject();
   LOG.info("Executed Query!");
  }

}
