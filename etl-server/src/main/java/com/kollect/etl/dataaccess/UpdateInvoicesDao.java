package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

@Repository
public class UpdateInvoicesDao {

    private IAbstractSqlSessionProvider sqlSessionProvider;
    public UpdateInvoicesDao(){
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

    public int updateInvoices_YH(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYH", object);
    }

    public int updateInvoices_YI(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYI", object);
    }

    public int updateInvoices_YJ(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYJ", object);
    }

    public int updateInvoices_YL(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYL", object);
    }

    public int updateInvoices_YN(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYN", object);
    }

    public int updateInvoices_YO(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYO", object);
    }

    public int updateInvoices_YP(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYP", object);
    }

    public int updateInvoices_YQ(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYQ", object);
    }

    public int updateInvoices_YR(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYR", object);
    }

    public int updateInvoices_YS(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYS", object);
    }

    public int updateInvoices_YT(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYT", object);
    }

    public int updateInvoices_YU(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYU", object);
    }

    public int updateInvoices_YV(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYV", object);
    }

    public int updateInvoices_YW(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYW", object);
    }

    public int updateInvoices_YX(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYX", object);
    }

    public int updateInvoices_YK(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYK", object);
    }

    public int updateInvoices_Y1(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceY1", object);
    }

    public int updateInvoices_YE(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYE", object);
    }

    public int updateInvoices_YM(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYM", object);
    }

    public int updateInvoices_YF(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceYF", object);
    }

    public int updateInvoices_ZZ(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceZZ", object);
    }

    public int updateInvoices_OTHERS(Object object) {
        return sqlSessionProvider.insert("updateTransactionInvoiceOthers", object);
    }

}
