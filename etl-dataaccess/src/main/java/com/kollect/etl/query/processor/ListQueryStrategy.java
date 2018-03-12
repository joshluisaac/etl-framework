package com.kollect.etl.query.processor;

import java.io.IOException;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public class ListQueryStrategy extends AbstractQueryStrategy {

  public ListQueryStrategy(String queryPropertiesFile) throws IOException {
    super(queryPropertiesFile);
  }

  @Override
  public void execute( IAbstractSqlSessionProvider iSqlSessionProvider, String queryName, Object object ) {
    //iSqlSessionProvider.queryMultipleObjects(queryName, object);
    
  }



}
