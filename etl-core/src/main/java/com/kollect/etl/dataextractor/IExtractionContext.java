package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.util.List;

public interface IExtractionContext {
  
  public List<String> executeQuery(final Connection dbConnection, final String queryString);

}
