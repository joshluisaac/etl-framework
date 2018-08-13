package com.kollect.etl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//@Component
public class TransferServiceImpl implements TransferService {
  
  private static final Logger LOG = LoggerFactory.getLogger(TransferService.class);

  @Override
  public String transName() {
    LOG.debug("{}", "Transfer com.kollect.etl.service initliazed");
    return "From transfer com.kollect.etl.service";
  }

}
