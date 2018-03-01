package com.kollect.etl.extractor;

import java.util.Iterator;
import java.util.List;

public interface IDataExtractorService {
  
  public Iterator<Object> executeQuery(String queryName, Object object);
  public <T> List<T> executeQueryObject(String queryName, Object object);

}
