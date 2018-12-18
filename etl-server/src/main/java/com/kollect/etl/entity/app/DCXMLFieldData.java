package com.kollect.etl.entity.app;


import java.util.LinkedHashMap;

public class DCXMLFieldData {
	public DCXMLFieldData() {
		lookup = new LinkedHashMap<String,String>();
		this.clearData();
	}
	public void clearData() {
		colName="";
		defaultVal="&#x200B";
		start="";
		end="";
		isKey=false;
		isOptional=false;
		isExternal=false;
		isLookup=false;
		lookup.clear();
	}	
	public String colName;
	public int colType;
	public String defaultVal;
	public String start;
	public String end;
	public Boolean isKey;
	public Boolean isExternal;
	public Boolean isOptional;
	public Boolean isLookup;
	public LinkedHashMap<String, String> lookup;
}
