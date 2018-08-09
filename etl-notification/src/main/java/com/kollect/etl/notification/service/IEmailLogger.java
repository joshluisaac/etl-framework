package com.kollect.etl.notification.service;

import java.util.List;
import java.util.Map;

public interface IEmailLogger {
    Map<String, String> saveEmailLog(String title, String recipient,
                                            String status);

    void persistLogToCsv(List<String> logList, String path);
}
