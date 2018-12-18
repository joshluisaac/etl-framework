package com.kollect.etl.service;

import java.util.HashMap;


import org.w3c.dom.Element;

import com.kollect.etl.entity.XMLDataType;
import com.kollect.etl.entity.app.DCXMLFieldData;

public interface IXMLBuilder {
	public void addField(DCXMLFieldData xmlFieldData);
	public void addElement(Element el, String name);
	public void addElement(Element el, String name, String value);
	public void addElement(Element el, String name,  HashMap<String, String> attributes);
	public void addElement(Element mainElement,XMLDataType contentXML);
	public void addRootElement(XMLDataType contentXML);
	public void addRootElement(String name);
	public void addRootElement(String name, String value);
	public void addRootElement( String name,  HashMap<String, String> attributes);
	public void addAttributes(Element el, String name, String value);
	
	public void printXML();
	public String toString();

}
