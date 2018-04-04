package com.kollect.etl.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BatchConfig implements IBatchConfig {
  
  public List<String> getChildQuery() {
     return new ArrayList<>(Arrays.asList("updateTransactionLoad"));
  }

    public Map<String, CrudProcessHolder> crudHolderMap(Map<String, CrudProcessHolder> map) {
        map.put("AB", new CrudProcessHolder("getTransactionAB", 10, 100, getChildQuery()));
        map.put("RG", new CrudProcessHolder("getTransactionRG", 10, 100, getChildQuery()));
        map.put("YY", new CrudProcessHolder("getTransactionYY", 10, 100, getChildQuery()));
        map.put("CLEARING_DOC_BASED_TYPES", new CrudProcessHolder("getTrxClearingDocBasedTypes", 10, 100, getChildQuery()));
        map.put("GI", new CrudProcessHolder("getTransactionGI", 10, 100, getChildQuery()));
        map.put("RI", new CrudProcessHolder("getTransactionRI", 10, 100, getChildQuery()));
        map.put("RM", new CrudProcessHolder("getTransactionRM", 10, 100, getChildQuery()));
        map.put("RV", new CrudProcessHolder("getTransactionRV", 10, 100, getChildQuery()));
        map.put("RY", new CrudProcessHolder("getTransactionRY", 10, 100, getChildQuery()));
        map.put("YC", new CrudProcessHolder("getTransactionYC", 10, 100, getChildQuery()));
        map.put("YD", new CrudProcessHolder("getTransactionYD", 10, 100, getChildQuery()));
        map.put("YH", new CrudProcessHolder("getTransactionYH", 10, 100, getChildQuery()));
        map.put("YI", new CrudProcessHolder("getTransactionYI", 10, 100, getChildQuery()));
        map.put("YJ", new CrudProcessHolder("getTransactionYJ", 10, 100, getChildQuery()));
        map.put("YL", new CrudProcessHolder("getTransactionYL", 10, 100, getChildQuery()));
        map.put("YN", new CrudProcessHolder("getTransactionYN", 10, 100, getChildQuery()));
        map.put("YO", new CrudProcessHolder("getTransactionYO", 10, 100, getChildQuery()));
        map.put("YP", new CrudProcessHolder("getTransactionYP", 10, 100, getChildQuery()));
        map.put("YQ", new CrudProcessHolder("getTransactionYQ", 10, 100, getChildQuery()));
        map.put("YR", new CrudProcessHolder("getTransactionYR", 10, 100, getChildQuery()));
        map.put("YS", new CrudProcessHolder("getTransactionYS", 10, 100, getChildQuery()));
        map.put("YT", new CrudProcessHolder("getTransactionYT", 10, 100, getChildQuery()));
        map.put("YU", new CrudProcessHolder("getTransactionYU", 10, 100, getChildQuery()));
        map.put("YV", new CrudProcessHolder("getTransactionYV", 10, 100, getChildQuery()));
        map.put("YW", new CrudProcessHolder("getTransactionYW", 10, 100, getChildQuery()));
        map.put("YX", new CrudProcessHolder("getTransactionYX", 10, 100, getChildQuery()));
        map.put("YK", new CrudProcessHolder("getTransactionYK", 10, 100, getChildQuery()));
        map.put("Y1", new CrudProcessHolder("getTransactionY1", 10, 100, getChildQuery()));
        map.put("YE", new CrudProcessHolder("getTransactionYE", 10, 100, getChildQuery()));
        map.put("YM", new CrudProcessHolder("getTransactionYM", 10, 100, getChildQuery()));
        map.put("YF", new CrudProcessHolder("getTransactionYF", 10, 100, getChildQuery()));
        map.put("ZZ", new CrudProcessHolder("getTransactionZZ", 10, 100, getChildQuery()));
        map.put("OTHERS", new CrudProcessHolder("getTransactionOthers", 10, 100, getChildQuery()));

        return map;
    }

}
