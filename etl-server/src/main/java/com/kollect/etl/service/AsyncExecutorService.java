package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("advanced")
public class AsyncExecutorService extends AbstractAsyncExecutorService {

  AsyncExecutorService(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService) {
    super(rwProvider, asyncService);
  }

}
