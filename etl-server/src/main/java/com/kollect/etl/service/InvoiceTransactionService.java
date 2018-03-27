package com.kollect.etl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceTransactionService {
  
  
    @Autowired
    private ReadWriteServiceProvider rwProvider;
    
    private boolean lock;

    @SuppressWarnings("unchecked")
    public int executeInvoiceTransactionService(){
        int numberOfRows = -1;
        if (!lock) {
            lock = true;
            List<Object> getInvoiceTransaction = rwProvider.executeQuery("getInvoiceTransaction", null);

            int numberOfRecords = getInvoiceTransaction.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) getInvoiceTransaction.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("load_id", map.get("load_id"));
                args.put("tenant_id", map.get("tenant_id"));
                args.put("import_id", map.get("import_id"));
                args.put("account_id", map.get("account_id"));
                args.put("invoice_date", map.get("invoice_date"));
                args.put("invoice_no", map.get("invoice_no"));
                args.put("invoice_description", map.get("invoice_description"));
                args.put("invoice_due_date", map.get("invoice_due_date"));
                args.put("invoice_amount", map.get("invoice_amount"));
                args.put("invoice_outstanding", map.get("invoice_outstanding"));
                args.put("dpd", map.get("dpd"));
                args.put("mpd", map.get("mpd"));
                args.put("status_id", map.get("status_id"));
                args.put("created_at", map.get("created_at"));
                args.put("updated_at", map.get("updated_at"));
                args.put("created_by", map.get("created_by"));
                args.put("updated_by", map.get("updated_by"));
                args.put("invoice_status_id", map.get("invoice_status_id"));
                args.put("invoice_outstanding_with_interest", map.get("invoice_outstanding_with_interest"));
                args.put("gst", map.get("gst"));
                args.put("import_status_id", map.get("import_status_id"));
                args.put("import_note", map.get("import_note"));
                args.put("branch_id", map.get("branch_id"));
                args.put("branch_code", map.get("branch_code"));
                args.put("invoice_id", map.get("invoice_id"));
                args.put("in_aging", map.get("in_aging"));
                args.put("invoice_pic_code", map.get("invoice_pic_code"));
                args.put("invoice_pic_name", map.get("invoice_pic_name"));
                args.put("flag_id", map.get("flag_id"));
                args.put("last_listener_run", map.get("last_listener_run"));
                args.put("line_of_business", map.get("line_of_business"));
                args.put("account_num", map.get("account_num"));
                args.put("contract_id", map.get("contract_id"));
                args.put("recon_acct", map.get("recon_acct"));
                args.put("currency", map.get("currency"));
                args.put("tax_code", map.get("tax_code"));
                args.put("tax_id", map.get("tax_id"));
                args.put("clearing_doc", map.get("clearing_doc"));
                args.put("reference", map.get("reference"));
                args.put("assignment", map.get("assignment"));
                args.put("item_no", map.get("item_no"));
                args.put("document_id", map.get("document_id"));
                args.put("business_area_id", map.get("business_area_id"));
                args.put("payment_id", map.get("payment_id"));
                args.put("balance", map.get("balance"));
                args.put("tax_amount", map.get("tax_amount"));
                args.put("payment_reference", map.get("payment_reference"));
                args.put("document_no", map.get("document_no"));
                args.put("mode", map.get("mode"));
                args.put("spec_gl_ind", map.get("spec_gl_ind"));
                args.put("amount_dc", map.get("amount_dc"));
                args.put("post_key", map.get("post_key"));
                args.put("trx_date", map.get("trx_date"));
                args.put("reference_id", map.get("reference_id"));
                args.put("cleardoc_id", map.get("cleardoc_id"));
                args.put("invoice_days", map.get("invoice_days"));
                args.put("inv_ref", map.get("inv_ref"));
                args.put("bill_doc", map.get("bill_doc"));
                args.put("payment_term", map.get("payment_term"));
                args.put("baseline_date", map.get("baseline_date"));
                args.put("contract_num", map.get("contract_num"));
                args.put("old_contract_id", map.get("old_contract_id"));
                args.put("kv_amount", map.get("kv_amount"));
                args.put("kv_amount_dc", map.get("kv_amount_dc"));
                args.put("load_execution_id", map.get("load_execution_id"));
                args.put("invoice_reference", map.get("invoice_reference"));
               
//                args.put("company_id", map.get("company_id"));
//                args.put("is_commercial", map.get("is_commercial"));
//                args.put("is_deposit", map.get("is_deposit"));
//                args.put("is_invoice", map.get("is_invoice"));
//                args.put("is_credit_note", map.get("is_credit_note"));
//                args.put("parent_invoice_no", map.get("parent_invoice_no"));
//                args.put("doc_type", map.get("doc_type"));
//                args.put("transaction_type_id", map.get("transaction_type_id"));
//                args.put("account_no", map.get("account_no"));

                int updateCount = rwProvider.updateQuery("updateInvoiceTransaction", args);
                if (updateCount == 0) {
                  rwProvider.insertQuery("insertInvoiceTransaction", args);
                }
            }
            lock = false;
            numberOfRows = numberOfRecords;
        }
        System.out.println("InvoiceTransaction - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }
}
