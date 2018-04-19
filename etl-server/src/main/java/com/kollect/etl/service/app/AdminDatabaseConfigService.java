package com.kollect.etl.service.app;

import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDatabaseConfigService {

    @Autowired
    private IReadWriteServiceProvider rwProvider;
    @Value("${app.datasource_uat_8}")
    String dataSource;

    public int insertAdminDatabaseProp(Object object) {
        return this.rwProvider.insertQuery(dataSource, "insertAdminDatabaseProp", object);
    }

    public List<Object> getAdminDatabaseSettings(Object object) {
        // TODO Auto-generated method stub
        return this.rwProvider.executeQuery(dataSource, "getAdminDatabaseConfig", object);

    }

    public int updateAdminDatabaseSettings(Object object) {
        return this.rwProvider.updateQuery(dataSource, "updateAdminDatabaseSettings", object);
    }

    public List<Object> getAdminDatabaseCounter(){
        return this.rwProvider.executeQuery(dataSource, "getAdminDatabaseCounter", null);
    }
}

