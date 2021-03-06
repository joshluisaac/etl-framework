package com.kollect.etl.dataaccess;



import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MybatisTest {

  private static final Logger LOG = LoggerFactory.getLogger(MybatisTest.class);
  
  private IAbstractSqlSessionProvider daoProvider;
  private Service service;
  
  //@Mock Dao dao;
  
  
  @Before
  public void run_one_per_method() {
    System.out.println("Running mocito....");
    daoProvider = Mockito.mock(AbstractSqlSessionProvider.class);
    service = new Service(daoProvider);
  }
  
  @Test
  public void test_queryObject() {
   service.queryObject();
  }



}
