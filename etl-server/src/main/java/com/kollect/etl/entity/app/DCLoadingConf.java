package com.kollect.etl.entity.app;

public class DCLoadingConf {
	
	Integer id=0,project_id,dbinfo_id;
	String name,tablename,xmlconfig,description;
	
	public DCLoadingConf(Integer project_id, Integer dbinfo_id, String name,
						 String tablename, String xmlconfig, String description) {
		this.project_id=project_id;
		this.dbinfo_id=dbinfo_id;
		this.name=name;
		this.tablename=tablename;
		this.xmlconfig=xmlconfig;
		this.description=description;
	}
	
	public DCLoadingConf(int id, Integer project_id, Integer dbinfo_id, String name,
						 String tablename, String xmlconfig, String description) {
		this(project_id,dbinfo_id,name,tablename,xmlconfig,description);
		this.id=id;
	}
}
