package com.kollect.etl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.entity.AdminDatabaseProp;
import com.kollect.etl.entity.AdminSftpProp;
import com.kollect.etl.service.AdminConfigService;

@Controller
public class AdminConfigController {
	

	@RequestMapping("/adminSftp")
	public String adminSftp(Model model) {
		model.addAttribute("pageTitle", "Admin - SFTP Settings");
		return "adminSftp";
	}

	@RequestMapping("/adminDatabase")
	public String adminDatabase(Model model) {
		model.addAttribute("pageTitle", "Admin - Database Settings");
		return "adminDatabase";
	}
	
	@Autowired
	private AdminConfigService adminConfigService;

	@GetMapping(value = "/adminSftp")
	public String viewSFTPSettings(Model model) {
		List<Object> list = this.adminConfigService.getAdminSFTPSettings(null);

		if (list.size() > 0) {
		      @SuppressWarnings("unchecked")
		      Map<String, Object> map = (Map<String, Object>) list.get(0);
		      model.addAttribute("result", map);
		    }
		return "adminSftp";
	}

	@RequestMapping(value = "/adminConfigProp", method = RequestMethod.POST)
	public String adminConfigProp(@RequestParam String ServerName, @RequestParam Integer PortNo,
			@RequestParam String UserName, @RequestParam String Password, @RequestParam String LocalDirectory,
			@RequestParam String RemoteDirectory) {


		AdminSftpProp Adminprop = new AdminSftpProp(ServerName, PortNo, UserName, Password, LocalDirectory,
				RemoteDirectory);
		

		
		 @SuppressWarnings("unchecked")
		 Map<String, Integer> counterMap = (Map<String, Integer>) adminConfigService.getAdminSFTPCounter().get(0);
			    int numberOfRecAffected = ((int) counterMap.get("sftpCounter") > 0)
			            ? (adminConfigService.updateAdminSFTPSettings(Adminprop))
			            : (adminConfigService.insertAdminSftpProp(Adminprop));

			           
		return "redirect:/adminSftp";
	}

	@GetMapping(value = "/adminDatabase")
	public String viewDatabaseSettings(Model model2) {
		List<Object> list = this.adminConfigService.getAdminDatabaseSettings(null);

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
		 Map<String, Integer> counterMap = (Map<String, Integer>) adminConfigService.getAdminDatabaseCounter().get(0);
			    int numberOfRecAffected = ((int) counterMap.get("databaseCounter") > 0)
			            ? (adminConfigService.updateAdminDatabaseSettings(AdminDatabaseprop))
			            : (adminConfigService.insertAdminDatabaseProp(AdminDatabaseprop));


		return "redirect:/adminDatabase";
	}

}
