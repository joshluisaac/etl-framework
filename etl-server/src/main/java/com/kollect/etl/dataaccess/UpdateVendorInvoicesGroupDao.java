package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;


@Repository
public class UpdateVendorInvoicesGroupDao {

    private IAbstractSqlSessionProvider sqlSessionProvider;
    public UpdateVendorInvoicesGroupDao(){
        sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
    }

    public int insertVendorInvoicesGroup1(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup1", object);
    }

    public int insertVendorInvoicesGroup2(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup2", object);
    }
    public int insertVendorInvoicesGroup3(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup3", object);
    }
    public int insertVendorInvoicesGroup4(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup4", object);
    }
    public int insertVendorInvoicesGroup5(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup5", object);
    }
    public int insertVendorInvoicesGroup6(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup6", object);
    }
    public int insertVendorInvoicesGroup7(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup7", object);
    }
    public int insertVendorInvoicesGroup8(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup8", object);
    }
    public int insertVendorInvoicesGroup9(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup9", object);
    }
    public int insertVendorInvoicesGroup10(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup10", object);
    }
    public int insertVendorInvoicesGroup11(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup11", object);
    }
    public int insertVendorInvoicesGroup12(Object object) {
        return sqlSessionProvider.insert("mergeVendorInvoicesGroup12", object);
    }

}
