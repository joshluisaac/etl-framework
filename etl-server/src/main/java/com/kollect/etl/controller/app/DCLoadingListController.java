package com.kollect.etl.controller.app;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.service.app.DCLoadingXMLService;



@Controller
public class DCLoadingListController {
	
	@Autowired
	private DCLoadingXMLService dCLoadingXMLService; 
	
	
	
	@GetMapping("/loadingxmllist")
    public Object loadDCListPage(Model model) {
		return this.dCLoadingXMLService.loadDCListPage(model);
    }

	@GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadXML(@RequestParam String name) {
		return this.dCLoadingXMLService.downloadXML(name);
    }

	
				  
	@PostMapping("/loadingconfshow")
    public ResponseEntity<String> showAllLoadingConfs(@RequestParam Integer project_id, @RequestParam(required=false) Integer dbid, @RequestParam(required=false) String tablename) {
		return this.dCLoadingXMLService.getLoadingConfList(project_id,dbid,tablename);
    }
}
