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

import com.kollect.etl.service.LumpSumPaymentService;

@Controller
public class LumpSumPaymentController {

  @Autowired
  private LumpSumPaymentService lumpSumPaymentService;
  private boolean lock = false;

  @PostMapping(value ="/lumpSumPayment")
  @SuppressWarnings("unchecked")
  @ResponseBody
  public Object selectLumSumPayment () {
    int numberOfUpdate = 0;
    if (!lock) {
        lock = true;
        this.lumpSumPaymentService.deleteNetLumpSum(null);
        numberOfUpdate = this.lumpSumPaymentService.numbOfRowsFunction();
        lock = false;
    }
    return numberOfUpdate;
  }
}