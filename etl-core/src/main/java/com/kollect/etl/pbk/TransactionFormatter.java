package com.kollect.etl.pbk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TransactionFormatter implements Formattable {
  
  @SuppressWarnings("unchecked")
  public List<String> format(Iterator<Object> itr) {
    List<String> list = new ArrayList<>();
    while (itr.hasNext()) {
      Object row = itr.next();
      Map<String, Object> rowMap = (Map<String, Object>) row;
      Object[] line = { 
          rowMap.get("payment_id"), 
          rowMap.get("transaction_id"), 
          rowMap.get("transaction_no"), 
          rowMap.get("line_of_business"),
          rowMap.get("customer_id"),
          rowMap.get("item_series_id"),
          rowMap.get("item_id"),
          rowMap.get("amount"),
          rowMap.get("transaction_date"),
          rowMap.get("invoice_id"),
          rowMap.get("currency"),
          rowMap.get("voucher_type")
      };
      String message = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}", line).replaceAll("null", "");
      list.add(message);
    }
    return list;
  }

}
