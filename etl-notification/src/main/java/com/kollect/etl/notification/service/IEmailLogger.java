package com.kollect.etl.notification.service;

import java.util.Map;

public interface IEmailLogger {
    Map<String, String> saveEmailLog(String title, String recipient,
                                            String status, String sendTime);

    void persistLogToCsv(String formattedLogMap, String path);

    String formatToCsvString(String recipient,String title, String logFileName,
                                 String sendTime, String status);
}
