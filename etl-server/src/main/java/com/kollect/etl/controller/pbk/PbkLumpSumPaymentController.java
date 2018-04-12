package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PbkLumpSumPaymentController {

  @Autowired
  private PbkLumpSumPaymentService pbkLumpSumPaymentService;

  @PostMapping(value ="/lumpSumPayment")
  @SuppressWarnings("unchecked")
  @ResponseBody
  public Object selectLumSumPayment (@RequestParam Integer batch_id) {
    return pbkLumpSumPaymentService.combinedLumpSumPaymentService(batch_id);
  }
}