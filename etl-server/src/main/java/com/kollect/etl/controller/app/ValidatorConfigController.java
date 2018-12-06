package com.kollect.etl.controller.app;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kollect.etl.util.dctemporary.PostgresPtr;
import com.google.gson.stream.JsonWriter;






@Controller
public class ValidatorConfigController {
	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	@GetMapping("/validator")
    public String showValidatorPage(Model model) {
		HashMap<Integer,String> projects = mainConPtr.FillComboBox("select id,name from pi_project;");
		model.addAttribute("projects", projects);
        return "validator";
    }

	@GetMapping("/validatorshow")
    public ResponseEntity<String> showAllValidators() {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		try {
			mainConPtr.QueryJsonResult(json,"select * from pi_validator");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(writer.toString(), HttpStatus.OK);
    }

	
	@GetMapping("/validationitemshow")
    public ResponseEntity<String> showValidatorItems(@RequestParam String validator_id) {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		try {
			mainConPtr.QueryJsonResult(json,"select * from pi_validationitems where validator_id="+validator_id+" order by csvorder");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(writer.toString(), HttpStatus.OK);
    }
	
	@GetMapping("/validator/{action}")
    public ResponseEntity<String> GetRelatedDatabase(@PathVariable Integer action,@RequestParam(required=false) String pid) {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		String result="";
		Boolean isSuccess=false;
		switch (action) {
			case 5:
				try {
					mainConPtr.QueryJsonResult(json,"select id,name from pi_validator where project_id="+pid);
					result=writer.toString();
					isSuccess=true;
				} catch (Exception e) {
					result=e.getMessage();
				}
				break;
	
			default:
				result="There is no data";
		}
		if (isSuccess)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(result, HttpStatus.FAILED_DEPENDENCY);
    }
	
	
	
	
	@PostMapping("/validator")
	public ResponseEntity<String> Manipuldate(@RequestParam HttpServletRequest request){

		int action = Integer.valueOf(request.getParameter("action"));
		String name = request.getParameter("name");
		String project_id = request.getParameter("project_id");
		String description = request.getParameter("description");
		switch (action) {
			case 1:
				String[] csvOrder=request.getParameterValues("csvorder[]");
				String[] csvTitle=request.getParameterValues("csvtitle[]");
				String[] tableName=request.getParameterValues("tablename[]");
				String[] columnName=request.getParameterValues("columnname[]");
				String[] conditions=request.getParameterValues("conditions[]");
				String[] ckSequence=request.getParameterValues("cksequence[]");
				String[] desc=request.getParameterValues("desc[]");
				try {
					mainConPtr.Connect();
					mainConPtr.StartTransaction();
					String query="insert into validator(name,project_id,description) values('"+name+"',"+project_id+",'"+description+"');";
					System.out.println(query);
					int rowId=mainConPtr.ExecuteNoneQuery1(query);
					System.out.println("length is:"+csvOrder.length);
					for(int i = 0; i< csvOrder.length; i++){
						String colName="";
						String type="null";
						if (columnName[i].length()>0) {
							System.out.println("column name is: "+columnName[i]);
							int indexOfCol = columnName[i].indexOf('#');
							System.out.println("# index is: "+indexOfCol+" col is: "+columnName[i].substring(0,indexOfCol));
							colName=columnName[i].substring(0,indexOfCol);
							type=columnName[i].substring(indexOfCol+1);
						}
							
						query="insert into validationitems(csvorder,csvtitle,tablename,columnname,conditions,sequence,type,description,validator_id) values("+
							  csvOrder[i]+
							  ",'"+csvTitle[i]+"'"+
							  (tableName[i].length()>0?",'"+tableName[i]+"'":",null")+
							  (colName.length()>0?",'"+colName+"'":",null")+
							  (conditions[i].length()>0?",'"+conditions[i]+"'":",null")+
							  (ckSequence[i].length()>0?",'"+ckSequence[i]+"'":",null")+
							  ","+type+
							  ",'"+desc[i]+"'"+
							  ","+rowId+
							  ");";
						System.out.println(query);
						mainConPtr.ExecuteNoneQuery1(query);
					}
					mainConPtr.Commit();
					mainConPtr.DisConnect();
				}catch(Exception e) {
					mainConPtr.RollBack();
					return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);

				}
				
				break;
		}
		return new ResponseEntity<>("ok", HttpStatus.OK);

	}
	
}
