package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UpdateVendorInvoicesGroupDao {

    private IAbstractSqlSessionProvider sqlSessionProvider;
    public UpdateVendorInvoicesGroupDao(){
        sqlSessionProvider = new AbstractSqlSessionProvider("mahb_prod");
    }

    public List<Object> getTotalAmount(Object object) {
        return sqlSessionProvider.queryObject("getTotalAmount", object);
    }
}
