package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.DcConfigDao;
import com.kollect.etl.entity.DcConfig;

@Service
public class DcConfigService {

  @Autowired
  private DcConfigDao dcConfigDao;
  
  
  public List<DcConfig> getDcConfigById(Object object){
    return this.dcConfigDao.getDcConfigById(object);
  }
  
  public List<DcConfig> getDcConfigByParentId(Object object){
    return this.dcConfigDao.getDcConfigByParentId(object);
  }
  
  
  public List<Object> getDcConfigParent(Object obj){
    return this.dcConfigDao.getDcConfigParent(obj);
  }


  public int deleteDcConfig(Object object) {
    return this.dcConfigDao.deleteConfig(object);
  }
  
  public int saveConfig(Object object) {
    return this.dcConfigDao.saveConfig(object);
  }
  
  public int updateDcConfigProp(Object object) {
    return this.dcConfigDao.updateDcConfigProp(object);
}
  
  public int insertDcConfigProp(Object object) {
	  return this.dcConfigDao.insertDcConfigProp(object);
  }
  
  public List<Object> getConfigListForExport(Object obj){
    return this.dcConfigDao.getConfigListForExport(obj);
  }
  
  
}
