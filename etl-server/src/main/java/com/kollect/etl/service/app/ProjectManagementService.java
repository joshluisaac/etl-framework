package com.kollect.etl.service.app;


import java.util.List;

						
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.entity.app.Combo;
import com.kollect.etl.entity.app.ProjectEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;





@Service
public class ProjectManagementService {
	private IReadWriteServiceProvider rwProvider;
	private String dataSource;

	@Autowired
    public ProjectManagementService (IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

	
	
	public Object loadProjectPage() {
        return "project";
	}

	
	
	
	public ResponseEntity<String> getProjectsList() {
		try {
			List<Object> projectList = this.rwProvider.executeQuery(dataSource, "getProjects",null);
			Gson gson = new Gson();
			String result=gson.toJson(projectList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	


	public List<Combo> fillProjectsCombo() {
		try {
			return this.rwProvider.executeQuery(dataSource, "getProjectsCombo",null);
		}catch(Exception e) {
			throw e;
		}
	}	
	
	
	public ResponseEntity<String> deleteProject(@RequestParam Integer id){
		String responseText="Can't Delete";
		try {
			this.rwProvider.executeQuery(dataSource, "deleteProject", id);
			responseText="Delete successfuly";
			return new ResponseEntity<>(responseText, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	

	
	public ResponseEntity<String> addProject(@RequestParam String name,
			 								@RequestParam String customer,@RequestParam String description){
		ProjectEntity projectEntity = new ProjectEntity(name, customer, description);
		try {
			//String query="insert into pi_project(name,customer,description) values('"+name+"','"+customer+"','"+description+"');";
			int insertCount=this.rwProvider.insertQuery(dataSource, "insertProject", projectEntity);
			if (insertCount != 0)
				return new ResponseEntity<>("Inserted successfuly", HttpStatus.OK);
			else
				return new ResponseEntity<>("Nothing inserted", HttpStatus.OK);

		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}


	
	public ResponseEntity<String> updateProject(@RequestParam Integer id, @RequestParam String name,
												@RequestParam String customer,@RequestParam String description){
		ProjectEntity projectEntity = new ProjectEntity(id, name, customer, description);
		try {
						
			//String query="update pi_project set name='"+name+"',customer='"+customer+"',description='"+description+"' where id="+id+";";
			int updateCount = this.rwProvider.updateQuery(dataSource, "updateProject", projectEntity);
			if (updateCount != 0)
				return new ResponseEntity<>("Updated successfuly", HttpStatus.OK);
			else
				return new ResponseEntity<>("Nothing updated", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Can't Update\n"+e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}

}
