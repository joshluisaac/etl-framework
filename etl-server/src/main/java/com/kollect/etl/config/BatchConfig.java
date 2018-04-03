package com.kollect.etl.config;

import java.util.Map;

public class BatchConfig {

    public static Map<String, CrudProcessHolder> buildHolderMap(Map<String, CrudProcessHolder> map) {
        map.put("AB", new CrudProcessHolder("getTransactionAB", 10, 100));
        map.put("RG", new CrudProcessHolder("getTransactionRG", 10, 100));
        map.put("YY", new CrudProcessHolder("getTransactionYY", 10, 100));
    map.put("CLEARING_DOC_BASED_TYPES", new CrudProcessHolder("getTrxClearingDocBasedTypes", 10, 100));
        map.put("GI", new CrudProcessHolder("getTransactionGI", 10, 100));
        map.put("RI", new CrudProcessHolder("getTransactionRI", 10, 100));
        map.put("RM", new CrudProcessHolder("getTransactionRM", 10, 100));
        map.put("RV", new CrudProcessHolder("getTransactionRV", 10, 100));
        map.put("RY", new CrudProcessHolder("getTransactionRY", 10, 100));
        map.put("YC", new CrudProcessHolder("getTransactionYC", 10, 100));
        map.put("YD", new CrudProcessHolder("getTransactionYD", 10, 100));
        map.put("YH", new CrudProcessHolder("getTransactionYH", 10, 100));
        map.put("YI", new CrudProcessHolder("getTransactionYI", 10, 100));
        map.put("YJ", new CrudProcessHolder("getTransactionYJ", 10, 100));
        map.put("YL", new CrudProcessHolder("getTransactionYL", 10, 100));
        map.put("YN", new CrudProcessHolder("getTransactionYN", 10, 100));
        map.put("YO", new CrudProcessHolder("getTransactionYO", 10, 100));
        map.put("YP", new CrudProcessHolder("getTransactionYP", 10, 100));
        map.put("YQ", new CrudProcessHolder("getTransactionYQ", 10, 100));
        map.put("YR", new CrudProcessHolder("getTransactionYR", 10, 100));
        map.put("YS", new CrudProcessHolder("getTransactionYS", 10, 100));
        map.put("YT", new CrudProcessHolder("getTransactionYT", 10, 100));
        map.put("YU", new CrudProcessHolder("getTransactionYU", 10, 100));
        map.put("YV", new CrudProcessHolder("getTransactionYV", 10, 100));
        map.put("YW", new CrudProcessHolder("getTransactionYW", 10, 100));
        map.put("YX", new CrudProcessHolder("getTransactionYX", 10, 100));
        map.put("YK", new CrudProcessHolder("getTransactionYK", 10, 100));
        map.put("Y1", new CrudProcessHolder("getTransactionY1", 10, 100));
        map.put("YE", new CrudProcessHolder("getTransactionYE", 10, 100));
        map.put("YM", new CrudProcessHolder("getTransactionYM", 10, 100));
        map.put("YF", new CrudProcessHolder("getTransactionYF", 10, 100));
        map.put("ZZ", new CrudProcessHolder("getTransactionZZ", 10, 100));
        map.put("OTHERS", new CrudProcessHolder("getTransactionOthers", 10, 100));

        return map;
    }

}
