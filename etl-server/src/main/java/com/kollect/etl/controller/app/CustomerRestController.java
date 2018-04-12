package com.kollect.etl.controller.app;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollect.etl.dataaccess.AbstractSqlSessionProvider;
import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController {
  
  IAbstractSqlSessionProvider sqlSessionProvider;
  
  public CustomerRestController() {
    sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
  }
  
  @GetMapping("/all")
  public List<Object> getAllCustomers(){
    return sqlSessionProvider.queryObject("getAllCustomer", null);
  }
  
  @RequestMapping("/{id}")
  public List<Object> getCustomerById(@PathVariable int id){
    return sqlSessionProvider.queryObject("getCustomerById", id);
  }
  

}
