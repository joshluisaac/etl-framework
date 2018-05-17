package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.EmailUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailUpdatesController {
    @Autowired
    private EmailUpdatesService emailUpdatesService;

    /**
     * GET request method to return the email updates page.
     * @return
     *          returns the html emailUpdates page.
     */
    @GetMapping("/emailupdate")
    public Object emailUpdates() {
        return "emailUpdates";
    }

    /**
     * POST method that gets values from client to run a service to send out batch email updates manually.
     * @param recipient
     *                  String of recipients to send emails to, separated by commas(,).
     */
    @PostMapping(value = "/resendemailupdate", produces = "application/json")
    @ResponseBody
    public void resendEmailUpdate(@RequestParam String recipient) {
        this.emailUpdatesService.resendEmail(recipient);
    }

    /**
     * POST method that gets values from client to send out test emails. Useful to carry out unit testing and test different
     * templates.
     * @param recipient
     *                  String of recipients to send emails to, separated by commas(,).
     */
    @PostMapping(value = "/sendtestemail", produces = "application/json")
    @ResponseBody
    public void sendTestEmail(@RequestParam String recipient) {
        this.emailUpdatesService.sendTestEmail(recipient);
    }
}
