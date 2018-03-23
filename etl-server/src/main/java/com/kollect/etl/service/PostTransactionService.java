package com.kollect.etl.service;


import com.kollect.etl.dataaccess.PostTransactionDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTransactionService {
  
  @Autowired
  private PostTransactionDao dao;
  
  public int updatePostTransaction(final String queryName, Object object) {
    return this.dao.updatePostTransaction(queryName, object);
  }
  
  public List<Object> getAccount(final String queryName, Object args){
    return this.dao.getAccount(queryName,args);
  }
  

}
