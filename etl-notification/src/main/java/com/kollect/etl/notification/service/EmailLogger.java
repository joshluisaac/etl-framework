package com.kollect.etl.notification.service;

import com.kollect.etl.util.JsonToHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailLogger implements IEmailLogger {
    private JsonToHashMap jsonToHashMap;

    @Autowired
    public EmailLogger(JsonToHashMap jsonToHashMap){
        this.jsonToHashMap=jsonToHashMap;
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
    public void persistLogToJson(Map<String, String> logMap, String path){
        jsonToHashMap.fromHashMapToJson(logMap, path);
    }
}
