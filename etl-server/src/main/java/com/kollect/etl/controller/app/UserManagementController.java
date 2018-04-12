package com.kollect.etl.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.service.app.UserManagementService;

@Controller
public class UserManagementController {
	@Autowired
	private UserManagementService userManagementService;

	/**
	 * GET request to return the user management form, get user by id and populate the table for current users
	 * @param id
	 * 			the id needed to return the user details by the id
	 * @param model
	 * 				a data structure of objects which needs to be rendered to view
	 * @return
	 * 		  returns the usermanagement template
	 */

	@RequestMapping("/usermanagement")
	public Object getUserById(@RequestParam(required = false) Integer id, Model model) {
		return this.userManagementService.getUser(id, model);
	}

	/**
	 * POST request to either add new users or update an existing one if the id is given
	 * @param id
	 * 			the id of the user -  sequence that is auto updated
	 * @param email
	 * 				email of the user for login
	 * @param firstName
	 * 					the first name of the user
	 * @param lastName
	 * 					last name of user
	 * @param password
	 * 					password of user
	 * @param enabled
	 * 				determines whether the user can log in or not
	 * @param role
	 * 				the role of the user, either admin or regular user
	 * @param tenant_id
	 * 					the id which decides the tenant, i.e. MAHB, PBK, YYC and so on
	 * @return
	 * 			redirects to the usermanagement page, with user added to form
	 */

	@PostMapping("/usermanagement")
	public Object addUser(@RequestParam(required = false) Integer id, @RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
			@RequestParam(required = false) boolean enabled, @RequestParam String role,
			@RequestParam Integer tenant_id) {
	    return this.userManagementService.addUpdateUser(id, email, firstName, lastName, password, enabled, role, tenant_id);
	}

}
