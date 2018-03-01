package com.kollect.etl.query.processor;

import java.io.IOException;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public class DeleteQueryStrategy extends AbstractQueryStrategy {

  public DeleteQueryStrategy(String queryPropertiesFile) throws IOException {
    super(queryPropertiesFile);
  }



  @Override
  public void execute( IAbstractSqlSessionProvider iSqlSessionProvider, String queryName, Object object ) {
    iSqlSessionProvider.delete(queryName, object);
    
  }

}
