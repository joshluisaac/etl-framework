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

  /**
   * HTTP GET request to retrieve all customer
   *
   * no parameter(s) required
   *
   * @return
   *        returns all customer on /all form
   */
  @GetMapping("/all")
  public List<Object> getAllCustomers(){
    return sqlSessionProvider.queryObject("getAllCustomer", null);
  }

  /**
   * HTTP GET request to retrieve customer using the associated id
   *
   * @param id
   *        id of the customer to be retrieved
   * @return
   *        returns customer data associated with the given id on /{id} form
   */
  @RequestMapping("/{id}")
  public List<Object> getCustomerById(@PathVariable int id){
    return sqlSessionProvider.queryObject("getCustomerById", id);
  }
  

}
