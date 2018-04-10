package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PelitaCalcOutstandingDao {
    IAbstractSqlSessionProvider sqlSessionProvider;

    public PelitaCalcOutstandingDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pelita_test");
    }

    public List<Object> getOutstandingByTenantId(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("getPelitaOutstandingByTenantId", object);
    }

    public int updateOutstanding(Object object) {
        return sqlSessionProvider.update("updatePelitaOutstanding", object);
    }
}
