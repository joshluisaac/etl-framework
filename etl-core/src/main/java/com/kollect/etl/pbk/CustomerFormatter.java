package com.kollect.etl.pbk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomerFormatter implements Formattable {
  
  
  @SuppressWarnings("unchecked")
  public List<String> format(Iterator<Object> itr) {
    List<String> list = new ArrayList<>();
    while (itr.hasNext()) {
      Object row = itr.next();
      Map<String, Object> rowMap = (Map<String, Object>) row;      
      Object[] line = { 
          rowMap.get("customer_id"), 
          rowMap.get("customer_name"), 
          rowMap.get("pic_name"),
          rowMap.get("address_1"),
          rowMap.get("address_2"), 
          rowMap.get("city"), 
          rowMap.get("state"), 
          rowMap.get("zipcode"), 
          rowMap.get("phone_value"),
          rowMap.get("phone_type")
      };
      String message = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}", line).replaceAll("null", "");
      list.add(message);
    }
    return list;
  }

}
