package com.kollect.etl.service;

import com.kollect.etl.dataaccess.UserDao;
import com.kollect.etl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserManagementService {
	@Autowired
	private UserDao userDao;

	private void insertUser(Object object) {
		this.userDao.insertUser(object);
	}

    private List<Object> viewUser(Object object) {
		return this.userDao.viewUser(object);
	}

    private int updateUser(Object object) {
		return this.userDao.updateUser(object);
	}

    private List<User> getUserById(Object object){
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