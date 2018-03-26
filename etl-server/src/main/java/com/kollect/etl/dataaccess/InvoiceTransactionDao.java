package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceTransactionDao {

    IAbstractSqlSessionProvider sqlSessionProvider;

    public InvoiceTransactionDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
    }

    public List<Object> getInvoiceTransaction(Object object) {
        // TODO Auto-generated method stub
        return sqlSessionProvider.queryObject("getInvoiceTransaction", object);
    }

    public int updateInvoiceTransaction(Object object) {
        return sqlSessionProvider.update("updateInvoiceTransaction", object);
    }

    public int insertInvoiceTransaction(Object object) {
        return sqlSessionProvider.insert("insertInvoiceTransaction", object);
    }
}
