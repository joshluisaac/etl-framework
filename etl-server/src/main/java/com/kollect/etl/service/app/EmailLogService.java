package com.kollect.etl.service.app;

import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public class EmailLogService {
    private IReadWriteServiceProvider iRWProvider;
    @Value("${app.datasource_uat_8}")
    private String dataSource;

    public EmailLogService(IReadWriteServiceProvider iRWProvider){
        this.iRWProvider = iRWProvider;
    }

    public void logEmails(Map<Object, Object> arguments){
        this.iRWProvider.insertQuery(dataSource, "insertEmailLog", arguments);
    }

    public void getEmailLogs(Model model){
        List<Object> logList = this.iRWProvider.executeQuery(dataSource, "getEmailLog", null);
        model.addAttribute("logList", logList);
    }
}
