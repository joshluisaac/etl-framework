package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PelitaComputeInvoiceAmountAfterTaxDao {
    IAbstractSqlSessionProvider sqlSessionProvider;

    public PelitaComputeInvoiceAmountAfterTaxDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("postgres_pelita_test");
    }

    public List<Object> getInvoiceAmountAfterTax(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("getInvoiceAmountAfterTaxById", object);
    }

    public int updateInvoiceAmountAfterTax(Object object) {

        return sqlSessionProvider.update("updateInvoiceAmountAfterTax", object);
    }
}
