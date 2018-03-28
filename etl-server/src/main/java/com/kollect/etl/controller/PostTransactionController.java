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

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.AsyncBatchService;

import com.kollect.etl.service.ReadWriteServiceProvider;

@Controller
public class PostTransactionController {

  @Autowired
  private ReadWriteServiceProvider rwProvider;
  @Autowired
  private AsyncBatchService asyncService;
  @Autowired
  private ComponentProvider compProvider;

  private static final String REGEX_PATTERN = "^3[0-9]{8}";

  @PostMapping(value = "/updatePostTrx")
  @ResponseBody
  public Object updatePostTransaction() {
    String[] postJobs = { "updateIsDepositTransaction", "updateInvoiceTransactionType", "updatePaymentTransactionType",
        "updateInvoiceStatusId", "updateInvoiceInAging" };
    for (String string : postJobs) {
      rwProvider.updateQuery(string, null);
    }

    return 0;
  }

  @PostMapping(value = "/updateParentInvoiceNo")
  @ResponseBody
  public Object updateParentInvoiceNo() {
    String[] postJobs = { "updateParentInvoiceNo" };
    for (String string : postJobs) {
      rwProvider.updateQuery(string, null);
    }
    return 0;
  }

  @SuppressWarnings("unchecked")
  @PostMapping(value = "/isCommercial")
  @ResponseBody
  public Object updateIsCommercialAccount() {
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
    }
    Iterator<Map<String, Object>> mQueryResults = mapList.iterator();
    asyncService.execute(mQueryResults, "updateCommercialTransaction", 10, 100);

    return 0;
  }

  @SuppressWarnings("unchecked")
  @PostMapping(value = "/updateIsInvoiceAndIsCredit")
  @ResponseBody
  public Object updateIsInvoiceAndIsCredit() {
    List<Object> list = rwProvider.executeQuery("getTrxPostKeyFlagsForDocTypeAB", null);
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
      Map<Object, Object> map = (Map<Object, Object>) list.get(i);
      Map<Object, Object> args = new HashMap<>();
      args.put("invoice", map.get("invoice"));
      args.put("load_id", map.get("load_id"));
      args.put("credit_note", map.get("credit_note"));
      this.rwProvider.updateQuery("updateIsInvoiceAndIsCredit", args);
    }
    return 0;
  }

  @PostMapping(value = "/test")
  @ResponseBody
  public Object test() {
    Iterator<Object> mQueryResults = rwProvider.executeQueryItr("getAllCustomer", null);
    asyncService.execute(mQueryResults, "updateTest", 1, 1);
    return 0;
  }

}
