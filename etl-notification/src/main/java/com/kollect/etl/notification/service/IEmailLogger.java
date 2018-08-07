package com.kollect.etl.notification.service;

import java.util.Map;

public interface IEmailLogger {
    Map<String, String> saveEmailLog(String title, String recipient,
                                            String status, String sendTime);

    void persistLogToJson(Map<String, String> logMap, String path);
}
