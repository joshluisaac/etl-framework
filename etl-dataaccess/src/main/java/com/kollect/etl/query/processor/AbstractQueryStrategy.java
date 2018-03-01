package com.kollect.etl.query.processor;

import java.io.IOException;
import java.util.Properties;

import com.kollect.etl.util.PropertiesUtils;
import com.kollect.etl.util.StringUtils;

public abstract class AbstractQueryStrategy implements IQueryStrategy {

  private static final String DB_PROPERTIES = "../config/server.properties";
  private Properties dbProp;
  private Properties queryProp;
  
  //public AbstractQueryStrategy(){}

  public AbstractQueryStrategy(String queryPropertiesFile) throws IOException {
    loadDbProperties(DB_PROPERTIES);
    loadQueryProperties(queryPropertiesFile);
  }

  public Properties loadDbProperties( String fileName ) throws IOException {
    try {
      this.dbProp = new PropertiesUtils().loadPropertiesFile(fileName);
      return dbProp;
    } catch (IOException e) {
      throw new IOException(e.getMessage(), e);
    }
  }

  public Properties loadQueryProperties( String fileName ) {
    try {
      this.queryProp = new PropertiesUtils().loadPropertiesFile(fileName);
      return queryProp;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;// should not return null
  }

  public Object getObjectParams() {
    return null;
  }

  public String getDbSources() {
    return queryProp.getProperty("query.dbsources");
  }

  public String[] getDbList( String dbSources ) {
    return new StringUtils().splitString(dbSources, "\\,");
  }

  public String getQueryNames( String batchName ) {
    return queryProp.getProperty("etlprocessservice." + batchName + ".query");
  }

  public String[] getQueryNameList( String queryList ) {
    return new StringUtils().splitString(queryList, "\\,");
  }

}
