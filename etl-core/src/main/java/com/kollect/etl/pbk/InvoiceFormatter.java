package com.kollect.etl.pbk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InvoiceFormatter implements Formattable {

  @SuppressWarnings("unchecked")
  public List<String> format(Iterator<Object> itr) {
    List<String> list = new ArrayList<>();
    while (itr.hasNext()) {
      Object row = itr.next();
      Map<String, Object> rowMap = (Map<String, Object>) row;
      Object[] line = { 
          rowMap.get("invoice_id"), 
          rowMap.get("series_id"), 
          rowMap.get("invoice_no"),
          rowMap.get("invoice_type"),
          rowMap.get("customer_id"),
          rowMap.get("line_of_business"),
          rowMap.get("name"),
          rowMap.get("delivery_date"),
          rowMap.get("invoice_date"),
          rowMap.get("invoice_due_date"),
          rowMap.get("series_reference"),
          rowMap.get("number_reference"),
          rowMap.get("invoice_amount"),
          rowMap.get("gst"),
          rowMap.get("invoice_total_amount"),
          rowMap.get("invoice_os_local"),
          rowMap.get("invoice_os_foreign"),
          rowMap.get("invoice_status"),
          rowMap.get("currency"),
          rowMap.get("derived_invoice_no_1"),
          rowMap.get("derived_invoice_no_2")
          
      };
      //String message = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7, date,dd/MM/yyyy}|{8, date,dd/MM/yyyy}|{9, date,dd/MM/yyyy}|{10}|{11}|{12}", line);
      String message = MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}|{14}|{15}|{16}|{17}|{18}|{19}|{20}", line).replaceAll("null", "");
      list.add(message);
    }
    return list;
  }
}
