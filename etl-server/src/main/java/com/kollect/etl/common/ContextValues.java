package com.kollect.etl.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContextValues {
  
  public Map<String, Object> buildContext(){
    Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("currentDate", new Date());
    return contextMap; 
  }

}
