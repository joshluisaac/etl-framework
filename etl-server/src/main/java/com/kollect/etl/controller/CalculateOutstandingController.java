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

  @GetMapping("/allbatches")
  public String allBatches() {
    
    return "allbatches";
  }

  @PostMapping(value = "/allbatches", produces="application/json")
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