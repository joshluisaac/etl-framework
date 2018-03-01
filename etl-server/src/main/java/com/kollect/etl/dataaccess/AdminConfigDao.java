package com.kollect.etl.dataaccess;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AdminConfigDao {
  IAbstractSqlSessionProvider sqlSessionProvider;

  public AdminConfigDao() {
    sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
  }

  public List<Object> getAdminConfigById(Object object) {
    return sqlSessionProvider.queryObject("getAdminConfigById", object);
  }

  public int saveAdminConfig(Object object) {
    return sqlSessionProvider.insert("insertAdminSftpProp", object);
  }

  public int insertAdminSftpProp(Object object) {
    return sqlSessionProvider.insert("insertAdminSftpProp", object);
  }

  public int insertAdminDatabaseProp(Object object) {
    return sqlSessionProvider.insert("insertAdminDatabaseProp", object);
  }

  // new changes
  public List<Object> getAdminTemplateSettings(Object object) {
    // TODO Auto-generated method stub
    return sqlSessionProvider.queryObject("getAdminConfig", object);
  }

  public int updateAdminSFTPSettings(Object object) {
    return sqlSessionProvider.update("updateAdminSFTPSettings", object);
  }

  public List<Object> getAdminSFTPCounter() {
    return sqlSessionProvider.queryObject("getAdminSFTPCounter", null);
  }

  // new changes
  public List<Object> getAdminDatabaseTemplateSettings(Object object) {
    // TODO Auto-generated method stub
    return sqlSessionProvider.queryObject("getAdminDatabaseConfig", object);
  }

  public int updateAdminDatabaseSettings(Object object) {
    return sqlSessionProvider.update("updateAdminDatabaseSettings", object);
  }

  public List<Object> getAdminDatabaseCounter() {
    return sqlSessionProvider.queryObject("getAdminDatabaseCounter", null);
  }

}
