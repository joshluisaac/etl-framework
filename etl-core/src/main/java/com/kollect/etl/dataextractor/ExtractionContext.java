package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.util.List;

public class ExtractionContext implements IExtractionContext {
  
  private IExtract extract;
  
  public ExtractionContext(IExtract extract){
    this.extract = extract;
  }
  
  public List<String> executeQuery(final Connection dbConnection, final String queryString){
    return extract.query(dbConnection, queryString);
  }

}
