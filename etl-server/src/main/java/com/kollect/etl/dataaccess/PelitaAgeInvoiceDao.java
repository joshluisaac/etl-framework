package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PelitaAgeInvoiceDao {
    private IAbstractSqlSessionProvider sqlSessionProvider;

    public PelitaAgeInvoiceDao(){
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pelita_test");
    }
    public List<Object> getAgeInvoiceById(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("getPelitaAgeInvoiceById", object);
    }

    public int updateAgeInvoice(Object object) {
        return sqlSessionProvider.update("pelitaUpdateAgeInvoice", object);
    }
}


