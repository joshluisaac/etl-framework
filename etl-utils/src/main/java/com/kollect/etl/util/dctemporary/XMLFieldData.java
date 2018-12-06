package com.kollect.etl.util.dctemporary;


import java.util.LinkedHashMap;

public class XMLFieldData {
	public XMLFieldData() {
		lookup = new LinkedHashMap<String,String>();
		this.ClearData();
	}
	public void ClearData() {
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
