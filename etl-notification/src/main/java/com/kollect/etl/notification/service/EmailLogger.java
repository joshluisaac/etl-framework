package com.kollect.etl.notification.service;

import com.kollect.etl.util.CsvReadWriteUtils;
import com.kollect.etl.util.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailLogger implements IEmailLogger {
    private CsvReadWriteUtils csvReadWriteUtils = new CsvReadWriteUtils(new Utils());

    @Override
    public void persistLogToCsv(List<String> logList, String path){
        csvReadWriteUtils.persistToCsv(logList, path);
    }

}
