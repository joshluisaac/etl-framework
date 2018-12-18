package com.kollect.etl.service.app;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import com.google.gson.Gson;
import com.kollect.etl.entity.app.Combo;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.ProjectManagementService;
import com.kollect.etl.util.dctemporary.PostgresPtr;

@Service
public class ValidatorConfigManagement {
	private IReadWriteServiceProvider rwProvider;
	private String dataSource;
	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	@Autowired
    public ValidatorConfigManagement (IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

	
	public Object loadValidatorPage(Model model) {
		ProjectManagementService project= new ProjectManagementService(rwProvider,dataSource);
		List<Combo> projects = project.fillProjectsCombo();
		model.addAttribute("projects", projects);
		return "validator";
	}

	
	public ResponseEntity<String> getValidatorsList() {
		try {
			List<Object> validatorList = this.rwProvider.executeQuery(dataSource, "getValidators",null);
			Gson gson = new Gson();
			String result=gson.toJson(validatorList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}	    
    

	public ResponseEntity<String> getValidatorItemsList(int validatorId) {
		try {
			List<Object> validatorList = this.rwProvider.executeQuery(dataSource, "getValidatorItems",validatorId);
			Gson gson = new Gson();
			String result=gson.toJson(validatorList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}		
	
	
	public ResponseEntity<String> fillValidatorCombo(int pid) {
		try {
			List<Object> validatorList = this.rwProvider.executeQuery(dataSource, "getValidatorCombo",pid);
			Gson gson = new Gson();
			String result=gson.toJson(validatorList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}	
    
	
	public ResponseEntity<String> mainpulateValidator(HttpServletRequest request){
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
					//System.out.println(query);
					int rowId=mainConPtr.ExecuteNoneQuery1(query);
					System.out.println("length is:"+csvOrder.length);
					for(int i = 0; i< csvOrder.length; i++){
						String colName="";
						String type="null";
						if (columnName[i].length()>0) {
							//System.out.println("column name is: "+columnName[i]);
							int indexOfCol = columnName[i].indexOf('#');
							//System.out.println("# index is: "+indexOfCol+" col is: "+columnName[i].substring(0,indexOfCol));
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
						//System.out.println(query);
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
