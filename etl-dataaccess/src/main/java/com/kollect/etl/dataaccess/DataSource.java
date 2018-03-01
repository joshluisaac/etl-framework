package com.kollect.etl.dataaccess;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;

/**
 * Created by joshua on 5/17/17.
 */

public class DataSource {

    private static final Logger LOG = LoggerFactory.getLogger(DataSource.class);
    
    
    public static SqlMapClient getSqlMapClient(String resource) throws Exception {
        Reader reader = Resources.getResourceAsReader(resource);
        SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);        
        LOG.info("SQL map client object created: {} {}",  new Object[]{sqlMapClient.hashCode(), sqlMapClient.toString()});
        return sqlMapClient;
    }
}
