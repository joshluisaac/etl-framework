package com.kollect.etl.util;

import java.lang.reflect.Method;

public class ReflectionSample {
  
  public static void main(String[] args) {
    Class clazz = StringUtils.class;
    Method[] methods = clazz.getDeclaredMethods();
    clazz.getSimpleName();
    
    for(int i=0; i<methods.length; i++) {
      System.out.println(methods[i]);
     
    }
  }

}
