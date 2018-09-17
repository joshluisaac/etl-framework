package com.kollect.etl.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Tester {

  private IType type;
  
  @Autowired
  Tester(IType type){
    this.type = type;
  }
  
  

  
}
