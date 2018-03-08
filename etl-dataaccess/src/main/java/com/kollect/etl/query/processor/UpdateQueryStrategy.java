package com.kollect.etl.query.processor;

import java.io.IOException;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public class UpdateQueryStrategy extends AbstractQueryStrategy {

  public UpdateQueryStrategy(String queryPropertiesFile) throws IOException {
    super(queryPropertiesFile);
  }

  @Override
  public void execute( IAbstractSqlSessionProvider iSqlSessionProvider, String queryName, Object object ) {
    //iSqlSessionProvider.update(queryName, object);
  }
  
  

}