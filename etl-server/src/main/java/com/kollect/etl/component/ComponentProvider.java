package com.kollect.etl.component;

import org.springframework.stereotype.Component;

import com.kollect.etl.util.StringUtils;

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

}
