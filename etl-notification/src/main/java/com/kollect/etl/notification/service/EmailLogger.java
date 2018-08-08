package com.kollect.etl.notification.service;

import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailLogger implements IEmailLogger {
    private Utils util;

    @Autowired
    public EmailLogger(Utils util){
        this.util=util;
    }

    @Override
    public Map<String, String> saveEmailLog(String title, String recipient,
                                             String status, String sendTime){
        Map<String, String> logMap = new HashMap<>();
        logMap.put("subject", title);
        logMap.put("recipient", recipient);
        logMap.put("status", status);
        logMap.put("sendTime", sendTime);
        return logMap;
    }

    @Override
    public String formatToCsvString(String recipient,String title, String logFileName,
                                        String sendTime, String status){
        return recipient+"|"+title+"|"+logFileName+"|"+sendTime+"|"+
                status+"\n";
    }

    @Override
    public void persistLogToCsv(String formattedLogMap, String path){
        util.writeTextFile(path, formattedLogMap, true);
    }
}
