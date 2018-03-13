package com.kollect.etl.dataaccess;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoMockByAnnotationTest {
  
  private static final Logger LOG = LoggerFactory.getLogger(DaoMockByAnnotationTest.class);
  
  @Mock
  private IAbstractSqlSessionProvider daoProvider;
  
  @InjectMocks
  private Service service;
  
  @Before
  public void setup_run_once_per_method() {
    MockitoAnnotations.initMocks(this);
  }
  
  
  @Test
  public void test_queryObject() {
   service.queryObject();
   LOG.info("Completed Mocks!!!");
  }

}
