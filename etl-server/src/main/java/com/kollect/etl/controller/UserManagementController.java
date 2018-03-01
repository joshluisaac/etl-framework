package com.kollect.etl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.entity.User;
import com.kollect.etl.service.UserManagementService;

@Controller
public class UserManagementController {
	@Autowired
	private UserManagementService userManagementService;

	@RequestMapping("/usermanagement")
	public Object getUserById(@RequestParam(required = false) Integer id, Model model) {
		model.addAttribute("pageTitle", "DataConnector");
		model.addAttribute("userList", this.userManagementService.viewUser(null));
		List<User> users = this.userManagementService.getUserById(id);
		if (users.size() > 0)
			model.addAttribute("userEditList", users.get(0));
		else
			model.addAttribute("userEditList", null);
		return "userManagement";
	}

	@PostMapping("/usermanagement")
	public String addUser(@RequestParam(required = false) Integer id, @RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
			@RequestParam(required = false) boolean enabled, @RequestParam String role,
			@RequestParam Integer tenant_id) {

		boolean insertFlag = false;
		User newUser = new User(email, firstName, lastName, password, enabled, role, tenant_id);
		if (id != null)
			newUser.setId(id);
		int updateCount = userManagementService.updateUser(newUser);
		if (updateCount == 0) {
			this.userManagementService.insertUser(newUser);
			insertFlag = true;
		}
		if (insertFlag)
			return "redirect:/usermanagement";
		return "redirect:/usermanagement?id=" + id;
	}

}
