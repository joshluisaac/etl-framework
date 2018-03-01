package com.kollect.etl.extractor;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

import java.util.Iterator;
import java.util.List;

public class DataAccess implements IDataAccess{

    IAbstractSqlSessionProvider iSqlSessionProvider;

    public DataAccess(IAbstractSqlSessionProvider iSqlSessionProvider) {
        this.iSqlSessionProvider = iSqlSessionProvider;
    }

    public Iterator<Object> executeQuery(String queryName, Object object) {
        return iSqlSessionProvider.queryMultipleObjects(queryName, object);
    }

    public <T> List<T> executeQueryObject(String queryName, Object object) {
        return iSqlSessionProvider.queryObject(queryName, object);
    }


}
