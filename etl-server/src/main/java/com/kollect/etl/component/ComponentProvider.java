package com.kollect.etl.component;

import com.kollect.etl.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ComponentProvider {
  
  
  /** Will resolve the account as either commercial or non commercial
   * 
   * @param accountNo
   * @param pattern
   * @return boolean value of regex result
   */
  public boolean isCommericalResolver(String accountNo, String pattern){
    return new StringUtils().hasMatch(accountNo, pattern) ? true:false;
    
  }
  
  
  @SuppressWarnings("unchecked")
  public void buildMapArgs(List<Object> list) {
    for (Object obj : list) {
      Map<String, Object> m = (Map<String, Object>) obj;
      Map<String, Object> args = new HashMap<>();
      for (Map.Entry<String, Object> x : m.entrySet()) {
        args.put(x.getKey(), x.getValue());
      }
      
      ///update statement
    }

  }
  /**
   * A sleep method to let the application rest for given seconds */
  public void taskSleep(){
    try {
      System.out.println("Rejuvenating for ten seconds...");
      TimeUnit.SECONDS.sleep(10);
    }catch (Exception e){
      System.out.println("An error occurred during thread sleep." +e);
    }
  }
 
}
