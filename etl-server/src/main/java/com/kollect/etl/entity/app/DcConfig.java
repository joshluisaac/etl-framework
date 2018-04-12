package com.kollect.etl.entity.app;

public class DcConfig {
  int id, global_config_id, column_start_position, column_end_position, column_data_handler;
  String column_name, column_default_value, lookup_query,look_insert_query,look_insert_key_query, remark;
  boolean column_is_key,column_is_iexternal,column_is_optional,column_is_cached, disable;
  
  public DcConfig() {}
  
  public DcConfig(int global_config_id, String remark, int column_start_position, int column_end_position, int column_data_handler,
			String column_name, String column_default_value, String lookup_query, String look_insert_query,
			String look_insert_key_query, boolean column_is_key, boolean column_is_iexternal, boolean column_is_optional,
			boolean column_is_cached, boolean disable) {
		this.global_config_id = global_config_id;
		this.remark = remark;
		this.column_start_position = column_start_position;
		this.column_end_position = column_end_position;
		this.column_data_handler = column_data_handler;
		this.column_name = column_name;
		this.column_default_value = column_default_value;
		this.lookup_query = lookup_query;
		this.look_insert_query = look_insert_query;
		this.look_insert_key_query = look_insert_key_query;
		this.column_is_key = column_is_key;
		this.column_is_iexternal = column_is_iexternal;
		this.column_is_optional = column_is_optional;
		this.column_is_cached = column_is_cached;
		this.disable = disable;
	}
  
  public void setGlobalConfigId(int global_config_id) {
    this.global_config_id = global_config_id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  

}
