package com.kollect.etl.util;


import java.lang.reflect.Method;

public class ReflectionSample {
  
  
  public void threeWaysOfObtainingClassReference() throws ClassNotFoundException {
    Class<?> clazz1 = FileUtils.class;
    Class<?> clazz2 = Class.forName("com.kollect.etl.util.FileUtils");
    FileUtils instance = new FileUtils();
    Class<?> clazz3 = instance.getClass();
  }
  
  public static void main(String[] args) throws Exception {
    Class<?> clazz = StringUtils.class;
    Method[] methods = clazz.getDeclaredMethods();
   
    for (Method m : methods) {
      String mName = m.getName();
      System.out.println(mName);
    }
    
    //StringUtils.class.getClass().getMethod("hasMatch", null).invoke("StringUtils", null);
    
    Object newInstance = clazz.newInstance();
    Method met = clazz.getMethod("hasMatch", String.class, String.class);
    met.invoke(newInstance, "344999888676657778","^3[0-9]{8}");
    
    //shortcut
    Object result = clazz.getMethod("hasMatch", String.class, String.class).invoke(clazz.newInstance(), "344999888676657778","^3[0-9]{8}");
    System.out.println(result);
    
    Class<Math> clazz0 = Math.class;
    Method cbrtMethod = clazz0.getMethod("cbrt", double.class);
    Object resultCbrt = cbrtMethod.invoke(null, 27.0);
    System.out.println(resultCbrt);
    
    

  }

}
