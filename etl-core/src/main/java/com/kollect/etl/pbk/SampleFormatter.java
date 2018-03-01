package com.kollect.etl.pbk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SampleFormatter  implements Formattable {

  @SuppressWarnings("unchecked")
  public List<String> format(Iterator<Object> itr) {
    List<String> list = new ArrayList<>();
    while (itr.hasNext()) {
      Object row = itr.next();
      Map<String, Object> rowMap = (Map<String, Object>) row;
      Object[] line = { 
          rowMap.get("customer_name"), 
          rowMap.get("customer_no"), 
          rowMap.get("created_at") == null ? "" : rowMap.get("created_at"),
          rowMap.get("updated_at") == null ? "" : rowMap.get("updated_at")  };
      String message = MessageFormat.format("{0}|{1}|{2}|{3}", line);
      
      
      list.add(message);
    }
    return list;
  }


}
