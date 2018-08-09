package com.kollect.etl.notification.service;

import java.util.List;
import java.util.Map;

public interface IEmailLogger {
    Map<String, String> saveEmailLog(String title, String recipient,
                                            String status, String sendTime);

    void persistLogToCsv(List<String> logList, String path);
}
