package com.kollect.etl.entity;

public class AdminDatabaseProp {
	private String driver,url, additionalArgs, username, password;
	private int port;
	
	public AdminDatabaseProp(String driver, String url, String additionalArgs, String username, String password,
			int port) {
		this.driver = driver;
		this.url = url;
		this.additionalArgs = additionalArgs;
		this.username = username;
		this.password = password;
		this.port = port;
	}

}
