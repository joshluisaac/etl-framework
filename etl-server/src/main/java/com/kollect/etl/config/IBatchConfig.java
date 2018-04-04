package com.kollect.etl.config;

import java.util.Map;

public interface IBatchConfig {
  
  public Map<String, CrudProcessHolder> crudHolderMap(Map<String, CrudProcessHolder> map);

}
