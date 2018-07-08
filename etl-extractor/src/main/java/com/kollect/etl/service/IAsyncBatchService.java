package com.kollect.etl.service;

import java.util.Iterator;

public interface IAsyncBatchService {

  <T> void execute(Iterator<T> itr, IRunnableProcess runnableProcess, int thread, int commitSize);

}