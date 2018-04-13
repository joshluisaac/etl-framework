package com.kollect.etl.service;

import java.util.List;
import java.util.Map;

import com.kollect.etl.config.CrudProcessHolder;

public interface IAsyncExecutorService {

  <T> void invoke(final String src, List<T> list, final List<String> sqlQuery, int thread, int commitSize);
  <T> void processEntries(Map<String, CrudProcessHolder> map, List<T> list);

}