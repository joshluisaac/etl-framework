package com.kollect.etl.service;

import java.util.List;
import java.util.Map;

import com.kollect.etl.entity.EmailSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.EmailSettingsDao;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class EmailSettingsService {
	 @Autowired
	 private EmailSettingsDao emailSettingsDao;
    private static final Logger LOG = LoggerFactory.getLogger(EmailSettingsService.class);
	 
	 public int insertEmailSettings(Object object) {
		  return this.emailSettingsDao.insertEmailSettings(object);
	  }


	public List<Object> getTemplateSettings(Object object) {
		return this.emailSettingsDao.getTemplateSettings(object);
		
	}
	
	public int updateEmailSettings(Object object) {
		  return this.emailSettingsDao.updateEmailSettings(object);
	  }

	
	public List<Object> getEmailCounter(){
		return this.emailSettingsDao.getEmailCounter();
	}

	public Object getEmailSettings(Model model){
        List<Object> list = this.getTemplateSettings(null);
        if (list.size() > 0) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            model.addAttribute("result", map);
        }
        return "emailSettingsForm";
    }

	public Object addUpdateEmailSettings(@RequestParam(required = false) boolean sendEmail, @RequestParam String userAuthentication,
                                         @RequestParam String userName, @RequestParam String pass, @RequestParam String host,
                                         @RequestParam String recipient, @RequestParam Integer port, @RequestParam String subject,
                                         @RequestParam String msg, @RequestParam String subjErr, @RequestParam String msgErr){
        {

            EmailSettings emailDto = new EmailSettings(sendEmail, userAuthentication, userName, pass, host, recipient, port,
                    subject, msg, subjErr, msgErr);

            @SuppressWarnings("unchecked")
            Map<String, Integer> counterMap = (Map<String, Integer>) this.getEmailCounter().get(0);

            int numberOfRecAffected = (counterMap.get("emailCounter") > 0)
                    ? (this.updateEmailSettings(emailDto))
                    : (this.insertEmailSettings(emailDto));

            LOG.debug("Number of records affected: {}", numberOfRecAffected);
            return "redirect:/adminEmailSettings";
        }
    }
}
