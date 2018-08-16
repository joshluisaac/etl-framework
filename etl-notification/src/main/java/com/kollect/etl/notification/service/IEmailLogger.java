package com.kollect.etl.notification.service;

import java.util.List;

public interface IEmailLogger {
    void persistLogToCsv(List<String> logList, String path);
}
