package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

@Repository
public class UpdateVendorInvoicesDao {

    private IAbstractSqlSessionProvider sqlSessionProvider;
    public UpdateVendorInvoicesDao(){
        sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
    }
}
