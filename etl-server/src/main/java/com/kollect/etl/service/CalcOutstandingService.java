package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.CalcOutstandingDao;

@Service
public class CalcOutstandingService {
  @Autowired
  private CalcOutstandingDao calcOutstandingDao;

  public List<Object> getOutstandingByTenantId(Object object) {
    return this.calcOutstandingDao.getOutstandingByTenantId(object);
    // TODO Auto-generated method stub

  }
  
  public int updateOutstanding(Object object) {
    return this.calcOutstandingDao.updateOutstanding(object);
  }
  
  
}
