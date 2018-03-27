package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgeInvoiceDao {
    private IAbstractSqlSessionProvider sqlSessionProvider;

    public AgeInvoiceDao(){
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pbk");
    }
    public List<Object> getAgeInvoiceById(Object object) {
        return sqlSessionProvider.queryObject("getAgeInvoiceById", object);
    }

    public int updateAgeInvoice(Object object) {
        return sqlSessionProvider.update("updateAgeInvoice", object);
    }
}
