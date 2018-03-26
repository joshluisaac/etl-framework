package com.kollect.etl.dataaccess;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service {
  
  private static final Logger LOG = LoggerFactory.getLogger(Service.class);
  IAbstractSqlSessionProvider daoProvider;
  
  public Service(IAbstractSqlSessionProvider daoProvider) {
    this.daoProvider = daoProvider;
    //sqlSessionProvider = new AbstractSqlSessionProvider("as400iSeries");
  }
  
  public void queryObject () {
    List<Object> list= daoProvider.queryObject("getCfmastCustomer", null);
    for (Object object : list) {
      Map<String, Object> map = (Map<String, Object>) object;
      LOG.debug("{}|{}|{}", map.get("cfcifn"),map.get("cfssno"),map.get("cfsex"));
      
    }
  }
  
  public void queryObject2() {
    daoProvider.queryObject("getUpdatedCustomer", null);
  }

  public void queryMultipleObjects() {
    daoProvider.queryMultipleObjects("getUpdatedCustomer", null);
  }
  
  public void update() throws PersistenceException {
    daoProvider.update("updateCosecLineOfBusiness", 65);
  }

  public void insert(){
    daoProvider.delete("deleteDuplicateCustomer", null);
  }
  

}
