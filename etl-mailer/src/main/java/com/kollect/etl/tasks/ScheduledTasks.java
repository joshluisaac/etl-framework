package main.java.tasks;

import main.java.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private EmailSenderService emailSenderService;

    @Autowired
    public EmailScheduler(EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }

    @Scheduled(fixedRate = 300000)
    public void sendPelitaExtractEmail(){
        emailSenderService.sendExtractionEmail("Pelita - Daily Extraction Metrics", "pelita");
    }
}
