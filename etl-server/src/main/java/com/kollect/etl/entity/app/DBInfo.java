package com.kollect.etl.entity.app;

public class DBInfo {
	int id=0;
	int project_id,dbPort;
	String dbUrl,dbName,dbUser,dbPass;
	public DBInfo(int project_id, String dbName, String dbUrl, int dbPort, String dbUser, String dbPass) {
		this.project_id=project_id;
		this.dbName=dbName;
		this.dbPass=dbPass;
		this.dbPort=dbPort;
		this.dbUrl=dbUrl;
		this.dbUser=dbUser;
	}
	public DBInfo(int id, int project_id, String dbName, String dbUrl, int dbPort, String dbUser, String dbPass) {
		this(project_id, dbName, dbUrl, dbPort, dbUser, dbPass);
		this.id=id;
	}
}
