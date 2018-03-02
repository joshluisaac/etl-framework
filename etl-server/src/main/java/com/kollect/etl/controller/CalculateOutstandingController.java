package com.kollect.etl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kollect.etl.service.CalcOutstandingService;

@Controller
public class CalculateOutstandingController {


  @Autowired
  private CalcOutstandingService calcOutstandingService;

  /**
   * HTTP GET request to retrieve all batches
   *
   * @return allbatches - used to return the HTML for first time visit.
   */

  @GetMapping("/allbatches")
  public String allBatches() {
    
    return "allbatches";
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
  public Object calcOutstanding (@RequestParam (required = false) Integer tenant_id) {
    List<Object> outstandingList = this.calcOutstandingService.getOutstandingByTenantId(tenant_id);
    int numberOfRows = outstandingList.size();
    for (int i=0;i<numberOfRows;i++) {
      Map<Object, Object> map = (Map<Object, Object>) outstandingList.get(i);
      Map<Object, Object> args = new HashMap<>();
      args.put("invoice_plus_gst", map.get("invoice_plus_gst"));  
      args.put("total_transactions", map.get("total_transactions"));
      args.put("invoice_id", map.get("invoice_id"));
      this.calcOutstandingService.updateOutstanding(args);
    }
    return numberOfRows;
  }
  
}
