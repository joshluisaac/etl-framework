package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface IReadWriteServiceProvider {

  SqlSession getBatchSqlSession();

  int updateQuery(String queryName, Object args);

  int insertQuery(String queryName, Object args);

  <T> List<T> executeQuery(String queryName, Object args);

  <T> Iterator<T> executeQueryItr(String queryName, Object args);

}