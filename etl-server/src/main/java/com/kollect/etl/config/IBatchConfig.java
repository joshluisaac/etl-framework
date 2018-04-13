package com.kollect.etl.config;

import java.util.Map;

public interface IBatchConfig {
  
  Map<String, CrudProcessHolder> crudHolderMap(Map<String, CrudProcessHolder> map, final String dataSrc);

}
