package com.kollect.etl.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kollect.etl.service.app.DBConManagementService;



@Controller
public class DBConStringController {
	
	@Autowired
	private DBConManagementService dBConManagementService; 
	
	
	
	@GetMapping("/database")
    public Object loadDatabasePage(Model model) {
		return this.dBConManagementService.loadDatabasePage(model);
    }


	@GetMapping("/database/{action}")
    public ResponseEntity<String> getComboInfo(@PathVariable Integer action,@RequestParam(required=false) Integer pid,
			 									@RequestParam(required=false) Integer dbid,@RequestParam(required=false) String tablename) {
		switch (action) {
			case 5:
				return this.dBConManagementService.fillDBInfoCombo(pid);
			case 6:
				return this.dBConManagementService.fillTableCombo(dbid);
			case 7:
				return this.dBConManagementService.fillColumnCombo(dbid,tablename);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);
		}
		
	}
	

	
	@GetMapping("/databaseshow")
    public ResponseEntity<String> showAllDatabases() {
		return this.dBConManagementService.getDatabaseList();
    }
	
	@PostMapping("/database")
	public ResponseEntity<String> manipulateDatabase(@RequestParam Integer action, @RequestParam Integer id, @RequestParam Integer project_id,
			  										@RequestParam String dbname, @RequestParam String dburl, @RequestParam Integer dbport,
			  										@RequestParam String dbuser, @RequestParam String dbpass){
		switch (action) {
			case 1:
				return this.dBConManagementService.addDBInfo(project_id, dbname, dburl, dbport, dbuser, dbpass);
			case 2:
				return this.dBConManagementService.updateDBInfo(id, project_id, dbname, dburl, dbport, dbuser, dbpass);
			case 3:
				return this.dBConManagementService.deleteDBInfo(id);
			case 4:
				return this.dBConManagementService.checkConnection(dbname, dburl, dbport, dbuser, dbpass);
			default:
				return new ResponseEntity<>("Unkhown action", HttpStatus.FAILED_DEPENDENCY);
		}

	}	
	
}
