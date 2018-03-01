package com.kollect.etl.services.business.batch;

import java.util.Date;
import java.util.Map;

public interface IBatchProcess {
  
  public TransferObject invoke(String identifier, Date effectiveDate, Map<Object, Object> arguments);

}
