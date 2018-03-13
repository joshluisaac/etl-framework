package com.kollect.etl.dataaccess;



import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MybatisTest {

  private static final Logger LOG = LoggerFactory.getLogger(MybatisTest.class);
  IAbstractSqlSessionProvider sqlSessionProvider;

  //@Before
  public void run_one_per_method() {
    sqlSessionProvider = new AbstractSqlSessionProvider("as400iSeries");
  }
  
  //@Test
  public void test_query_as400iseries() {
    List<Object> list= sqlSessionProvider.queryObject("getCfmastCustomer", null);
    for (Object object : list) {
      Map<String, Object> map = (Map<String, Object>) object;
      LOG.debug("{}|{}|{}", map.get("cfcifn"),map.get("cfssno"),map.get("cfsex"));
      
    }
  }

  //@Test
  public void test_query_object() {
    sqlSessionProvider.queryObject("getUpdatedCustomer", null);
  }

  //@Test
  public void test_query_multiple_objects() {
    sqlSessionProvider.queryMultipleObjects("getUpdatedCustomer", null);
  }

  //@Test
  public void test_update_statement(){
    sqlSessionProvider.update("updateCosecLineOfBusiness", 65);
  }

  //@Test
  public void test_delete_statement(){
    sqlSessionProvider.delete("deleteDuplicateCustomer", null);
  }

}
