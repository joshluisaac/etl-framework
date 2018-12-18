package com.kollect.etl.entity.app;

public class ProjectEntity {
	int id=0;
	String name,customer,description;
	public ProjectEntity(String name, String customer, String description) {
		
		this.name=name;
		this.customer=customer;
		this.description=description;
	}
	public ProjectEntity(int id, String name, String customer, String description) {
		this(name,customer,description);
		this.id=id;
	}
}
