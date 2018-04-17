package com.kollect.etl.controller.app;

import com.kollect.etl.entity.app.AdminSftpProp;
import com.kollect.etl.service.app.AdminSftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AdminSftpController {

    @Autowired
    private AdminSftpService service;

    @RequestMapping("/adminSftp")
    public String adminSftp(Model model) {
        model.addAttribute("pageTitle", "Admin - SFTP Settings");
        return "adminSftp";
    }
    @GetMapping(value = "/adminSftp")
    public String viewSFTPSettings(Model model) {
        List<Object> list = this.service.getAdminSFTPSettings(null);

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
        Map<String, Integer> counterMap = (Map<String, Integer>) service.getAdminSFTPCounter().get(0);
        int numberOfRecAffected = ((int) counterMap.get("sftpCounter") > 0)
                ? (service.updateAdminSFTPSettings(Adminprop))
                : (service.insertAdminSftpProp(Adminprop));


        return "redirect:/adminSftp";
    }
}
