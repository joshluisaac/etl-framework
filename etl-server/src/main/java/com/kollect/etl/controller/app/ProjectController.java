package com.kollect.etl.controller.app;


import java.io.StringWriter;
import java.io.Writer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.stream.JsonWriter;
import com.kollect.etl.util.dctemporary.PostgresPtr;



@Controller
public class ProjectController {

	private PostgresPtr mainConPtr= new  PostgresPtr("192.168.1.15", 5432, "kollectvalley_uat8", "kollectvalley", "kollect1234");
	
	
//	private void CheckMyBatis() {
//		Properties props = new Properties();
//	    props.setProperty("dialect", "postgresql");
//	    props.setProperty("reasonable", "true");
//	    props.setProperty("supportMethodsArguments", "true");
//	    props.setProperty("returnPageInfo", "check");
//	    props.setProperty("params", "count=countSql");		
//		Reader reader = Resources.getResourceAsProperties(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//		SqlSession session = sqlSessionFactory.openSession();
//		session.commit();
//		session.close();
//	}

	@GetMapping("/project")
    public String loadProjectPage() {
//		CheckMyBatis();
        return "project";
    }
	

	@GetMapping("/projectshow")
    public ResponseEntity<String> showAllProject() {
		Writer writer = new StringWriter();
		JsonWriter json = new JsonWriter(writer);
		//response.setContentType("text/plain");

		try {
			mainConPtr.QueryJsonResult(json,"select * from pi_project");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(writer.toString(), HttpStatus.OK);
    }
	
	@PostMapping("/project")
	public ResponseEntity<String> Manipuldate(@RequestParam Integer action, @RequestParam Integer id, @RequestParam String name,
											  @RequestParam String customer,@RequestParam String description){
		
		Boolean isConnect=false;
		String responseText="";
		try {
			switch(action) {
				case 1:
					if ( name!=null && customer!=null && description!=null)
					{
						String query="insert into pi_project(name,customer,description) values('"+name+"','"+customer+"','"+description+"');";
						mainConPtr.ExecuteNoneQuery(query);
						isConnect=true;
						responseText="Inserted successfuly";
					}
					if (! isConnect)
						responseText="Can't Insert";
					break;
				case 2:
					if ( id!=null && name!=null && customer!=null && description!=null)
					{
						
						String query="update pi_project set name='"+name+"',customer='"+customer+"',description='"+description+"' where id="+id+";";
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
						
						String query="delete from pi_project where id="+id;
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
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
	}
}
