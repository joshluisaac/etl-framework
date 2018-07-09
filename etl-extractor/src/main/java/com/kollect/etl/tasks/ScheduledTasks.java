package com.kollect.etl.tasks;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    /*Required services*/
    private IReadWriteServiceProvider iRWProvider;
    private ComponentProvider componentProvider;

    /*Required variables*/
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String intro ="This is an Automated Notification for KollectValley Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";

    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    @Value("${app.datasource_kv_production}")
    private String prodDataSource;
    private @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> yycDataSource;
    private @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> pbkDataSource;
    private @Value("${app.datasource_pelita_test}")
    List<String> pelitaDataSource;
    private @Value("${app.datasource_ictzone}")
    List<String> ictZoneDataSource;
    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    /*The constructor for the class to inject the necessary services*/
    @Autowired
    public ScheduledTasks(IReadWriteServiceProvider iRWProvider,
                          ComponentProvider componentProvider){
        this.iRWProvider = iRWProvider;
        this.componentProvider = componentProvider;
    }
}