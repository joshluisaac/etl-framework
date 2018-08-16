package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.EmailSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class EmailSettingsController {

	private EmailSettingsService emailSettingsService;

	@Autowired
	public EmailSettingsController(EmailSettingsService emailSettingsService){
		this.emailSettingsService=emailSettingsService;
	}

	/**
	 * HTTP GET request to retrieve email settings
	 * 
	 * @param model
	 *            a data structure of objects which needs to be rendered to view
	 * @return emailSettingsForm pre-loaded with data
	 */
	@GetMapping(value = "/adminEmailSettings")
	public Object viewEmailSettings(Model model) {
	    model.addAttribute("result", emailSettingsService.getEmailSettings());
		return "emailSettingsForm";
	}

	/**
	 * HTTP POST request mapping to create or update email settings
	 *
	 * @param generalEmailSettings
	 * 								A hashmap containing all form email settings
	 */
	@ResponseBody
	@RequestMapping(value = "/adminEmailSettings", method = RequestMethod.POST)
	public void addEmailSettings(@RequestParam HashMap<String, String> generalEmailSettings) {
		emailSettingsService.addUpdateEmailSettings(generalEmailSettings);
	}
}
