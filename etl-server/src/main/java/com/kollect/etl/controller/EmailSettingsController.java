package com.kollect.etl.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.entity.EmailSettings;
import com.kollect.etl.service.EmailSettingsService;

@Controller
public class EmailSettingsController {

	private static final Logger LOG = LoggerFactory.getLogger(EmailSettingsController.class);

	@Autowired
	private EmailSettingsService emailSettingsService;

	@RequestMapping("/adminEmailSettings")
	public String adminEmailSettings(Model model) {
		model.addAttribute("pageTitle", "Admin - Email Settings");
		return "emailSettingsForm";
	}

	/**
	 * HTTP GET request to retrieve email settings
	 * 
	 * @param Model
	 *            a data structure of objects which needs to be rendered to view
	 * @return emailSettingsForm pre-loaded with data
	 */
	@GetMapping(value = "/adminEmailSettings")
	public String viewEmailSettings(Model model) {
		List<Object> list = this.emailSettingsService.getTemplateSettings(null);
		if (list.size() > 0) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(0);
			model.addAttribute("result", map);
		}
		return "emailSettingsForm";
	}

	/**
	 * HTTP POST request mapping to create or update email settings
	 * 
	 * @param sendEmail
	 *            flag for sending or not sending email
	 * @param userAuthentication
	 *            main authentication user
	 * @param userName
	 *            the user who's sending the email
	 * @param pass
	 *            user password
	 * @param email
	 *            SMTP server name
	 * @param recipient
	 *            a comma separated list of recipients
	 * @param port
	 *            SMTP server port
	 * @param subject
	 *            email subject for successful status
	 * @param msg
	 *            email message content for successful status
	 * @param subjErr
	 *            email subject for unsuccessful status
	 * @param msgErr
	 *            email message content for unsuccessful status
	 * @return redirects requests to GET /adminEmailSettings
	 */
	@RequestMapping(value = "/adminEmailSettings", method = RequestMethod.POST)
	public String addEmailSettings(@RequestParam (required = false) boolean sendEmail, @RequestParam String userAuthentication,
			@RequestParam String userName, @RequestParam String pass, @RequestParam String host,
			@RequestParam String recipient, @RequestParam Integer port, @RequestParam String subject,
			@RequestParam String msg, @RequestParam String subjErr, @RequestParam String msgErr) {

		EmailSettings emailDto = new EmailSettings(sendEmail, userAuthentication, userName, pass, host, recipient, port,
				subject, msg, subjErr, msgErr);

		@SuppressWarnings("unchecked")
		Map<String, Integer> counterMap = (Map<String, Integer>) emailSettingsService.getEmailCounter().get(0);

		int numberOfRecAffected = ((int) counterMap.get("emailCounter") > 0)
				? (emailSettingsService.updateEmailSettings(emailDto))
				: (emailSettingsService.insertEmailSettings(emailDto));

		LOG.debug("Number of records affected: {}", numberOfRecAffected);
		return "redirect:/adminEmailSettings";
	}
}
