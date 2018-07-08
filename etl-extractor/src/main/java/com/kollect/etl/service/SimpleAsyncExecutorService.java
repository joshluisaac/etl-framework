package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("simple")
public class SimpleAsyncExecutorService  extends AbstractAsyncExecutorService {

  public SimpleAsyncExecutorService(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService) {
    super(rwProvider, asyncService);
  }

}
