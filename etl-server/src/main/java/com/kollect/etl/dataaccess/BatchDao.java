package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BatchDao {
    IAbstractSqlSessionProvider sqlSessionProvider;
    public BatchDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
    }

    public int insertBatch(Object object) {
        return sqlSessionProvider.insert("insertBatch", object);
    }

    public List<Object> viewBatch(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("viewBatch", object);
    }

    public List<Object> getBatchById(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("getBatchById", object);
    }

    public int updateBatch(Object object) {
        return sqlSessionProvider.insert("updateBatch", object);
    }
}
