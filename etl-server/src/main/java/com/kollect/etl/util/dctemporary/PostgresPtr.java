package com.kollect.etl.util.dctemporary;

public class PostgresPtr extends DbPtr {

	public PostgresPtr(String databaseURL, int databasePort, String databaseName, String databaseUser,
			String databasePass) {
		super(databaseURL, databasePort, databaseName, databaseUser, databasePass);
		this.InitConnection("org.postgresql.Driver", "postgresql");
	}

}
