package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CalcOutstandingDao {
  IAbstractSqlSessionProvider sqlSessionProvider;

  public CalcOutstandingDao() {
    sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pbk");
  }

  public List<Object> getOutstandingByTenantId(Object object) {
    // TODO Auto-generated method stub
    return sqlSessionProvider.queryObject("getOutstandingByTenantId", object);
  }

  public int updateOutstanding(Object object) {
    return sqlSessionProvider.update("updateOutstanding", object);
  }
}
