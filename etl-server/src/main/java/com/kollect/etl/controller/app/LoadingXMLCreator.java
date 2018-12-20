package com.kollect.etl.controller.app;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.service.app.DCLoadingXMLService;




@Controller
public class LoadingXMLCreator {
	
	@Autowired
	private DCLoadingXMLService dCLoadingXMLService;


	
	@GetMapping("/xmlcreator")
    public Object loadDatabasePage(Model model) {
		return this.dCLoadingXMLService.loadCreatorPage(model);
    }	
	


	
	@PostMapping("/xmlcreator")
	public ResponseEntity<String> creatingLoadingXML(HttpServletRequest request, @RequestParam Integer project_id, @RequestParam Integer dbid,
													 @RequestParam String name, @RequestParam String tablename, @RequestParam String description,
													 @RequestParam Integer action){
		return this.dCLoadingXMLService.creatingXML(request, project_id, dbid, name, tablename, description, action);
	}
	

	
}
