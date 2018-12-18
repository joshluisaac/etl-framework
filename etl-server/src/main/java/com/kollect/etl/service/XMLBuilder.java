package com.kollect.etl.service;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kollect.etl.entity.XMLDataType;
import com.kollect.etl.entity.app.DCXMLFieldData;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class XMLBuilder implements IXMLBuilder{


	@Autowired
	public XMLBuilder(String outputFile, String rootElementName, HashMap<String, String> rootAttributes) {
		try {
			this.outputFile=outputFile;
			this.dbFactory = DocumentBuilderFactory.newInstance();
			this.dBuilder = this.dbFactory.newDocumentBuilder();
			this.doc = this.dBuilder.newDocument();

			this.root = doc.createElement(rootElementName);
			for (String key : rootAttributes.keySet())
			{
				this.addAttributes(this.root, key, rootAttributes.get(key));
			}
			this.doc.appendChild(this.root);
//			this.addElement(this.root, "table",tableName);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void addField(DCXMLFieldData xmlFieldData)
	{
		
		Element fieldElement = doc.createElement("field");
        this.addAttributes(fieldElement, "isoptional", xmlFieldData.isOptional.toString());
        this.addAttributes(fieldElement, "isexternal", xmlFieldData.isExternal.toString());
        this.addAttributes(fieldElement, "iskey", xmlFieldData.isKey.toString());
        this.root.appendChild(fieldElement);

        
        this.addElement(fieldElement, "name", xmlFieldData.colName);
        this.addElement(fieldElement, "default", xmlFieldData.defaultVal);
        
        Element locationELement = doc.createElement("location");
        this.addAttributes(locationELement, "start", xmlFieldData.start);
        this.addAttributes(locationELement, "end", xmlFieldData.end);
        fieldElement.appendChild(locationELement);
        
        //<handler config="" name="com.profitera.dc.handler.LongHandler" />
        Element handlerELement = doc.createElement("handler");
        this.addAttributes(handlerELement, "config", "");
        String handlerPath="";
        switch (xmlFieldData.colType) {
	        case 1:
	        	handlerPath="com.profitera.dc.handler.LongHandler";
	        	break;
			case 2:
				handlerPath="com.profitera.dc.handler.CurrencyHandler";
				break;
			case 3:
				handlerPath="com.profitera.dc.handler.StringHandler";
				break;
			case 4:
				handlerPath="com.profitera.dc.handler.DefaultDateHandler";
				break;
			case 5: 
				handlerPath="com.profitera.dc.handler.YNBooleanHandler";
				break;        
		}
        this.addAttributes(handlerELement, "name", handlerPath);
        fieldElement.appendChild(handlerELement);
        
        
        if (xmlFieldData.isLookup)
        {
            Element lookupELement = doc.createElement("lookup");
            for(Map.Entry<String,String> attrMap: xmlFieldData.lookup.entrySet()) {
            	String key = attrMap.getKey();
            	String value = attrMap.getValue();
            	if(key.equals("fullcache"))
            		this.addElement(lookupELement, key);
            	else
            		this.addElement(lookupELement, key, value);
            }
            fieldElement.appendChild(lookupELement);
        	//this.addElement(fieldElement, "lookup");
        }

	}

	@Override
	public void addElement(Element el, String name)
	{
        Element nameELement = doc.createElement(name);
        el.appendChild(nameELement);
	}

	
	@Override
	public void addElement(Element el, String name, String value)
	{
        Element nameELement = doc.createElement(name);
        nameELement.appendChild(doc.createTextNode(value));
        el.appendChild(nameELement);
	}
	
	@Override
	public void addElement(Element el, String name,  HashMap<String, String> attributes)
	{
        Element nameELement = doc.createElement(name);
        for(Map.Entry<String,String> attrMap: attributes.entrySet()) {
            this.addAttributes(nameELement, attrMap.getKey(), attrMap.getValue());
        }
        el.appendChild(nameELement);
	}
	
	
	/**
	 * 
	 * @param mainElement is the element that we want add attributes and child
	 * @param contentXML the childs informations
	 */
	@Override
	public void addElement(Element mainElement,XMLDataType contentXML)
	{
		Element newELement = doc.createElement(contentXML.name);
        for(Map.Entry<String,String> attrMap: contentXML.attributes.entrySet()) {
            this.addAttributes(newELement, attrMap.getKey(), attrMap.getValue());
        }
        if (contentXML.text.length()!=0) {
        	newELement.appendChild(doc.createTextNode(contentXML.text));
        }else {
			for (XMLDataType childs : contentXML.childs)
				this.addElement(newELement, childs);
        }
		mainElement.appendChild(newELement);
	}
	
	@Override
	public void addRootElement(XMLDataType contentXML)
	{
		this.addElement(this.root,contentXML);
	}
	
	@Override
	public void addRootElement(String name) 
	{
		this.addElement(this.root,name);
	}

	@Override
	public void addRootElement(String name, String value)
	{
		this.addElement(this.root,name,value);
	}

	@Override
	public void addRootElement( String name,  HashMap<String, String> attributes)
	{
		this.addElement(this.root, name, attributes);
	}
	
	@Override
	public void addAttributes(Element el, String name, String value)
	{
        Attr attr = doc.createAttribute(name);
        attr.setValue(value);
        el.setAttributeNode(attr);
        
	}
	
	public void printXML()
	{
		try {
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File("/home/poweretl-master/poweretl/etl-server/loadinxmlconfig/"+this.outputFile+".xml"));
	        transformer.transform(source, result);
	        
	        // Output to console for testing
//	        StreamResult consoleResult = new StreamResult(System.out);
//	        transformer.transform(source, consoleResult);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	


	public String toString() {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}


	
	
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private Element root;
	private String outputFile;
	
}
