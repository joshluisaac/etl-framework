package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.EmailLogService;
import com.kollect.etl.service.app.EmailUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailUpdatesController {
    @Autowired
    private EmailUpdatesService emailUpdatesService;
    @Autowired
    private EmailLogService emailLogService;

    /**
     * GET request method to return the email updates page, with logs.
     * @return
     *          returns the html emailUpdates page with logs.
     */
    @GetMapping("/emailupdate")
    public Object emailUpdates(Model model) {
        this.emailLogService.getEmailLogs(model);
        return "emailUpdates";
    }

    /**
     * POST method that gets values from client to run a service to send out batch email updates manually.
     * @param recipient
     *                  String of recipients to send emails to, separated by commas(,).
     * @return
     *        returns the service to be executed.
     */
    @PostMapping(value = "/resendemailupdate", produces = "application/json")
    @ResponseBody
    public Object resendEmailUpdate(@RequestParam String recipient) {
        return this.emailUpdatesService.resendEmail(recipient);
    }

    /**
     * POST method that gets values from client to send out test emails. Useful to carry out unit testing and test different
     * templates.
     * @param recipient
     *                  String of recipients to send emails to, separated by commas(,).
     * @return
     *        returns the service to be executed.
     */
    @PostMapping(value = "/sendtestemail", produces = "application/json")
    @ResponseBody
    public Object sendTestEmail(@RequestParam String recipient) {
        return this.emailUpdatesService.sendTestEmail(recipient);
    }
}
