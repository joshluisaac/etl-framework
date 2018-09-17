package com.kollect.etl.util;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
 
  private Properties p;
  private static final String CONCAT = "concatenator.";

  public Configuration() throws IOException {
    this.p = new PropertiesUtils().loadPropertiesFileResource("util.properties");
    //this.appProp = new PropertiesUtils().loadPropertiesFileResource(APPLICATION_PROPERTIES);
    //this.p = new PropertiesUtils().loadPropertiesFileResource(appProp.getProperty("util.properties"));
  }
  
  public String getRegex(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".regex");
  }
  
  public String getReplacement(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".replacement");
  }
  
  public String getUniqueKeyIndex(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".uniqueKeyIndex");
  }
  
  public String getCloneAs(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".cloneAs");
  }
  
  public String getCloneFlag(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".cloneBeforeUnique");
  }
  
  public String getHashIndicator(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".generateHash");
  }
  
  public String getExpectedLength(String impl) {
    return p.getProperty(CONCAT +impl.toLowerCase()+ ".expectedLength");
  }
  
  public String getDirPath() {
    return p.getProperty("concatenator.dirPath");
  }
  
  public String getFilePrefix() {
    return p.getProperty("concatenator.filePrefix");
  }
  
  public String getFileDestination() {
    return p.getProperty("transform.destinationPath");
  }
  
  

}
