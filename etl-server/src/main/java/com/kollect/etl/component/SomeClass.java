package com.kollect.etl.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SomeClass {
  
  @SuppressWarnings("unchecked")
  public void buildMapArgs(List<Object> list) {
    for (Object obj : list) {      
      Map<String, Object> args = new HashMap<>((Map<String, Object>) obj);
      
      ///update statement
    }
  }
  
  
  //@SuppressWarnings("unchecked")
  private static <T> Map<String, T> getArgs(T obj) {
      Map<String, T> m = (Map<String, T>) obj;
      Map<String, T> args = new HashMap<>();
      for (Map.Entry<String, T> x : m.entrySet()) {
          args.put(x.getKey(), x.getValue());
      }
      return args;
  }

}
