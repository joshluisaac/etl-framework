package com.kollect.etl.dataaccess;

public interface IDaoFactory {

  IDaoProvider getDaoSource(String dataSource);

}