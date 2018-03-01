package com.kollect.etl.entity;

public class User {
	Integer id = 0;
	String email, firstName, lastName, password;
	boolean enabled;
	String role;
	int tenant_id;
	
	public User(String email, String firstName, String lastName, String password, boolean enabled,
			String role, int tenant_id) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.tenant_id = tenant_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
