package com.kollect.etl.controller.app;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.kollect.etl.util.dctemporary.ColumnMetaData;
import com.kollect.etl.util.dctemporary.PostgresPtr;;


@Controller
public class DBConStringController {
	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	
	@GetMapping("/database")
    public String showDatabasePage(Model model) {
		HashMap<Integer,String> projects = mainConPtr.FillComboBox("select id,name from pi_project;");
		model.addAttribute("projects", projects);
        return "database";
    }


	@GetMapping("/database/{action}")
    public ResponseEntity<String> getRelatedInfo(@PathVariable Integer action,@RequestParam(required=false) String pid,
    												 @RequestParam(required=false) String dbid,@RequestParam(required=false) String tablename) {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		String result="";
		Boolean isSuccess=false;
		switch (action) {
			case 5:
				try {
					mainConPtr.QueryJsonResult(json,"select id,dbname from pi_dbinfo where project_id="+pid);
					result=writer.toString();
					isSuccess=true;
				} catch (Exception e) {
					result=e.getMessage();
				}

				break;
			case 6:
				try {
					mainConPtr.ExecuteQuery("select * from pi_dbinfo where id="+dbid);
					if(mainConPtr.resultSet.next()) {
						PostgresPtr postGres= new PostgresPtr(mainConPtr.resultSet.getString(4), mainConPtr.resultSet.getInt(5),mainConPtr.resultSet.getString(3), mainConPtr.resultSet.getString(6), mainConPtr.resultSet.getString(7));
						ArrayList<String> tableList = postGres.getTableNames();
						Gson gson = new Gson();
						result=gson.toJson(tableList);
						isSuccess=true;
					}
					mainConPtr.DisConnect();
				}
				catch (Exception e) {
					result=e.getMessage();
				}
				break;
			case 7:
				try {
					mainConPtr.ExecuteQuery("select * from pi_dbinfo where id="+dbid);
					if(mainConPtr.resultSet.next()) {
						PostgresPtr postGres= new PostgresPtr(mainConPtr.resultSet.getString(4), mainConPtr.resultSet.getInt(5),mainConPtr.resultSet.getString(3), mainConPtr.resultSet.getString(6), mainConPtr.resultSet.getString(7));
						
						ArrayList<ColumnMetaData> columnList = postGres.getColumnsMetadata(tablename);
						Gson gson = new Gson();
						result=gson.toJson(columnList);
						isSuccess=true;
					}
					mainConPtr.DisConnect();
				}
				catch (Exception e) {
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

	
	
	@GetMapping("/databaseshow")
    public ResponseEntity<String> showAllProject() {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		//response.setContentType("text/plain");
		try {
			mainConPtr.QueryJsonResult(json,"select * from pi_dbinfo");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(writer.toString(), HttpStatus.OK);
    }
	
	
	
	@PostMapping("/database")
	public ResponseEntity<String> manipuldate(@RequestParam Integer action, @RequestParam Integer id, @RequestParam String project_id,
											  @RequestParam String dbname, @RequestParam String dburl, @RequestParam Integer dbport,
											  @RequestParam String dbuser, @RequestParam String dbpass){

		
		Boolean isConnect=false;
		String responseText="";
		try {
			switch(action) {
				case 4:
					if ( dbname!=null && dburl!=null && dbport!=null && dburl!=null && dbpass!=null)
					{
						PostgresPtr dbPtr = new PostgresPtr(dburl, dbport, dbname, dbuser, dbpass);
		
						if(dbPtr.Connect()) {
							isConnect=true;
							dbPtr.DisConnect();
							responseText="Connected";
						}else
							return new ResponseEntity<>("Can't Connect", HttpStatus.OK);
					}
			        break;
				case 1:
					if ( dbname!=null && dburl!=null && dbport!=null && dburl!=null && dbpass!=null)
					{
						mainConPtr.ExecuteNoneQuery("insert into pi_dbinfo(project_id,dburl,dbport,dbname,dbuser,dbpass) values("+project_id+",'"+dburl+"',"+dbport+",'"+dbname+"','"+dbuser+"','"+dbpass+"');");
						isConnect=true;
						responseText="Inserted successfuly";
					}
					if (! isConnect)
						responseText="Can't Insert";
					break;
				case 2:
					if ( id!=null && project_id!=null && dbname!=null && dburl!=null && dbport!=null && dburl!=null && dbpass!=null)
					{
						
						String query="update pi_dbinfo set project_id="+project_id+" ,dburl='"+dburl+"',dbport="+dbport+",dbname='"+dbname+"',dbuser='"+dbuser+"',dbpass='"+dbpass+"' where id="+id+";";
						mainConPtr.ExecuteNoneQuery(query);
						isConnect=true;
						responseText="Updated successfuly";
					}
					if (! isConnect)
						responseText="Can't Update";
					break;
				case 3:
					if ( id!=null )
					{
						
						String query="delete from pi_dbinfo where id="+id;
						mainConPtr.ExecuteNoneQuery(query);
						isConnect=true;
						responseText="Delete successfuly";
					}
					if (! isConnect)
						responseText="Can't Delete";
					break;
			}
			return new ResponseEntity<>(responseText, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);		}
	}
	
}
