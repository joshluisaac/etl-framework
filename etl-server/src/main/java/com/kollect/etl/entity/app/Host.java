package com.kollect.etl.entity.app;

import java.sql.Timestamp;

public class Host {
	Integer id;
	String name, fqdn, username, host;
	int port;
	String publicKey;
	Timestamp createdAt, updatedAt;
	public Host(String name, String fqdn, String username, String host, int port, String publicKey,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.name = name;
		this.fqdn = fqdn;
		this.username = username;
		this.host = host;
		this.port = port;
		this.publicKey = publicKey;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public void setId(int id) {
		this.id = id;
	}
}