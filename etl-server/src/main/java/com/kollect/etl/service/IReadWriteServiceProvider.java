package com.kollect.etl.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface IReadWriteServiceProvider {
  SqlSession getBatchSqlSession();
  SqlSession getBatchSqlSession(String source);
  int updateQuery(String queryName, Object args);
  int updateQuery(final String source, final String queryName, Object args);
  int insertQuery(String queryName, Object args);
  int insertQuery(final String source, final String queryName, Object args);
  <T> List<T> executeQuery(String queryName, Object args);
  <T> List<T> executeQuery(final String source, final String queryName, Object args);
  <T> Iterator<T> executeQueryItr(String queryName, Object args);
  <T> Iterator<T> executeQueryItr(final String source, final String queryName, Object args);
}