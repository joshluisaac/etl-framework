package com.kollect.etl.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.service.app.EmailSettingsService;

@Controller
public class EmailSettingsController {

	@Autowired
	private EmailSettingsService emailSettingsService;

	/**
	 * HTTP GET request to retrieve email settings
	 * 
	 * @param model
	 *            a data structure of objects which needs to be rendered to view
	 * @return emailSettingsForm pre-loaded with data
	 */
	@GetMapping(value = "/adminEmailSettings")
	public Object viewEmailSettings(Model model) {
	    return this.emailSettingsService.getEmailSettings(model);
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
	 * @param host
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
	public Object addEmailSettings(@RequestParam (required = false) boolean sendEmail, @RequestParam String userAuthentication,
			@RequestParam String userName, @RequestParam String pass, @RequestParam String host,
			@RequestParam String recipient, @RequestParam Integer port, @RequestParam String subject,
			@RequestParam String msg, @RequestParam String subjErr, @RequestParam String msgErr) {

		// does the processing
		this.emailSettingsService.addUpdateEmailSettings(sendEmail, userAuthentication, userName, pass, host, recipient, port, subject,
        msg, subjErr,msgErr);

		return "redirect:/adminEmailSettings";

	}
}
