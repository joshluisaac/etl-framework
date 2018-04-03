package com.kollect.etl.config;

import java.util.Map;

public class BatchConfig {
  
  
  
  public static Map<String, CrudProcessHolder> buildHolderMap (Map<String, CrudProcessHolder> map) {
    map.put("AB", new CrudProcessHolder("getTransactionAB",10,100));
    map.put("RG", new CrudProcessHolder("getTransactionRG",10,100));
    map.put("YY", new CrudProcessHolder("getTransactionYY",10,100));
    map.put("CLEARING_DOC_BASED_TYPES", new CrudProcessHolder("getTrxClearingDocBasedTypes",10,100));
    map.put("GI", new CrudProcessHolder("getTransactionGI",10,100));
    map.put("RI", new CrudProcessHolder("getTransactionRI",10,100));
    map.put("RM", new CrudProcessHolder("getTransactionRM",10,100));
    map.put("RV", new CrudProcessHolder("getTransactionRV",10,100));
    map.put("RY", new CrudProcessHolder("getTransactionRY",10,100));
    map.put("YC", new CrudProcessHolder("getTransactionYC",10,100));
    map.put("YD", new CrudProcessHolder("getTransactionYD",10,100));
    map.put("YH", new CrudProcessHolder("getTransactionYH",10,100));
    return map;
  }
  
  

}
