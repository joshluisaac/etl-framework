package com.kollect.etl.service;

import java.util.List;
import java.util.Map;

import com.kollect.etl.config.CrudProcessHolder;

public interface IAsyncExecutorService {

  <T> void invoke(List<T> list, String updateQuery, int thread, int commitSize);
  <T> void cycleAndProcessEntries(Map<String, CrudProcessHolder> map, List<T> list);
  void cycleAndProcessEntries(Map<String, CrudProcessHolder> map);

}