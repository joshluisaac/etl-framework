package com.kollect.etl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kollect.etl.service.LumpSumPaymentService;

@Controller
public class LumpSumPaymentController {

  @Autowired
  private LumpSumPaymentService lumpSumPaymentService;

  @PostMapping(value ="/lumpSumPayment")
  @SuppressWarnings("unchecked")
  @ResponseBody
  public Object selectLumSumPayment (@RequestParam Integer batch_id) {
    return lumpSumPaymentService.combinedLumpSumPaymentService(batch_id);
  }
}