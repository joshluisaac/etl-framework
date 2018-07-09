package com.kollect.etl.service;

import org.apache.ibatis.session.SqlSession;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public interface IServiceContext {
  
  IAbstractSqlSessionProvider getSqlSessionProvider();
  public SqlSession getBatchExecutionSqlSession();
  

}
