package com.kollect.etl.service.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.kollect.etl.entity.XMLDataType;
import com.kollect.etl.entity.app.Combo;
import com.kollect.etl.entity.app.DCLoadingConf;
import com.kollect.etl.entity.app.DCXMLFieldData;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.IXMLBuilder;
import com.kollect.etl.service.XMLBuilder;


@Service
public class DCLoadingXMLService {
	private IReadWriteServiceProvider rwProvider;
	private String dataSource;
	//private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	@Autowired
    public DCLoadingXMLService (IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

	
	public Object loadCreatorPage(Model model) {
		ProjectManagementService project= new ProjectManagementService(rwProvider,dataSource);
		List<Combo> projects = project.fillProjectsCombo();
		model.addAttribute("projects", projects);
		return "xmlcreator";
	}

	
	public Object loadDCListPage(Model model) {
		ProjectManagementService project= new ProjectManagementService(rwProvider,dataSource);
		List<Combo> projects = project.fillProjectsCombo();
		model.addAttribute("projects", projects);
		return "loadingxmllist";
	}
	

	public ResponseEntity<InputStreamResource> downloadXML(String name) {
		File file = new File("/home/poweretl-master/poweretl/etl-server/loadinxmlconfig/"+name+".xml");
		
		String[] outputName = name.split("_");
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
	               // Content-Disposition
	               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +outputName[2]+".xml")
	               // Content-Type
	               //.contentType(mediaType)
	               // Contet-Length
	               .contentLength(file.length()) //
	               .body(resource);
		} catch (Exception e) {
			 return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	
	public ResponseEntity<String> getLoadingConfList(Integer project_id, Integer dbid, String tablename) {
		try {
			HashMap<String, Object> newObj=new HashMap<String, Object>();
			newObj.put("project_id", project_id);
			newObj.put("dbinfo_id", dbid);
			newObj.put("tablename", tablename);
			List<Object> databaseList = this.rwProvider.executeQuery(dataSource, "getLoadingConf",newObj);
			Gson gson = new Gson();
			String result=gson.toJson(databaseList);
			return new ResponseEntity<>(result, HttpStatus.OK);
//			return new ResponseEntity<>("unkhown", HttpStatus.OK);			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}	
	
	
	public  ResponseEntity<String> creatingXML(HttpServletRequest request, Integer project_id, Integer dbinfo_id, String name,
												String tablename, String description,Integer action){
		String isDefault=request.getParameter("isdefault");
		String filename=project_id+"_"+dbinfo_id+"_"+name;
		IXMLBuilder dcBuilder;
		HashMap<String, String> setAttr = new HashMap<String,String>();

		
		if ( isDefault == null ) 
		{
			setAttr.put("delimiter", request.getParameter("defdelimm"));
			setAttr.put("fullcache", request.getParameter("defcache"));
			setAttr.put("mode", request.getParameter("defmode"));
			setAttr.put("padline", request.getParameter("defpadline"));
			setAttr.put("type", request.getParameter("deftype"));
			setAttr.put("verifyfilekey", request.getParameter("defverify"));
			
		}
		else {
			setAttr.put("delimiter", "|");
			setAttr.put("fullcache", "false");
			setAttr.put("mode", "mixed");
			setAttr.put("padline", "true");
			setAttr.put("type", "del");
			setAttr.put("verifyfilekey", "false");
			
		}

		dcBuilder = new XMLBuilder(filename, "load", setAttr);
		setAttr.clear();

		dcBuilder.addRootElement(tablename);
		
		Enumeration<String> params = request.getParameterNames(); 
		
		Boolean isFirst=false;
		DCXMLFieldData xmlFieldData = new DCXMLFieldData();

		dcBuilder.addRootElement("generatedkey", request.getParameter("generatedkey"));
		dcBuilder.addRootElement("generatedkeyseqname", request.getParameter("generatedkeyseqname"));
		
		
		setAttr.put("key", request.getParameter("refreshdata"));
		setAttr.put("timeout", request.getParameter("refreshtimeout"));
		dcBuilder.addRootElement("refreshdata", setAttr);
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
				dcBuilder.addRootElement(postXML);
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
						 dcBuilder.addField(xmlFieldData);
						 xmlFieldData.clearData();
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
			 dcBuilder.addField(xmlFieldData);
		dcBuilder.printXML();
		String xmlconfig=dcBuilder.toString();
		switch (action) {
			case 1:
					DCLoadingConf dCLoadingConf = new DCLoadingConf(project_id, dbinfo_id, name, tablename, xmlconfig, description);
					try {
						int insertCount=this.rwProvider.insertQuery(dataSource, "insertLoadingConf", dCLoadingConf);
						if (insertCount != 0)
							return new ResponseEntity<>("Inserted successfuly", HttpStatus.OK);
						else
							return new ResponseEntity<>("Nothing inserted", HttpStatus.OK);
						
					}catch(Exception e) {
						return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
					}
			case 4:
				return new ResponseEntity<>(xmlconfig, HttpStatus.OK);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);				
		}
		
		
	}
}





//@PostMapping("/xmlcreator")
//public ResponseEntity<String> creatingXML(@RequestParam Map<String,String> request,@RequestParam(value="lookuptypepost[]", required=false) String[] lookuptypepost,
//										  @RequestParam(value="querypost[]",required=false) String[] querypost){
//	
////	System.out.println(request.keySet().toString());
////	System.out.println(request.toString());
//	String isDefault=request.get("isdefault");
//	IXMLBuilder dcBuilder;
//	HashMap<String, String> setAttr = new HashMap<String,String>();
//
//	if ( isDefault == null ) 
//	{
//		setAttr.put("delimiter", request.get("defdelimm"));
//		setAttr.put("fullcache", request.get("defcache"));
//		setAttr.put("mode", request.get("defmode"));
//		setAttr.put("padline", request.get("defpadline"));
//		setAttr.put("type", request.get("deftype"));
//		setAttr.put("verifyfilekey", request.get("defverify"));
//		
//	}
//	else {
//		setAttr.put("delimiter", "|");
//		setAttr.put("fullcache", "false");
//		setAttr.put("mode", "mixed");
//		setAttr.put("padline", "true");
//		setAttr.put("type", "del");
//		setAttr.put("verifyfilekey", "false");
//	}
//	dcBuilder = new XMLBuilder(request.get("name"), "load", setAttr);
//	setAttr.clear();
//
//	dcBuilder.addRootElement(request.get("tablename"));
//	
//	//Enumeration<String> params = request.getParameterNames(); 
//	
//	Boolean isFirst=false;
//	DCXMLFieldData xmlFieldData = new DCXMLFieldData();
//	dcBuilder.addRootElement("generatedkey", request.get("generatedkey"));
//	dcBuilder.addRootElement("generatedkeyseqname", request.get("generatedkeyseqname"));
//	
//	
//	setAttr.put("key", request.get("refreshdata"));
//	setAttr.put("timeout", request.get("refreshtimeout"));
//	dcBuilder.addRootElement("refreshdata", setAttr);
//	setAttr.clear();
//	
//	XMLDataType postXML = new XMLDataType();
//	String isPost=request.get("ispost");
//	if ( isPost != null ) 
//		if (isPost.equals("on"))
//		{
//			
//			postXML.name="post";
//			if (lookuptypepost.length>0 && querypost.length>0) {
//				for (int i = 0; i < lookuptypepost.length; i++) { 
//					XMLDataType child = new XMLDataType();
//					child.name=lookuptypepost[i];
//					child.text=querypost[i];
//					postXML.childs.add(child);
//				}
//			}
//			dcBuilder.addRootElement(postXML);
//		}
//	//System.out.println("salammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");		
//	for(String params : request.keySet()){
//	 String paramName = params;
//	 //System.out.println(paramName);
//	 int startChar=paramName.indexOf("[");
//	 int endChar=paramName.indexOf("]");
//	 //System.out.println(paramName+", "+startChar+", "+endChar);
//	 if(startChar>0) {
//		 String tagType=paramName.substring(0,startChar);
//		 String rowId=paramName.substring(startChar+1,endChar);
//		 
//		 //System.out.println("character [ index: "+charIndex+"    tag type:  "+tagType);
//		 switch(tagType) {
//			 case "fieldname":
//				 if(isFirst) {
//					 dcBuilder.addField(xmlFieldData);
//					 xmlFieldData.clearData();
//				 }
//				 xmlFieldData.colName=request.get(paramName);
//				 //System.out.println("new field: "+request.getParameter(paramName));
//
//				 isFirst=true;
//				 break;
//			 case "start":
//				 xmlFieldData.start=request.get(paramName);
//				 break;
//			 case "end":
//				 xmlFieldData.end=request.get(paramName);
//				 break;
//			 case "iskey":
//				 if(request.get(paramName).equals("on"))
//					 xmlFieldData.isKey=true;					 
//				 break;
//			 case "isoptional":
//				 if(request.get(paramName).equals("on"))
//					 xmlFieldData.isOptional=true;
//				 break;
//			 case "isexternal":
//				 if(request.get(paramName).equals("on"))
//					 xmlFieldData.isExternal=true;
//				 break;
//			 case "defaultval":
//				 xmlFieldData.defaultVal=(request.get(paramName)==null?"":request.get(paramName));
//				 break;
//			 case "coltype":
//				 xmlFieldData.colType=Integer.valueOf(request.get(paramName));
//				 break;
//			 case "islookup":
//				 if(request.get(paramName).equals("on")) {
////					System.out.println("lokuptype is:		 "+request.get("lookuptype"+rowId+"[]"));
////					System.out.println("query is: "+request.get("query"+rowId+"[]"));
//					xmlFieldData.isLookup=true;
//					int i=0;
//					while(request.containsKey("lookuptype"+rowId+"["+i+"]")) {
//						xmlFieldData.lookup.put(request.get("lookuptype"+rowId+"["+i+"]"),request.get("query"+rowId+"["+i+"]"));
//						++i;
//					}
//				 }
//				 break;
//			 case "nullable":
//			 case "lookuptype":
//			 case "query":
//			 case "lookuptypepost":
//			 case "querypost":
//				 break;
//			 default:
//				 //System.out.println("Tag type is Unkhown: "+tagType+" the value is: "+request.get(paramName));
//		 }
//	 //System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
//	 }//if(charIndex>0) {
//	}
//	 if(isFirst) 
//		 dcBuilder.addField(xmlFieldData);
//	dcBuilder.printXML();
//	
//	return new ResponseEntity<>(dcBuilder.toString(), HttpStatus.OK);
//}
