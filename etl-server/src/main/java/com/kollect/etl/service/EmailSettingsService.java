package com.kollect.etl.service;

import com.kollect.etl.entity.EmailSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public class EmailSettingsService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(EmailSettingsService.class);

	 @Autowired
     public EmailSettingsService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
         this.rwProvider = rwProvider;
         this.dataSource = dataSource;
     }

	public Object getEmailSettings(Model model){
        List<Object> list = this.rwProvider.executeQuery(dataSource, "getEmailSettings", null);
        if (list.size() > 0) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            model.addAttribute("result", map);
        }
        return "emailSettingsForm";
    }

	public Object addUpdateEmailSettings(boolean sendEmail, String userAuthentication,
                                         String userName, String pass, String host,
                                         String recipient, Integer port, String subject,
                                         String msg, String subjErr, String msgErr){
        {

            EmailSettings emailDto = new EmailSettings(sendEmail, userAuthentication, userName, pass, host, recipient, port,
                    subject, msg, subjErr, msgErr);

            @SuppressWarnings("unchecked")
            Map<String, Integer> counterMap = (Map<String, Integer>) this.rwProvider.executeQuery(dataSource, "getEmailCounter", null).get(0);

            int numberOfRecAffected = (counterMap.get("emailCounter") > 0)
                    ? (this.rwProvider.updateQuery(dataSource, "updateEmailSettings", emailDto))
                    : (this.rwProvider.insertQuery(dataSource, "insertEmailSettings", emailDto));

            LOG.debug("Number of records affected: {}", numberOfRecAffected);
            return "redirect:/adminEmailSettings";
        }
    }
}
