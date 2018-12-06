package com.kollect.etl.controller.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.kollect.etl.util.dctemporary.PostgresPtr;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;



@Controller
public class ValidationController {

	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	private PostgresPtr postgresPtr;
	
	@GetMapping("/validation")
    public String showValidationPage(Model model) {
		HashMap<Integer,String> projects = mainConPtr.FillComboBox("select id,name from pi_project;");
		model.addAttribute("projects", projects);
        return "validation";
    }

	
	
	
	@PostMapping("/validation")
	public ResponseEntity<String> ValidationFunction(HttpServletRequest request){
		int action = Integer.valueOf(request.getParameter("action"));
		Boolean isConnect=false;
		String responseText="";		
		switch (action) {
			case 4:
				try {
					mainConPtr.ExecuteQuery("select * from pi_dbinfo where id="+request.getParameter("dbid"));
					if(mainConPtr.resultSet.next()) {
						postgresPtr= new PostgresPtr(mainConPtr.resultSet.getString(4), mainConPtr.resultSet.getInt(5),mainConPtr.resultSet.getString(3), mainConPtr.resultSet.getString(6), mainConPtr.resultSet.getString(7));
					}
					String validatorId = request.getParameter("validator_id");
					String projectId = request.getParameter("project_id");
				    Part filePart = request.getPart("filename");
				    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
				    
				    InputStream fileContent = filePart.getInputStream();
				    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
				    ArrayList<QueryCheckList> csvCheckList = new ArrayList<>();
				    ArrayList<Integer> csvIgnorList = new ArrayList<>();
				    ArrayList<Integer> csvCount = new ArrayList<>();
				    HashMap<Integer, String> csvTitle = new HashMap<>();
				    

				    
				    CreateQueryList(validatorId, projectId, csvCheckList,csvIgnorList,csvTitle,csvCount);
					int csvColCount = csvCount.get(0);
				    String[] csvColTitle= new String[csvColCount+1];
				    Boolean[] emptyFlags = new Boolean[csvColCount+1];
				    csvColTitle[0]="RowNumber";
					for (int i=1;i<=csvColCount;i++) {
						
						csvColTitle[i]=csvTitle.get(i-1);
						emptyFlags[i-1]=false;
					}
				    System.out.println("size of: "+csvColCount);
					Writer writer = new StringWriter();
					JsonWriter json = new JsonWriter(writer);
				    int count=0;
				    int errorCount=0;
				    
				    
				    
				    Gson gson = new Gson();
				    json.beginArray();
			    	json.beginObject();
                	json.name("colCount");
                    gson.toJson(csvColCount,Integer.class, json);
                    
                    json.name("colTitle");
                	gson.toJson(gson.toJsonTree(csvColTitle),json);

                	json.name("rows");
		    		json.beginArray();
				    while(reader.ready())
				    {
				    	++count;
				    	String csvLine=reader.readLine();
				    	//System.out.println(csvLine);
				    	String[] csvFields = csvLine.split("\\|",-1);
//				    	int c = 0;
//				    	for (String x: csvFields)
//			    			System.out.println((c++)+": "+x+", ");
//				    	System.out.println();
				    	//System.out.println("length is: "+csvFields.length+", first: "+csvFields[0]);
				    	Boolean isAccurateRow=true;

				    	
				    	if(csvFields.length==csvColCount)
				    	{
				    		//System.out.println("row: "+count);
				    		Boolean[] checkFlags=CheckDB(csvFields, csvCheckList, csvIgnorList);
				    		for(Boolean checkFlag : checkFlags)
				    			if(checkFlag==false) {
				    				isAccurateRow=false;
		    	                    

						    		
						    		json.beginObject();
		    	                	json.name("rowNumber");
		    	                	System.out.println("creating error row number: "+count);
		    	                    gson.toJson(count,Integer.class, json);

		    	                    json.name("accuracy");
		    	                	gson.toJson(gson.toJsonTree(checkFlags),json);
		    	                    json.endObject();

				    				break;
				    			}
				    	}
				    	else {
				    		isAccurateRow=false;
				    		json.beginObject();
    	                	json.name("rowNumber");
    	                	System.out.println("creating error row number: "+count);
    	                    gson.toJson(count,Integer.class, json);

    	                    json.name("accuracy");
    	                	gson.toJson(gson.toJsonTree(emptyFlags),json);
    	                    json.endObject();

				    	}
				    	if(isAccurateRow==false) {
				    		++errorCount;
				    		System.out.println(count+" line not available on database");
				    	}
				    	
				    }
                    json.endArray();

                    json.name("total");
                    gson.toJson(count,Integer.class, json);		    		

                    json.name("error");
                    gson.toJson(errorCount,Integer.class, json);		    		
                    json.endObject();

				    json.endArray();
				    System.out.println(writer.toString());

				    System.out.println(fileName);
				    responseText=writer.toString();
				    isConnect=true;
				    mainConPtr.DisConnect();
				    postgresPtr.DisConnect();
				    //check against the 
				    
				}catch (Exception e) {
            		responseText=e.getMessage();

				}finally {

				}
				break;
		}
		if (isConnect==true)
			return new ResponseEntity<>(responseText, HttpStatus.OK);
		else
			return new ResponseEntity<>(responseText, HttpStatus.FAILED_DEPENDENCY);
	}

	
	
	
	private void CreateQueryList(String vaildatorId, String projectId, ArrayList<QueryCheckList> csvCheckList,
								 ArrayList<Integer> ignoreList, HashMap<Integer, String> csvTitle, ArrayList<Integer> csvCount)
	{
		String query="select * from pi_validationitems where validator_id="+vaildatorId+" order by sequence";
		String columns="";
		String tableName="";
		String conditions="";
		String id="";
		ArrayList<String> map = new ArrayList<String>();
		ArrayList<String> type = new ArrayList<>();
		
		int oldSequence=1;
		Boolean isFirst=false;
		String theSequence;
		int csvColCount=0;
		try {
			
			mainConPtr.ExecuteQuery(query);
			while(mainConPtr.resultSet.next()) {
				//++csvColCount;
				Integer csvOrder=mainConPtr.resultSet.getInt(2/*csvorder*/);
				if (csvOrder>csvColCount)
					csvColCount=csvOrder;
				--csvOrder;
				//System.out.println("man injammmmmmmmm "+csvOrder);
				csvTitle.put(csvOrder,mainConPtr.resultSet.getString(3/*columnname*/));
				//System.out.println("rad kardammmmmmmmmm "+mainConPtr.resultSet.getString(3/*columnname*/));
				if(mainConPtr.resultSet.getInt(8/*sequence*/)==-1) {
					ignoreList.add(csvOrder);
					continue;
				}
					
				int sequence=mainConPtr.resultSet.getInt(7/*sequence*/);
				if (oldSequence!=sequence && isFirst) {
					//put the query to map

					if(columns.length()>0)
						columns = columns.substring(0,(columns.length()-1));
					if(conditions.length()==0)
						theSequence="select "+columns+" from "+tableName+" where "+conditions+" and tenant_id="+projectId+";";
					else
						theSequence="select "+columns+" from "+tableName+" where "+conditions+" and tenant_id="+projectId+";";
					QueryCheckList csvCheckNode = new QueryCheckList();
					csvCheckNode.seq=oldSequence;
					csvCheckNode.query=theSequence;
					csvCheckNode.map = (ArrayList<String>) map.clone();
					csvCheckNode.type = (ArrayList<String>)type.clone();
//					System.out.println(theSequence);
//					System.out.println(map.toString());
					csvCheckList.add(csvCheckNode);
					oldSequence=sequence;
					columns="";
					tableName="";
					conditions="";
					map.clear();
					type.clear();
				}
				columns+=mainConPtr.resultSet.getString(5/*columnname*/)+",";

				map.add(csvOrder.toString());
				type.add(mainConPtr.resultSet.getString(8/*type*/));
				if(tableName.length()==0)
					tableName=mainConPtr.resultSet.getString(4/*tablename*/);
				if(sequence==0) {
					id=mainConPtr.resultSet.getString(6/*conditions*/);
					columns+=id+",";
					conditions+=mainConPtr.resultSet.getString(5/*columnname*/)+"="+"$"+csvOrder.toString();
					map.add("$"+id);
					type.add("0");
				}
				else if (conditions.length()==0)
				{
					conditions+=mainConPtr.resultSet.getString(6/*conditions*/);
				}
				isFirst=true;
				//columns should add the 
			}
			if(columns.length()>0)
				columns = columns.substring(0,(columns.length()-1));
			if(conditions.length()==0)
				theSequence="select "+columns+" from "+tableName+" where "+conditions+" and tenant_id="+projectId+";";
			else
				theSequence="select "+columns+" from "+tableName+" where "+conditions+" and tenant_id="+projectId+";";
			QueryCheckList csvCheckNode = new QueryCheckList();
			csvCheckNode.seq=oldSequence;
			csvCheckNode.query=theSequence;
			csvCheckNode.map = (ArrayList<String>) map.clone();
			csvCheckNode.type = (ArrayList<String>)type.clone();
//			System.out.println(theSequence);
//			System.out.println(map.toString());
			csvCheckList.add(csvCheckNode);
			csvCount.add(csvColCount);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
		}
	
	}
	
	
	
	
	
	
	private Boolean[] CheckDB(String[] csvFields,  ArrayList<QueryCheckList> csvCheckList,ArrayList<Integer> csvIgnoreList) {
		HashMap<String, String> ids = new HashMap<>();
		String query= "";
		Boolean flags[] = new Boolean[csvFields.length];
		for (int i = 0; i < flags.length; i++) {
			flags[i]=false;
		}
		for(Integer csvIgnoreNode: csvIgnoreList)
			flags[csvIgnoreNode]=true;
		
		//System.out.println("checklist size is:"+csvCheckList.size());
		try {
			for (QueryCheckList csvCheckNode : csvCheckList) {
				query= csvCheckNode.query;
//				System.out.println(query);
//				System.out.println(csvCheckNode.map.toString());
				int charIndex=0;
				String tempquery=query;
				do {
					charIndex = tempquery.indexOf('$');
					if(charIndex>0) {
						
						String tempStr=tempquery.substring(charIndex+1);
						int lastIndex = tempStr.indexOf(" ");
						String word=tempStr.substring(0, lastIndex);
						try {
							int csvId= Integer.parseInt(word);
							// 1 and 16 problems
							query=query.replace("$"+csvId+" ", "'"+csvFields[csvId]+"' ");
						} catch (NumberFormatException e) {}
						tempquery=tempquery.substring(charIndex+1);
					}
				}while (charIndex>0);
		     
				//replace ids isn query
		        for(Map.Entry<String,String> id: ids.entrySet()) {
		        	//System.out.println("I am going to change "+id.getKey()+" to "+id.getValue());
		        	query=query.replace(id.getKey(), id.getValue());
		        }
//		        System.out.println(query);
//		        System.out.println(csvCheckNode.map.toString());
		        postgresPtr.ExecuteQuery(query);
		        if(!postgresPtr.resultSet.next()) {
		        	for(String csvStr : csvCheckNode.map) {
		        		int csvColumnNumber=Integer.parseInt(csvStr);
		        		flags[csvColumnNumber]=true;
		        		//System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEmpty     : "+csvFields[csvColumnNumber]+"id is: "+csvColumnNumber);
		        	}
		        	//System.out.println("----------------------------------------------------------------------------");
		        	continue;
		        }
		        postgresPtr.resultSet.beforeFirst();
		        while(postgresPtr.resultSet.next()) {
		        	int count = 1;
		        	for(String csvStr : csvCheckNode.map) {
	        			if (csvStr.charAt(0)=='$') {
	        				//System.out.println("I put the "+csvStr+" to "+postgresPtr.resultSet.getString(count+1));
	        				ids.put(csvStr, postgresPtr.resultSet.getString(count));
	        			}
	        			else {
	        				//System.out.println("Im hereeeeeeeeeeeeeeee"+csvStr+ " count is: "+count);
	        				int csvColumnNumber=Integer.parseInt(csvStr);
	        				if (!csvFields[csvColumnNumber].isEmpty())
	        				{
	        					
	        					if(csvCheckNode.type.get(count-1).equals("4")) {
	        						//System.out.println("compare csv     : "+csvFields[csvColumnNumber].substring(0, 19)+"\nand mysql result: "+postgresPtr.resultSet.getString(count));
		        					if (csvFields[csvColumnNumber].substring(0, 19).equals(postgresPtr.resultSet.getString(count)))
		        						flags[csvColumnNumber]=true;
	        						
	        					}else {
	        						//System.out.println("compare csv     : "+csvFields[csvColumnNumber]+"\nand mysql result: "+postgresPtr.resultSet.getString(count));
		        					if (csvFields[csvColumnNumber].equals(postgresPtr.resultSet.getString(count)))
		        						flags[csvColumnNumber]=true;
	        					}
	        				}
	        				else {
	        					//System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEmpty     : "+csvFields[csvColumnNumber]+"id is: "+csvColumnNumber);
	        					flags[csvColumnNumber]=true;
	        				}
	        				//System.out.println("----------------------------------------------------------------------------");
	        			}
		        		++count;
		        	}
		        }
		        postgresPtr.DisConnect();

			}

		} catch (Exception e) {
			System.out.println("CheckDB function Error is: "+e.getMessage());
			// TODO Auto-generated catch block
		}
		return flags;
	}
}


class QueryCheckList{
	public void Clear() {
		seq=0;
		query="";
		map.clear();
		type.clear();
	}
	public int seq=0;
	public String query;
	public ArrayList<String> map;
	public ArrayList<String> type;
}

	
	

