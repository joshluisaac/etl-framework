package com.kollect.etl.service;


import com.kollect.etl.dataaccess.PostTransactionDao;
import com.kollect.etl.util.StringUtils;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTransactionService {
  
  @Autowired
  private PostTransactionDao dao;
  
  private static final Logger LOG = LoggerFactory.getLogger(PostTransactionService.class);

  public List<Object> executeQuery(final String queryName, Object args){
    return this.dao.executeQuery(queryName,args);
  }
  
  public int updateQuery(final String queryName, Object args){
    try {
      return this.dao.updateQuery(queryName,args);
    } catch (PersistenceException persEx) {
      LOG.error("Failed to execute update statement: {}",queryName, persEx.getCause());
      throw persEx;
    }
  }
  
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
