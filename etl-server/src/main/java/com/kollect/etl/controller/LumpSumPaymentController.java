package com.kollect.etl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kollect.etl.service.LumpSumPaymentService;

@Controller
public class LumpSumPaymentController {


@Autowired
  private LumpSumPaymentService lumpSumPaymentService;
  
  @PostMapping(value ="/lumsumpayment")
  @SuppressWarnings("unchecked")

  public Object selectLumSumPayment () {
    List<Object> selectLumSumPaymentList = this.lumpSumPaymentService.getSumAmount(null);
    for ( int x = 0; x<selectLumSumPaymentList.size(); x++) {
      Map<Object, Object> map = (Map<Object, Object>) selectLumSumPaymentList.get(x);
      Map<Object, Object> args = new HashMap<>();
      args.put("account_id", map.get("account_id"));
      args.put("net_lump_sum_amount", map.get("net_lump_sum_amount"));
      System.out.println(args);
     int updateCount = this.lumpSumPaymentService.updateGetSumAmount(args);
     if (updateCount == 0) {
       this.lumpSumPaymentService.insertGetSumAmount(args);
     }
    }
    return "allbatches";
  }
}