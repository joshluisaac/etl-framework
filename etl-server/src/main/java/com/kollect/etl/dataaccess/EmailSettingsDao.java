package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class EmailSettingsDao {

	IAbstractSqlSessionProvider sqlSessionProvider;
	
	  public EmailSettingsDao() {
		    sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
		  }
	
	public int insertEmailSettings(Object object) {
		  return sqlSessionProvider.insert("insertEmailSettings", object);
	  }

	public List<Object> getTemplateSettings(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.queryObject("getEmailSettings", object);
	}
	
	
	public int updateEmailSettings(Object object) {
		  return sqlSessionProvider.update("updateEmailSettings", object);
	  }
	
	/**
	 * Queries the database to check the email number of records
	 * 
	 * @return returns a list of email settings
	 */
	public List<Object> getEmailCounter(){
		return sqlSessionProvider.queryObject("getEmailCounter", null);
	}

}
