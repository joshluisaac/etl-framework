package com.kollect.etl.service;

import java.util.List;

public interface IAsyncExecutorService {

  <T> void invoke(List<T> list, String updateQuery, int thread, int commitSize);

}