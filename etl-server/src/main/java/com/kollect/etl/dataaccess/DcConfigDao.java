package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kollect.etl.entity.app.DcConfig;

@Repository
public class DcConfigDao {
  
  IAbstractSqlSessionProvider sqlSessionProvider;
  
  public DcConfigDao() {
    sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
  }
  
  public List<DcConfig> getDcConfigById(Object object){
    return sqlSessionProvider.queryObject("getDcConfigById", object);
  }
  
  public List<DcConfig> getDcConfigByParentId(Object object){
    return sqlSessionProvider.queryObject("getDcConfigByParentId", object);
  }
  
  public List<Object> getDcConfigParent(Object obj){
    return sqlSessionProvider.queryObject("getDcConfigParent", obj);
  }
  
  public int deleteConfig(Object object) {
    return sqlSessionProvider.delete("deleteDcConfig", object);
  }
  
  public int saveConfig(Object object) {
    return sqlSessionProvider.insert("insertDcConfig", object);
  }
  
  public int insertDcConfigProp(Object object) {
	  return sqlSessionProvider.insert("insertDcConfigProp", object);
  }
  
  public int updateDcConfigProp(Object object) {
    return sqlSessionProvider.insert("updateDcConfigProp", object);
}

  public List<Object> getConfigListForExport(Object obj){
    return sqlSessionProvider.queryObject("getConfigListForExport", obj);
  
  }
  

}
