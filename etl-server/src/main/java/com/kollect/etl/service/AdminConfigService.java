package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.AdminConfigDao;

@Service
public class AdminConfigService {
	
	  @Autowired
	  private AdminConfigDao adminConfigDao;
	  
	  
	  public List<Object> getAdminConfigById(Object object){
	    return this.adminConfigDao.getAdminConfigById(object);
	  }


	  public int saveAdminConfig(Object object) {
		    return this.adminConfigDao.saveAdminConfig(object);
		  }
		  
	  
	  public int insertAdminSftpProp(Object object) {
		  return this.adminConfigDao.insertAdminSftpProp(object);
	  }
	  
	  public int insertAdminDatabaseProp(Object object) {
		  return this.adminConfigDao.insertAdminDatabaseProp(object);
	  }
	  
	  //new changes
	  public List<Object> getAdminSFTPSettings(Object object) {
			// TODO Auto-generated method stub
			return this.adminConfigDao.getAdminTemplateSettings(object);
			
		}
	  
		public int updateAdminSFTPSettings(Object object) {
			  return this.adminConfigDao.updateAdminSFTPSettings(object);
		  }

		
		public List<Object> getAdminSFTPCounter(){
			return this.adminConfigDao.getAdminSFTPCounter();
		}
		
		//new changes
		  public List<Object> getAdminDatabaseSettings(Object object) {
				// TODO Auto-generated method stub
				return this.adminConfigDao.getAdminDatabaseTemplateSettings(object);
				
			}
		  
			public int updateAdminDatabaseSettings(Object object) {
				  return this.adminConfigDao.updateAdminDatabaseSettings(object);
			  }

			
			public List<Object> getAdminDatabaseCounter(){
				return this.adminConfigDao.getAdminDatabaseCounter();
			}
}
