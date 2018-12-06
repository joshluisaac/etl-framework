package com.kollect.etl.util.dctemporary;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class XMLDataType {
	
	public XMLDataType() 
	{
		this.attributes = new LinkedHashMap<String, String>();
		this.childs = new LinkedHashSet<XMLDataType>();
		ClearData();
	}
	
	public void ClearData() 
	{
		this.name="";
		this.text="";
		this.attributes.clear();
		this.childs.clear();
	}
		
	public String name;
	public String text;
	public LinkedHashMap<String, String> attributes;
	public LinkedHashSet<XMLDataType> childs;
}
