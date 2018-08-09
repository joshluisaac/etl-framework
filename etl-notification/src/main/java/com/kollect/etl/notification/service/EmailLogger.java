package com.kollect.etl.notification.service;

import com.kollect.etl.util.CsvReadWriteUtils;
import com.kollect.etl.util.Utils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailLogger implements IEmailLogger {
    private CsvReadWriteUtils csvReadWriteUtils = new CsvReadWriteUtils(new Utils());

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
    public void persistLogToCsv(List<String> logList, String path){
        csvReadWriteUtils.persistToCsv(logList, path);
    }

}
