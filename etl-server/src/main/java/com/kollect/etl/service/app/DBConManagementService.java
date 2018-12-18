package com.kollect.etl.service.app;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.kollect.etl.entity.ColumnMetaData;
import com.kollect.etl.entity.app.Combo;
import com.kollect.etl.entity.app.DBInfo;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.util.dctemporary.PostgresPtr;

@Service
public class DBConManagementService {
	private IReadWriteServiceProvider rwProvider;
	private String dataSource;
	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	@Autowired
    public DBConManagementService (IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }
	
	public Object loadDatabasePage(Model model) {
		ProjectManagementService project= new ProjectManagementService(rwProvider,dataSource);
		List<Combo> projects = project.fillProjectsCombo();
		model.addAttribute("projects", projects);
		return "database";
	}

	
	public ResponseEntity<String> getDatabaseList() {
		try {
			List<Object> databaseList = this.rwProvider.executeQuery(dataSource, "getDBInfo",null);
			Gson gson = new Gson();
			String result=gson.toJson(databaseList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}	

	
	public ResponseEntity<String> fillDBInfoCombo(int pid) {
		try {
			List<Object> databaseList = this.rwProvider.executeQuery(dataSource, "getDBInfoCombo",pid);
			Gson gson = new Gson();
			String result=gson.toJson(databaseList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}	


	public ResponseEntity<String> fillTableCombo(int dbid) {
		try {
			
			mainConPtr.ExecuteQuery("select * from pi_dbinfo where id="+dbid);
			if(mainConPtr.resultSet.next()) {
				PostgresPtr postGres= new PostgresPtr(mainConPtr.resultSet.getString(4), mainConPtr.resultSet.getInt(5),mainConPtr.resultSet.getString(3), mainConPtr.resultSet.getString(6), mainConPtr.resultSet.getString(7));
				ArrayList<String> tableList = postGres.getTableNames();
				Gson gson = new Gson();
				String result=gson.toJson(tableList);
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
			return new ResponseEntity<>("", HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}finally{
			mainConPtr.DisConnect();
		}
	}	
	
	
	public ResponseEntity<String> fillColumnCombo(int dbid,String tablename) {
		try {
			mainConPtr.ExecuteQuery("select * from pi_dbinfo where id="+dbid);
			if(mainConPtr.resultSet.next()) {
				PostgresPtr postGres= new PostgresPtr(mainConPtr.resultSet.getString(4), mainConPtr.resultSet.getInt(5),mainConPtr.resultSet.getString(3), mainConPtr.resultSet.getString(6), mainConPtr.resultSet.getString(7));
				
				ArrayList<ColumnMetaData> columnList = postGres.getColumnsMetadata(tablename);
				Gson gson = new Gson();
				String result=gson.toJson(columnList);
				mainConPtr.DisConnect();
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
			return new ResponseEntity<>("", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}finally {
			mainConPtr.DisConnect();
		}
		
	}
	
	
	public ResponseEntity<String> addDBInfo(@RequestParam Integer project_id, @RequestParam String dbname, 
												@RequestParam String dburl, @RequestParam Integer dbport,
												@RequestParam String dbuser, @RequestParam String dbpass){
		DBInfo dbInfo = new DBInfo(project_id, dbname, dburl, dbport, dbuser, dbpass);
		try {
			int insertCount=this.rwProvider.insertQuery(dataSource, "insertDBInfo", dbInfo);
			if (insertCount != 0)
				return new ResponseEntity<>("Inserted successfuly", HttpStatus.OK);
			else
				return new ResponseEntity<>("Nothing inserted", HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}    

	
	public ResponseEntity<String> updateDBInfo(@RequestParam Integer id, @RequestParam Integer project_id, @RequestParam String dbname, 
												@RequestParam String dburl, @RequestParam Integer dbport,
												@RequestParam String dbuser, @RequestParam String dbpass){
		DBInfo dbInfo = new DBInfo(id,project_id, dbname, dburl, dbport, dbuser, dbpass);
		try {
			int insertCount=this.rwProvider.updateQuery(dataSource, "updateDBInfo", dbInfo);
			if (insertCount != 0)
				return new ResponseEntity<>("Updated successfuly", HttpStatus.OK);
			else
				return new ResponseEntity<>("Nothing inserted", HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}   

	
	public ResponseEntity<String> deleteDBInfo(@RequestParam Integer id){
		String responseText="Can't Delete";
		try {
			this.rwProvider.executeQuery(dataSource, "deleteDBInfo", id);
			responseText="Delete successfuly";
			return new ResponseEntity<>(responseText, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}


	public ResponseEntity<String> checkConnection(@RequestParam String dbname,@RequestParam String dburl, @RequestParam Integer dbport,
												  @RequestParam String dbuser, @RequestParam String dbpass){
		PostgresPtr dbPtr = new PostgresPtr(dburl, dbport, dbname, dbuser, dbpass);
		
		if(dbPtr.Connect()) {
			dbPtr.DisConnect();

			return new ResponseEntity<>("Connected", HttpStatus.OK);
		}else
			return new ResponseEntity<>("Can't Connect", HttpStatus.OK);

//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
//		}
	}



}
