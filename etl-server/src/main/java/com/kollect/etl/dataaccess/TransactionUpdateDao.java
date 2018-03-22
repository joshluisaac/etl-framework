package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionUpdateDao {

    private IAbstractSqlSessionProvider sqlSessionProvider;

    public TransactionUpdateDao() {
        sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
    }

    public int updateInvoices_AB(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceAB", object);
    }

    public int updateInvoices_RG(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceRG", object);
    }

    public int updateInvoices_YY(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYY", object);
    }

    public int updateInvoices_GI(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceGI", object);
    }

    public int updateInvoices_RI(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceRI", object);
    }

    public int updateInvoices_RM(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceRM", object);
    }

    public int updateInvoices_RV(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceRV", object);
    }

    public int updateInvoices_RY(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceRY", object);
    }

    public int updateInvoices_YC(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYC", object);
    }

    public int updateInvoices_YD(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYD", object);
    }

    public List<Object> getTransactionYH(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYH", object);
    }

    public List<Object> getTransactionYI(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYI", object);
    }

    public List<Object> getTransactionYJ(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYJ", object);
    }

    public List<Object> getTransactionYL(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYL", object);
    }

    public List<Object> getTransactionYN(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYN", object);
    }

    public List<Object> getTransactionYO(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYO", object);
    }

    public List<Object> getTransactionYP(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYP", object);
    }

    public List<Object> getTransactionYQ(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYQ", object);
    }

    public List<Object> getTransactionYR(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYR", object);
    }

    public List<Object> getTransactionYS(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYS", object);
    }

    public List<Object> getTransactionYT(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYT", object);
    }

    public List<Object> getTransactionYU(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYU", object);
    }

    public List<Object> getTransactionYV(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYV", object);
    }

    public List<Object> getTransactionYW(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYW", object);
    }

    public List<Object> getTransactionYX(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYX", object);
    }

    public List<Object> getTransactionYK(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYK", object);
    }

    public List<Object> getTransactionY1(Object object) {
        return sqlSessionProvider.queryObject("getTransactionY1", object);
    }

    public List<Object> getTransactionYE(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYE", object);
    }

    public List<Object> getTransactionYM(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYM", object);
    }

    public List<Object> getTransactionYF(Object object) {
        return sqlSessionProvider.queryObject("getTransactionYF", object);
    }

    public List<Object> getTransactionZZ(Object object) {
        return sqlSessionProvider.queryObject("getTransactionZZ", object);
    }

    public List<Object> getTransactionOthers (Object object) {
        return sqlSessionProvider.queryObject("getTransactionOthers", object);
    }

    public int updateTransactionLoad(Object object) {
        return sqlSessionProvider.update("updateTransactionLoad", object);
    }

}
