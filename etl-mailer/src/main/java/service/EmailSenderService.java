package main.java.service;

import com.kollect.etl.notification.service.EmailClient;
import com.kollect.etl.notification.service.EmailContentBuilder;
import com.kollect.etl.notification.service.EmailLogger;
import com.kollect.etl.notification.service.IEmailClient;
import com.kollect.etl.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;
    private EmailContentBuilder emailContentBuilder = new EmailContentBuilder(new TemplateEngine());
    private JsonUtils jsonUtils = new JsonUtils();

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    private String getDailyFile(String tenant){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        return "stats_manifest_"+tenant+"_"+sdf.format(new Date())+".json";
    }

    @SuppressWarnings("unchecked")
    private List<Object> getDailyStats(String tenant){
        return jsonUtils.fromJson(new File(getDailyFile(tenant)).toString(), List.class);
    }

    public void sendExtractionEmail(String title, String tenant){
        IEmailClient emailClient = new EmailClient(javaMailSender, new EmailLogger());
        emailClient.sendExtractLoadEmail("datareceived@kollect.my", "kvloading@kollect.my,hashim@kollect.my", title,
                getDailyStats(tenant), emailContentBuilder,
                "templates/template_extract_load.html",
                "emailLog/extractorEmailLog.csv");
    }

}
