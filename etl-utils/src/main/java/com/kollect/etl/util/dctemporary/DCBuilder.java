package com.kollect.etl.util.dctemporary;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DCBuilder {

	public DCBuilder(String tableName, String outputFile) {
		try {
			this.outputFile=outputFile;
			this.dbFactory = DocumentBuilderFactory.newInstance();
			this.dBuilder = this.dbFactory.newDocumentBuilder();
			this.doc = this.dBuilder.newDocument();

			this.root = doc.createElement("load");
			this.AddAttr(this.root, "delimiter", "|");
			this.AddAttr(this.root, "fullcache", "false");
			this.AddAttr(this.root, "mode", "mixed");
			this.AddAttr(this.root, "padline", "true");
			this.AddAttr(this.root, "type", "del");
			this.AddAttr(this.root, "verifyfilekey", "false");
			this.doc.appendChild(this.root);

			this.AddElement(this.root, "table",tableName);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public DCBuilder(String tableName, String outputFile, HashMap<String, String> attributes) {
		try {
			this.outputFile=outputFile;
			this.dbFactory = DocumentBuilderFactory.newInstance();
			this.dBuilder = this.dbFactory.newDocumentBuilder();
			this.doc = this.dBuilder.newDocument();

			this.root = doc.createElement("load");
			this.AddAttr(this.root, "delimiter", attributes.get("delimiter"));
			this.AddAttr(this.root, "fullcache", attributes.get("fullcache"));
			this.AddAttr(this.root, "mode", attributes.get("mode"));
			this.AddAttr(this.root, "padline", attributes.get("padline"));
			this.AddAttr(this.root, "type", attributes.get("type"));
			this.AddAttr(this.root, "verifyfilekey", attributes.get("verifyfilekey"));
			this.doc.appendChild(this.root);

			this.AddElement(this.root, "table",tableName);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void AddField(XMLFieldData xmlFieldData)
	{
		
		Element fieldElement = doc.createElement("field");
        this.AddAttr(fieldElement, "isoptional", xmlFieldData.isOptional.toString());
        this.AddAttr(fieldElement, "isexternal", xmlFieldData.isExternal.toString());
        this.AddAttr(fieldElement, "iskey", xmlFieldData.isKey.toString());
        this.root.appendChild(fieldElement);

        
        this.AddElement(fieldElement, "name", xmlFieldData.colName);
        this.AddElement(fieldElement, "default", xmlFieldData.defaultVal);
        
        Element locationELement = doc.createElement("location");
        this.AddAttr(locationELement, "start", xmlFieldData.start);
        this.AddAttr(locationELement, "end", xmlFieldData.end);
        fieldElement.appendChild(locationELement);
        
        //<handler config="" name="com.profitera.dc.handler.LongHandler" />
        Element handlerELement = doc.createElement("handler");
        this.AddAttr(handlerELement, "config", "");
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
        this.AddAttr(handlerELement, "name", handlerPath);
        fieldElement.appendChild(handlerELement);
        
        
        if (xmlFieldData.isLookup)
        {
            Element lookupELement = doc.createElement("lookup");
            for(Map.Entry<String,String> attrMap: xmlFieldData.lookup.entrySet()) {
            	String key = attrMap.getKey();
            	String value = attrMap.getValue();
            	if(key.equals("fullcache"))
            		this.AddElement(lookupELement, key);
            	else
            		this.AddElement(lookupELement, key, value);
            }
            fieldElement.appendChild(lookupELement);
        	//this.AddElement(fieldElement, "lookup");
        }

	}

	public void AddElement(Element el, String name)
	{
        Element nameELement = doc.createElement(name);
        el.appendChild(nameELement);
	}

	
	public void AddElement(Element el, String name, String value)
	{
        Element nameELement = doc.createElement(name);
        nameELement.appendChild(doc.createTextNode(value));
        el.appendChild(nameELement);
	}
	
	public void AddElement(Element el, String name,  HashMap<String, String> attributes)
	{
        Element nameELement = doc.createElement(name);
        for(Map.Entry<String,String> attrMap: attributes.entrySet()) {
            this.AddAttr(nameELement, attrMap.getKey(), attrMap.getValue());
        }
        el.appendChild(nameELement);
	}
	
	
	/**
	 * 
	 * @param mainElement is the element that we want add attributes and child
	 * @param contentXML the childs informations
	 */
	public void AddElement(Element mainElement,XMLDataType contentXML)
	{
		Element newELement = doc.createElement(contentXML.name);
        for(Map.Entry<String,String> attrMap: contentXML.attributes.entrySet()) {
            this.AddAttr(newELement, attrMap.getKey(), attrMap.getValue());
        }
        if (contentXML.text.length()!=0) {
        	newELement.appendChild(doc.createTextNode(contentXML.text));
        }else {
			for (XMLDataType childs : contentXML.childs)
				this.AddElement(newELement, childs);
        }
		mainElement.appendChild(newELement);
	}
	
	
	public void AddRootElement(XMLDataType contentXML)
	{
		this.AddElement(this.root,contentXML);
	}
	
	
	public void AddRootElement(String name) 
	{
		this.AddElement(this.root,name);
	}

	public void AddRootElement(String name, String value)
	{
		this.AddElement(this.root,name,value);
	}

	public void AddRootElement( String name,  HashMap<String, String> attributes)
	{
		this.AddElement(this.root, name, attributes);
	}
	
	public void AddAttr(Element el, String name, String value)
	{
        Attr attr = doc.createAttribute(name);
        attr.setValue(value);
        el.setAttributeNode(attr);
        
	}
	
	public void PrintXML()
	{
		try {
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File("/home/mohsen/"+this.outputFile+".xml"));
	        transformer.transform(source, result);
	        
	        // Output to console for testing
//	        StreamResult consoleResult = new StreamResult(System.out);
//	        transformer.transform(source, consoleResult);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	


	public String ToString() {
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
