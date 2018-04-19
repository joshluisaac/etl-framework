package com.kollect.etl.service.app;

import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSftpService {

    @Autowired
    private IReadWriteServiceProvider rwProvider;
    @Value("${app.datasource_uat_8}")
    String dataSource;


    public List<Object> getAdminConfigById(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getAdminConfigById", object);
    }

    public int saveAdminConfig(Object object) {
        return this.rwProvider.insertQuery(dataSource, "insertAdminSftpProp", object);
    }

    public int updateAdminSFTPSettings(Object object) {
        return this.rwProvider.updateQuery(dataSource, "updateAdminSFTPSettings", object);
    }

    public List<Object> getAdminSFTPCounter() {
        return this.rwProvider.executeQuery(dataSource, "getAdminSFTPCounter", null);
    }
        //getAdminTemplateSettings
    public List<Object> getAdminSFTPSettings(Object object) {
        // TODO Auto-generated method stub
        return this.rwProvider.executeQuery(dataSource, "getAdminConfig", object);

    }

    public int insertAdminSftpProp(Object object) {

        return this.rwProvider.insertQuery(dataSource, "insertAdminSftpProp", object);
    }
}
