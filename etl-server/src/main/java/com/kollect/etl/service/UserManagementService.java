package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.UserDao;
import com.kollect.etl.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserManagementService {
	@Autowired
	private UserDao userDao;

	public int insertUser(Object object) {
		return this.userDao.insertUser(object);
	}

	public List<Object> viewUser(Object object) {
		return this.userDao.viewUser(object);
		// TODO Auto-generated method stub

	}

	public int updateUser(Object object) {
		return this.userDao.updateUser(object);
	}

	public List<User> getUserById(Object object){
    return this.userDao.getUserById(object);
  }

  public Object getUser(@RequestParam(required = false) Integer id, Model model){
      model.addAttribute("pageTitle", "DataConnector");
      model.addAttribute("userList", this.viewUser(null));
      List<User> users = this.getUserById(id);
      if (users.size() > 0)
          model.addAttribute("userEditList", users.get(0));
      else
          model.addAttribute("userEditList", null);
      return "userManagement";
  }

  public Object addUpdateUser(@RequestParam(required = false) Integer id, @RequestParam String email,
                       @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
                       @RequestParam(required = false) boolean enabled, @RequestParam String role,
                       @RequestParam Integer tenant_id){
      boolean insertFlag = false;
      User newUser = new User(email, firstName, lastName, password, enabled, role, tenant_id);
      if (id != null)
          newUser.setId(id);
      int updateCount = this.updateUser(newUser);
      if (updateCount == 0) {
          this.insertUser(newUser);
          insertFlag = true;
      }
      if (insertFlag)
          return "redirect:/usermanagement";
      return "redirect:/usermanagement?id=" + id;
  }
}