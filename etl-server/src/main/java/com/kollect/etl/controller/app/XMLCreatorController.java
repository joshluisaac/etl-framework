package com.kollect.etl.controller.app;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kollect.etl.util.dctemporary.PostgresPtr;
import com.kollect.etl.util.dctemporary.DCBuilder;
import com.kollect.etl.util.dctemporary.XMLDataType;
import com.kollect.etl.util.dctemporary.XMLFieldData;


@Controller
public class XMLCreatorController {
	
	
	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");

	
	@GetMapping("/xmlcreator")
    public String showCreatorPage(Model model) {
		HashMap<Integer,String> projects = mainConPtr.FillComboBox("select id,name from pi_project;");
		model.addAttribute("projects", projects);
        return "xmlcreator";
    }


	@PostMapping("/xmlcreator")
	public ResponseEntity<String> creatingXML(@RequestParam Map<String,String> request,@RequestParam(value="lookuptypepost[]", required=false) String[] lookuptypepost,
											  @RequestParam(value="querypost[]",required=false) String[] querypost){
		
//		System.out.println(request.keySet().toString());
//		System.out.println(request.toString());
		String isDefault=request.get("isdefault");
		DCBuilder dcBuilder;
		HashMap<String, String> setAttr = new HashMap<String,String>();

		if ( isDefault == null ) 
		{
				setAttr.put("delimiter", request.get("defdelimm"));
				setAttr.put("fullcache", request.get("defcache"));
				setAttr.put("mode", request.get("defmode"));
				setAttr.put("padline", request.get("defpadline"));
				setAttr.put("type", request.get("deftype"));
				setAttr.put("verifyfilekey", request.get("defverify"));
				dcBuilder = new DCBuilder(request.get("tablename"),request.get("name"),setAttr);
				setAttr.clear();
		}
		else
			dcBuilder = new DCBuilder(request.get("tablename"),request.get("name"));
		
		
		//Enumeration<String> params = request.getParameterNames(); 
		
		Boolean isFirst=false;
		XMLFieldData xmlFieldData = new XMLFieldData();

		dcBuilder.AddRootElement("generatedkey", request.get("generatedkey"));
		dcBuilder.AddRootElement("generatedkeyseqname", request.get("generatedkeyseqname"));
		
		
		setAttr.put("key", request.get("refreshdata"));
		setAttr.put("timeout", request.get("refreshtimeout"));
		dcBuilder.AddRootElement("refreshdata", setAttr);
		setAttr.clear();
		
		XMLDataType postXML = new XMLDataType();
		String isPost=request.get("ispost");
		if ( isPost != null ) 
			if (isPost.equals("on"))
			{
				
				postXML.name="post";
				if (lookuptypepost.length>0 && querypost.length>0) {
					for (int i = 0; i < lookuptypepost.length; i++) { 
						XMLDataType child = new XMLDataType();
						child.name=lookuptypepost[i];
						child.text=querypost[i];
						postXML.childs.add(child);
					}
				}
				dcBuilder.AddRootElement(postXML);
			}
		//System.out.println("salammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");		
		for(String params : request.keySet()){
		 String paramName = params;
		 //System.out.println(paramName);
		 int startChar=paramName.indexOf("[");
		 int endChar=paramName.indexOf("]");
		 //System.out.println(paramName+", "+startChar+", "+endChar);
		 if(startChar>0) {
			 String tagType=paramName.substring(0,startChar);
			 String rowId=paramName.substring(startChar+1,endChar);
			 
			 //System.out.println("character [ index: "+charIndex+"    tag type:  "+tagType);
			 switch(tagType) {
				 case "fieldname":
					 if(isFirst) {
						 dcBuilder.AddField(xmlFieldData);
						 xmlFieldData.ClearData();
					 }
					 xmlFieldData.colName=request.get(paramName);
					 //System.out.println("new field: "+request.getParameter(paramName));

					 isFirst=true;
					 break;
				 case "start":
					 xmlFieldData.start=request.get(paramName);
					 break;
				 case "end":
					 xmlFieldData.end=request.get(paramName);
					 break;
				 case "iskey":
					 if(request.get(paramName).equals("on"))
						 xmlFieldData.isKey=true;					 
					 break;
				 case "isoptional":
					 if(request.get(paramName).equals("on"))
						 xmlFieldData.isOptional=true;
					 break;
				 case "isexternal":
					 if(request.get(paramName).equals("on"))
						 xmlFieldData.isExternal=true;
					 break;
				 case "defaultval":
					 xmlFieldData.defaultVal=(request.get(paramName)==null?"":request.get(paramName));
					 break;
				 case "coltype":
					 xmlFieldData.colType=Integer.valueOf(request.get(paramName));
					 break;
				 case "islookup":
					 if(request.get(paramName).equals("on")) {
//						System.out.println("lokuptype is:		 "+request.get("lookuptype"+rowId+"[]"));
//						System.out.println("query is: "+request.get("query"+rowId+"[]"));
						xmlFieldData.isLookup=true;
						int i=0;
						while(request.containsKey("lookuptype"+rowId+"["+i+"]")) {
							xmlFieldData.lookup.put(request.get("lookuptype"+rowId+"["+i+"]"),request.get("query"+rowId+"["+i+"]"));
							++i;
						}
					 }
					 break;
				 case "nullable":
				 case "lookuptype":
				 case "query":
				 case "lookuptypepost":
				 case "querypost":
					 break;
				 default:
					 //System.out.println("Tag type is Unkhown: "+tagType+" the value is: "+request.get(paramName));
			 }
		 //System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		 }//if(charIndex>0) {
		}
		 if(isFirst) 
			 dcBuilder.AddField(xmlFieldData);
		dcBuilder.PrintXML();
		
		return new ResponseEntity<>(dcBuilder.ToString(), HttpStatus.OK);
	}
	
	
	@PostMapping("/xmltest")
	public ResponseEntity<String> creatingXMLMain(HttpServletRequest request){
		
		String isDefault=request.getParameter("isdefault");
		DCBuilder dcBuilder;
		HashMap<String, String> setAttr = new HashMap<String,String>();

		if ( isDefault == null ) 
		{
				setAttr.put("delimiter", request.getParameter("defdelimm"));
				setAttr.put("fullcache", request.getParameter("defcache"));
				setAttr.put("mode", request.getParameter("defmode"));
				setAttr.put("padline", request.getParameter("defpadline"));
				setAttr.put("type", request.getParameter("deftype"));
				setAttr.put("verifyfilekey", request.getParameter("defverify"));
				dcBuilder = new DCBuilder(request.getParameter("tablename"),request.getParameter("name"),setAttr);
				setAttr.clear();
		}
		else
			dcBuilder = new DCBuilder(request.getParameter("tablename"),request.getParameter("name"));
		
		
		Enumeration<String> params = request.getParameterNames(); 
		
		Boolean isFirst=false;
		XMLFieldData xmlFieldData = new XMLFieldData();

		dcBuilder.AddRootElement("generatedkey", request.getParameter("generatedkey"));
		dcBuilder.AddRootElement("generatedkeyseqname", request.getParameter("generatedkeyseqname"));
		
		
		setAttr.put("key", request.getParameter("refreshdata"));
		setAttr.put("timeout", request.getParameter("refreshtimeout"));
		dcBuilder.AddRootElement("refreshdata", setAttr);
		setAttr.clear();
		
		XMLDataType postXML = new XMLDataType();
		String isPost=request.getParameter("ispost");
		if ( isPost != null ) 
			if (isPost.equals("on"))
			{
				postXML.name="post";
			 	String[] postType=request.getParameterValues("lookuptypepost[]");
				String[] query=request.getParameterValues("querypost[]");
				if (postType != null && postType.length != 0 && query != null && query.length != 0) {
					for (int i = 0; i < postType.length; i++) { 
						XMLDataType child = new XMLDataType();
						child.name=postType[i];
						child.text=query[i];
						postXML.childs.add(child);
					}
				}
				dcBuilder.AddRootElement(postXML);
			}
		//System.out.println("salammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");		
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 //System.out.println(paramName);
		 int startChar=paramName.indexOf("[");
		 int endChar=paramName.indexOf("]");
		 //System.out.println(paramName+", "+startChar+", "+endChar);
		 if(startChar>0) {
			 String tagType=paramName.substring(0,startChar);
			 String rowId=paramName.substring(startChar+1,endChar);
			 
			 //System.out.println("character [ index: "+charIndex+"    tag type:  "+tagType);
			 switch(tagType) {
				 case "fieldname":
					 if(isFirst) {
						 dcBuilder.AddField(xmlFieldData);
						 xmlFieldData.ClearData();
					 }
					 xmlFieldData.colName=request.getParameter(paramName);
					 //System.out.println("new field: "+request.getParameter(paramName));

					 isFirst=true;
					 break;
				 case "start":
					 xmlFieldData.start=request.getParameter(paramName);
					 break;
				 case "end":
					 xmlFieldData.end=request.getParameter(paramName);
					 break;
				 case "iskey":
					 if(request.getParameter(paramName).equals("on"))
						 xmlFieldData.isKey=true;					 
					 break;
				 case "isoptional":
					 if(request.getParameter(paramName).equals("on"))
						 xmlFieldData.isOptional=true;
					 break;
				 case "isexternal":
					 if(request.getParameter(paramName).equals("on"))
						 xmlFieldData.isExternal=true;
					 break;
				 case "defaultval":
					 xmlFieldData.defaultVal=(request.getParameter(paramName)==null?"":request.getParameter(paramName));
					 break;
				 case "coltype":
					 xmlFieldData.colType=Integer.valueOf(request.getParameter(paramName));
					 break;
				 case "islookup":
					 if(request.getParameter(paramName).equals("on"))
						 xmlFieldData.isLookup=true;
				 	String[] lookupType=request.getParameterValues("lookuptype"+rowId+"[]");
					String[] query=request.getParameterValues("query"+rowId+"[]");
					if (lookupType != null && lookupType.length != 0 && query != null && query.length != 0) {
						for (int i = 0; i < lookupType.length; i++) { 
							xmlFieldData.lookup.put(lookupType[i], query[i]);
						}
					}
					 break;
				 case "nullable":
				 case "lookuptype":
				 case "query":
					 break;
				 default:
					 System.out.println("Tag type is Unkhown: "+tagType+" the value is: "+request.getParameter(paramName));
			 }
		 //System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		 }//if(charIndex>0) {
		}
		 if(isFirst) 
			 dcBuilder.AddField(xmlFieldData);
		dcBuilder.PrintXML();
		
		return new ResponseEntity<>(dcBuilder.ToString(), HttpStatus.OK);
	}

	
}
