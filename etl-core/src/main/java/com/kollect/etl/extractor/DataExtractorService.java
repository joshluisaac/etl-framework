package com.kollect.etl.extractor;

import java.util.Iterator;
import java.util.List;

public class DataExtractorService implements IDataExtractorService {

   IDataAccess dao;

    public DataExtractorService(final IDataAccess dao){
        this.dao = dao;
    }

    public Iterator<Object> executeQuery(String queryName, Object object) {
        return dao.executeQuery(queryName, object);
    }


    public <T> List<T> executeQueryObject(String queryName, Object object) {
        return dao.executeQueryObject(queryName, object);
    }

}
