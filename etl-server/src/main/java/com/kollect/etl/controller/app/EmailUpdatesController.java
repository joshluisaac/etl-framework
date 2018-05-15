package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.EmailUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailUpdatesController {
    @Autowired
    private EmailUpdatesService emailUpdatesService;

    @GetMapping("/emailupdate")
    public Object emailUpdates() {
        return "emailUpdates";
    }

    @PostMapping(value = "/resendemailupdate", produces = "application/json")
    @ResponseBody
    public Object resendEmailUpdate() {
        return this.emailUpdatesService.resendEmail();
    }

    @PostMapping(value = "/sendtestemail", produces = "application/json")
    @ResponseBody
    public Object sendTestEmail() {
        return this.emailUpdatesService.sendTestEmail();
    }
}
