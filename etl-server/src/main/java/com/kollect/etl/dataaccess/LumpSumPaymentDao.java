package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class LumpSumPaymentDao {
	 IAbstractSqlSessionProvider sqlSessionProvider;
	
	public LumpSumPaymentDao() {
	    sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pbk");
	  }
	
	 public List<Object> getSumAmount(Object object) {
	    // TODO Auto-generated method stub
	    return sqlSessionProvider.queryObject("getSumAmount", object);
	  }
	 
	 public int updateGetSumAmount(Object object) {
	    return sqlSessionProvider.update("updateGetSumAmount", object);
	  }
	 
	 public int insertGetSumAmount(Object object) {
	   return sqlSessionProvider.insert("insertGetSumAmount", object);
	 }
}