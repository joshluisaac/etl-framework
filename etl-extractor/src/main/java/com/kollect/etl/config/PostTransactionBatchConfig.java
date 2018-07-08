package com.kollect.etl.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostTransactionBatchConfig implements IBatchConfig {
  
  
  public List<String> getIsDepositChildQuery() {
    return new ArrayList<>(Arrays.asList("updateIsDepositTransaction"));
 }
  
  public List<String> getInvoiceTrxChildQuery() {
    return new ArrayList<>(Arrays.asList("updateInvoiceTransactionType"));
 }
  
  public List<String> getPaymentTrxChildQuery() {
    return new ArrayList<>(Arrays.asList("updatePaymentTransactionType"));
 }
  
  public List<String> getInvoiceStatusIdChildQuery() {
    return new ArrayList<>(Arrays.asList("updateInvoiceStatusId"));
 }
  
  public List<String> getInAgingChildQuery() {
    return new ArrayList<>(Arrays.asList("updateInvoiceInAging"));
 }
  
  public List<String> getPostKeyChildQuery() {
    return new ArrayList<>(Arrays.asList("updateIsInvoiceAndIsCredit"));
 }
  
  public Map<String, CrudProcessHolder> crudHolderMap(Map<String, CrudProcessHolder> map, final String dataSrc) {
//    map.put("1", new CrudProcessHolder("getDepositTransactions", 10, 100, getIsDepositChildQuery()));
//    map.put("2", new CrudProcessHolder("getInvoiceTransactionType", 10, 100, getInvoiceTrxChildQuery()));
//    map.put("3", new CrudProcessHolder("getPaymentTransactionType", 10, 100, getPaymentTrxChildQuery()));
//    map.put("4", new CrudProcessHolder("getInvoiceStatusId", 10, 100, getInvoiceStatusIdChildQuery()));
//    map.put("5", new CrudProcessHolder("getInvoiceInAging", 10, 100, getInAgingChildQuery()));
    map.put("6", new CrudProcessHolder(null,"getTrxPostKeyFlagsForDocTypeAB", 10, 100, getPostKeyChildQuery()));
    return map;
}

}
