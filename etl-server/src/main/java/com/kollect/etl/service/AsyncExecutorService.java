package com.kollect.etl.service;

import org.springframework.stereotype.Service;

@Service
public class AsyncExecutorService extends AbstractAsyncExecutorService {

  AsyncExecutorService(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService) {
    super(rwProvider, asyncService);
  }

}
