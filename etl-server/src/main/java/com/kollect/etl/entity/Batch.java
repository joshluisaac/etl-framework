package com.kollect.etl.entity;

public class Batch {
    Integer id;
    String code, name, description;
    boolean disable;

    public Batch(String code, String name, String description, boolean disable){
        super();
        this.code = code;
        this.name = name;
        this.description = description;
        this.disable = disable;
    }
    public void setId(int id) {
        this.id = id;
    }
}
