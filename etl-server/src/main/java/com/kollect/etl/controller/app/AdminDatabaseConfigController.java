package com.kollect.etl.controller.app;

import java.util.List;
import java.util.Map;

import com.kollect.etl.service.app.AdminDatabaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.entity.app.AdminDatabaseProp;

@Controller
public class AdminDatabaseConfigController {


	/*@Autowired
	private AdminConfigService adminConfigService;*/

	@Autowired
	private AdminDatabaseConfigService service;

	@RequestMapping("/adminDatabase")
	public String adminDatabase(Model model) {
		model.addAttribute("pageTitle", "Admin - Database Settings");
		return "adminDatabase";
	}

	@GetMapping(value = "/adminDatabase")
	public String viewDatabaseSettings(Model model2) {
		List<Object> list = this.service.getAdminDatabaseSettings(null);

		if (list.size() > 0) {
		      @SuppressWarnings("unchecked")
		      Map<String, Object> map2 = (Map<String, Object>) list.get(0);
		      model2.addAttribute("resultDatabase", map2);
		    }
		return "adminDatabase";
	}
	
	@RequestMapping(value = "/adminDatabaseProp", method = RequestMethod.POST)
	public String adminDatabaseProp(@RequestParam String driver, @RequestParam String url,
			@RequestParam String additionalArgs, @RequestParam String username, @RequestParam String password,
			@RequestParam Integer port) {

		
		// create new class for AdminDatabaseProp and change all line below
		AdminDatabaseProp AdminDatabaseprop = new AdminDatabaseProp(driver, url, additionalArgs, username, password,
				port);
		

		 @SuppressWarnings("unchecked")
		 Map<String, Integer> counterMap = (Map<String, Integer>) service.getAdminDatabaseCounter().get(0);
			    int numberOfRecAffected = ((int) counterMap.get("databaseCounter") > 0)
			            ? (service.updateAdminDatabaseSettings(AdminDatabaseprop))
			            : (service.insertAdminDatabaseProp(AdminDatabaseprop));


		return "redirect:/adminDatabase";
	}

}
