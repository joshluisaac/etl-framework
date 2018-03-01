package com.kollect.etl.util.load.config.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData {
  
  public List<Object> mockList() {

    Map<String, String> m = new HashMap<>();
    m.put("delimiter", "|");
    m.put("destinationTable", "CUSTOMERS");
    m.put("generatedKey", "ID");
    m.put("generatedKeySequence", "CUSTOMERS_ID");
    m.put("dateFormat", "yyyy-MM-dd HH:mm:ss");
    m.put("fileName", "CUSTOMERS");

    m.put("isExternal", "true");

    m.put("isKey", "true");
    m.put("isOptional", "true");
    m.put("fieldName", "customer_Name");
    m.put("defaultValue", "");
    m.put("locationStart", "4");
    m.put("locationEnd", "4");
    m.put("handlerName", "date");
    m.put("lookUpQuery", "");
    m.put("isLookUp", "true");

    List<Object> list = new ArrayList<>();
    list.add(m);

    return list;
  }

}
