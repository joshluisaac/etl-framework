package com.kollect.etl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.kollect.etl.service.AsyncBatchService;
import com.kollect.etl.service.PostTransactionService;

@Controller
public class PostTransactionController {

  @Autowired
<<<<<<< HEAD
  private PostTransactionService service;
  @Autowired
  private AsyncBatchService asyncService;
=======
  private ReadWriteServiceProvider rwProvider;
  @Autowired
  private AsyncBatchService asyncService;
  @Autowired
  private ComponentProvider compProvider;

>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
  private static final String REGEX_PATTERN = "^3[0-9]{8}";

  @PostMapping(value = "/updatePostTrx")
  @ResponseBody
  public Object updatePostTransaction() {
    String[] postJobs = { "updateIsDepositTransaction", "updateInvoiceTransactionType", "updatePaymentTransactionType",
        "updateInvoiceStatusId", "updateInvoiceInAging" };
    for (String string : postJobs) {
<<<<<<< HEAD
      service.updateQuery(string,null);
=======
      rwProvider.updateQuery(string, null);
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
    }

    return 0;
  }

  @PostMapping(value = "/updateParentInvoiceNo")
  @ResponseBody
  public Object updateParentInvoiceNo() {
    String[] postJobs = { "updateParentInvoiceNo" };
    for (String string : postJobs) {
<<<<<<< HEAD
      service.updateQuery(string,null);
=======
      rwProvider.updateQuery(string, null);
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
    }
    return 0;
  }

  @SuppressWarnings("unchecked")
  @PostMapping(value = "/isCommercial")
  @ResponseBody
  public Object updateIsCommercialAccount() {
<<<<<<< HEAD
    List<Object> list = service.executeQuery("getAccount", null);
    List<Map<String, Object>> mapList = new ArrayList<>();
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
        Map<String, Object> map = (Map<String, Object>) list.get(i);
        Map<String, Object> args = new HashMap<>();
        String accountNo = (String) map.get("account_no");
        boolean isCommercial = service.isCommericalResolver(accountNo, REGEX_PATTERN);
        args.put("account_no", accountNo);
        args.put("load_id", map.get("load_id"));
        args.put("isCommercial", isCommercial);
        mapList.add(args);
=======
    List<Object> list = rwProvider.executeQuery("getAccount", null);
    List<Map<String, Object>> mapList = new ArrayList<>();
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
      Map<String, Object> map = (Map<String, Object>) list.get(i);
      Map<String, Object> args = new HashMap<>();
      String accountNo = (String) map.get("account_no");
      boolean isCommercial = compProvider.isCommericalResolver(accountNo, REGEX_PATTERN);
      args.put("account_no", accountNo);
      args.put("load_id", map.get("load_id"));
      args.put("isCommercial", isCommercial);
      mapList.add(args);
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
    }
    
    System.out.println(mapList.toString());
    
    Iterator<Map<String, Object>> mQueryResults = mapList.iterator();
    asyncService.execute(mQueryResults, "updateCommercialTransaction", 10, 100);

    return 0;
  }

  @SuppressWarnings("unchecked")
  @PostMapping(value = "/updateIsInvoiceAndIsCredit")
  @ResponseBody
  public Object updateIsInvoiceAndIsCredit() {
<<<<<<< HEAD
    List<Object> list = service.executeQuery("getTrxByPostKey", null);
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
        Map<Object, Object> map = (Map<Object, Object>) list.get(i);
        Map<Object, Object> args = new HashMap<>();
        args.put("invoice", map.get("invoice"));
        args.put("load_id", map.get("load_id"));
        args.put("credit_note", map.get("credit_note"));
        this.service.updateQuery("updateIsInvoiceAndIsCredit",args);
=======
    List<Object> list = rwProvider.executeQuery("getTrxPostKeyFlagsForDocTypeAB", null);
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
      Map<Object, Object> map = (Map<Object, Object>) list.get(i);
      Map<Object, Object> args = new HashMap<>();
      args.put("invoice", map.get("invoice"));
      args.put("load_id", map.get("load_id"));
      args.put("credit_note", map.get("credit_note"));
      this.rwProvider.updateQuery("updateIsInvoiceAndIsCredit", args);
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
    }
    return 0;
  }
<<<<<<< HEAD
  
  
  
  
=======

  @PostMapping(value = "/test")
  @ResponseBody
  public Object test() {
    Iterator<Object> mQueryResults = rwProvider.executeQueryItr("getAllCustomer", null);
    asyncService.execute(mQueryResults, "updateTest", 1, 1);
    return 0;
  }
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027

}
