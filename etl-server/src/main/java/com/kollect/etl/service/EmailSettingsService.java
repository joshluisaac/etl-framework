package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.EmailSettingsDao;

@Service
public class EmailSettingsService {
	 @Autowired
	 private EmailSettingsDao emailSettingsDao;
	 
	 
	 public int insertEmailSettings(Object object) {
		  return this.emailSettingsDao.insertEmailSettings(object);
	  }


	public List<Object> getTemplateSettings(Object object) {
		// TODO Auto-generated method stub
		return this.emailSettingsDao.getTemplateSettings(object);
		
	}
	
	public int updateEmailSettings(Object object) {
		  return this.emailSettingsDao.updateEmailSettings(object);
	  }

	
	public List<Object> getEmailCounter(){
		return this.emailSettingsDao.getEmailCounter();
	}
}
