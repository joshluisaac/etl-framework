package com.kollect.etl.service.app;

import com.kollect.etl.entity.app.DcConfig;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcConfigService {

    @Autowired
    private IReadWriteServiceProvider rwProvider;
    @Value("${app.datasource_uat_8}")
    String dataSource;

    public List<DcConfig> getDcConfigById(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getDcConfigById", object);
    }

    public List<DcConfig> getDcConfigByParentId(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getDcConfigByParentId", object);
    }

    public List<Object> getDcConfigParent(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getDcConfigParent", object);
    }


    public int deleteDcConfig(Object object) {
        return this.rwProvider.insertQuery(dataSource, "deleteDcConfig", object);
    }

    public int saveConfig(Object object) {
        return this.rwProvider.insertQuery(dataSource, "insertDcConfig", object);
    }

    public int updateDcConfigProp(Object object) {
        return this.rwProvider.updateQuery(dataSource, "updateDcConfigProp", object);
    }

    public int insertDcConfigProp(Object object) {
        return this.rwProvider.insertQuery(dataSource, "insertDcConfigProp", object);
    }

    public List<Object> getConfigListForExport(Object obj) {
        return this.rwProvider.executeQuery(dataSource, "getConfigListForExport", obj);
    }


}
