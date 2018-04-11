package com.kollect.etl.controller;

import com.kollect.etl.service.PbkCalcOutstandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PbkCalcOutstandingController {
  private PbkCalcOutstandingService pbkCalcOutstandingService;
  @Autowired
  public PbkCalcOutstandingController(PbkCalcOutstandingService pbkCalcOutstandingService){
   this.pbkCalcOutstandingService = pbkCalcOutstandingService;
  }

  /**
   * HTTP POST request mapping to run the batch
   *
   * ResponseBody is used to return a json value to the URL needed as a result of the ajax
   *
   * @param tenant_id
   *            this is the tenant id that determines the client, e.g. 63 for PBK
   *
   * @return returns the number of rows updated as json
   */

  @PostMapping(value = "/calcoutstanding", produces="application/json")
  @SuppressWarnings("unchecked")
  @ResponseBody
  public Object calcOutstanding (@RequestParam (required = false) Integer tenant_id, @RequestParam Integer batch_id) {
    return this.pbkCalcOutstandingService.combinedCalcOutstanding(batch_id);
  }
  
}
