package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

@Repository
public class UpdateDataDateDao {
    IAbstractSqlSessionProvider sqlSessionProvider;

    public UpdateDataDateDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pbk");
    }

    public int updateDataDate() {
        return sqlSessionProvider.update("updateDataDate", null);
    }
}
