package com.kollect.etl.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

public class BatchConfig implements IBatchConfig {
  
  
  
  public List<String> getChildQuery() {
     return new ArrayList<>(Arrays.asList("updateTransactionLoad"));
  }

    public Map<String, CrudProcessHolder> crudHolderMap(Map<String, CrudProcessHolder> map, final String dataSrc) {
        map.put("AB", new CrudProcessHolder(dataSrc,"getTransactionAB", 10, 100, getChildQuery()));
        map.put("RG", new CrudProcessHolder(dataSrc,"getTransactionRG", 10, 100, getChildQuery()));
        map.put("YY", new CrudProcessHolder(dataSrc,"getTransactionYY", 10, 100, getChildQuery()));
        map.put("CLEARING_DOC_BASED_TYPES", new CrudProcessHolder(dataSrc,"getTrxClearingDocBasedTypes", 10, 100, getChildQuery()));
        map.put("GI", new CrudProcessHolder(dataSrc,"getTransactionGI", 10, 100, getChildQuery()));
        map.put("RI", new CrudProcessHolder(dataSrc,"getTransactionRI", 10, 100, getChildQuery()));
        map.put("RM", new CrudProcessHolder(dataSrc,"getTransactionRM", 10, 100, getChildQuery()));
        map.put("RV", new CrudProcessHolder(dataSrc,"getTransactionRV", 10, 100, getChildQuery()));
        map.put("RY", new CrudProcessHolder(dataSrc,"getTransactionRY", 10, 100, getChildQuery()));
        map.put("YC", new CrudProcessHolder(dataSrc,"getTransactionYC", 10, 100, getChildQuery()));
        map.put("YD", new CrudProcessHolder(dataSrc,"getTransactionYD", 10, 100, getChildQuery()));
        map.put("YH", new CrudProcessHolder(dataSrc,"getTransactionYH", 10, 100, getChildQuery()));
        map.put("YI", new CrudProcessHolder(dataSrc,"getTransactionYI", 10, 100, getChildQuery()));
        map.put("YJ", new CrudProcessHolder(dataSrc,"getTransactionYJ", 10, 100, getChildQuery()));
        map.put("YL", new CrudProcessHolder(dataSrc,"getTransactionYL", 10, 100, getChildQuery()));
        map.put("YN", new CrudProcessHolder(dataSrc,"getTransactionYN", 10, 100, getChildQuery()));
        map.put("YO", new CrudProcessHolder(dataSrc,"getTransactionYO", 10, 100, getChildQuery()));
        map.put("YP", new CrudProcessHolder(dataSrc,"getTransactionYP", 10, 100, getChildQuery()));
        map.put("YQ", new CrudProcessHolder(dataSrc,"getTransactionYQ", 10, 100, getChildQuery()));
        map.put("YR", new CrudProcessHolder(dataSrc,"getTransactionYR", 10, 100, getChildQuery()));
        map.put("YS", new CrudProcessHolder(dataSrc,"getTransactionYS", 10, 100, getChildQuery()));
        map.put("YT", new CrudProcessHolder(dataSrc,"getTransactionYT", 10, 100, getChildQuery()));
        map.put("YU", new CrudProcessHolder(dataSrc,"getTransactionYU", 10, 100, getChildQuery()));
        map.put("YV", new CrudProcessHolder(dataSrc,"getTransactionYV", 10, 100, getChildQuery()));
        map.put("YW", new CrudProcessHolder(dataSrc,"getTransactionYW", 10, 100, getChildQuery()));
        map.put("YX", new CrudProcessHolder(dataSrc,"getTransactionYX", 10, 100, getChildQuery()));
        map.put("YK", new CrudProcessHolder(dataSrc,"getTransactionYK", 10, 100, getChildQuery()));
        map.put("Y1", new CrudProcessHolder(dataSrc,"getTransactionY1", 10, 100, getChildQuery()));
        map.put("YE", new CrudProcessHolder(dataSrc,"getTransactionYE", 10, 100, getChildQuery()));
        map.put("YM", new CrudProcessHolder(dataSrc,"getTransactionYM", 10, 100, getChildQuery()));
        map.put("YF", new CrudProcessHolder(dataSrc,"getTransactionYF", 10, 100, getChildQuery()));
        map.put("ZZ", new CrudProcessHolder(dataSrc,"getTransactionZZ", 10, 100, getChildQuery()));
        map.put("OTHERS", new CrudProcessHolder(dataSrc,"getTransactionOthers", 10, 100, getChildQuery()));

        return map;
    }

}
