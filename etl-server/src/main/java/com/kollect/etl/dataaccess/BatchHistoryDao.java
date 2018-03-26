package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BatchHistoryDao {
    IAbstractSqlSessionProvider sqlSessionProvider;
    public BatchHistoryDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
    }

    public int insertBatchHistory(Object object) {
        return sqlSessionProvider.insert("insertBatchHistory", object);
    }

    public List<Object> viewLatestBatchHistory(Object object) {
        return sqlSessionProvider.queryObject("viewLatestBatchHistory", object);
    }

    public List<Object> viewBatchHistory(Object object) {
        return sqlSessionProvider.queryObject("viewBatchHistory", object);
    }
}
