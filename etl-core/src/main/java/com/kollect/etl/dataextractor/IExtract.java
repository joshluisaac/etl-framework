package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.util.List;

public interface IExtract {
  
  public List<String> query(final Connection dbConnection, final String queryString);

}
