package com.kollect.etl.service.app;

import com.kollect.etl.entity.app.User;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserManagementService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

	@Autowired
    public UserManagementService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

  public Object getUser(@RequestParam(required = false) Integer id, Model model){
      model.addAttribute("pageTitle", "DataConnector");
      model.addAttribute("userList", this.rwProvider.executeQuery(dataSource, "viewUser", null));
      List<User> users = this.rwProvider.executeQuery(dataSource, "getUserById", id);
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
      int updateCount = this.rwProvider.updateQuery(dataSource, "updateUser", newUser);
      if (updateCount == 0) {
          this.rwProvider.insertQuery(dataSource,"insertUser", newUser);
          insertFlag = true;
      }
      if (insertFlag)
          return "redirect:/usermanagement";
      return "redirect:/usermanagement?id=" + id;
  }
}