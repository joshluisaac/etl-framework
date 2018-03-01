package com.kollect.etl.query.processor;

import java.io.IOException;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public class SelectQueryStrategy extends AbstractQueryStrategy {

  public SelectQueryStrategy(String queryPropertiesFile) throws IOException {
    super(queryPropertiesFile);
  }

  @Override
  public void execute( IAbstractSqlSessionProvider iSqlSessionProvider, String queryName, Object object ) {
    iSqlSessionProvider.querySingleObject(queryName, object);
    
  }

}
