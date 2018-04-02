package com.kollect.etl.entity;

import java.sql.Timestamp;

public class Invoice {

  long id, load_id, tenant_id, import_id, account_id, dpd, mpd, status_id, created_by, updated_by, invoice_status_id,
      import_status_id, branch_id, invoice_id, flag_id, contract_id, tax_id, item_no, document_id, business_area_id,
      payment_id, reference_id, cleardoc_id, invoice_days, bill_doc, old_contract_id, load_execution_id;

  Timestamp invoice_date, invoice_due_date, created_at, updated_at, last_listener_run, trx_date, baseline_date;

  String invoice_no, invoice_description, import_note, branch_code, invoice_pic_code, invoice_pic_name,
      line_of_business, account_num, recon_acct, currency, tax_code, clearing_doc, reference, assignment,
      payment_reference, document_no, mode, spec_gl_ind, post_key, inv_ref, payment_term, contract_num,
      invoice_reference;

  boolean in_aging, updateRow;

  double invoice_amount, invoice_outstanding, invoice_outstanding_with_interest, gst, balance, tax_amount, amount_dc,
      kv_amount, kv_amount_dc;
  
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLoad_id() {
    return load_id;
  }

  public void setLoad_id(long load_id) {
    this.load_id = load_id;
  }

  public long getTenant_id() {
    return tenant_id;
  }

  public void setTenant_id(long tenant_id) {
    this.tenant_id = tenant_id;
  }

  public long getImport_id() {
    return import_id;
  }

  public void setImport_id(long import_id) {
    this.import_id = import_id;
  }

  public long getAccount_id() {
    return account_id;
  }

  public void setAccount_id(long account_id) {
    this.account_id = account_id;
  }

  public long getDpd() {
    return dpd;
  }

  public void setDpd(long dpd) {
    this.dpd = dpd;
  }

  public long getMpd() {
    return mpd;
  }

  public void setMpd(long mpd) {
    this.mpd = mpd;
  }

  public long getStatus_id() {
    return status_id;
  }

  public void setStatus_id(long status_id) {
    this.status_id = status_id;
  }

  public long getCreated_by() {
    return created_by;
  }

  public void setCreated_by(long created_by) {
    this.created_by = created_by;
  }

  public long getUpdated_by() {
    return updated_by;
  }

  public void setUpdated_by(long updated_by) {
    this.updated_by = updated_by;
  }

  public long getInvoice_status_id() {
    return invoice_status_id;
  }

  public void setInvoice_status_id(long invoice_status_id) {
    this.invoice_status_id = invoice_status_id;
  }

  public long getImport_status_id() {
    return import_status_id;
  }

  public void setImport_status_id(long import_status_id) {
    this.import_status_id = import_status_id;
  }

  public long getBranch_id() {
    return branch_id;
  }

  public void setBranch_id(long branch_id) {
    this.branch_id = branch_id;
  }

  public long getInvoice_id() {
    return invoice_id;
  }

  public void setInvoice_id(long invoice_id) {
    this.invoice_id = invoice_id;
  }

  public long getFlag_id() {
    return flag_id;
  }

  public void setFlag_id(long flag_id) {
    this.flag_id = flag_id;
  }

  public long getContract_id() {
    return contract_id;
  }

  public void setContract_id(long contract_id) {
    this.contract_id = contract_id;
  }

  public long getTax_id() {
    return tax_id;
  }

  public void setTax_id(long tax_id) {
    this.tax_id = tax_id;
  }

  public long getItem_no() {
    return item_no;
  }

  public void setItem_no(long item_no) {
    this.item_no = item_no;
  }

  public long getDocument_id() {
    return document_id;
  }

  public void setDocument_id(long document_id) {
    this.document_id = document_id;
  }

  public long getBusiness_area_id() {
    return business_area_id;
  }

  public void setBusiness_area_id(long business_area_id) {
    this.business_area_id = business_area_id;
  }

  public long getPayment_id() {
    return payment_id;
  }

  public void setPayment_id(long payment_id) {
    this.payment_id = payment_id;
  }

  public long getReference_id() {
    return reference_id;
  }

  public void setReference_id(long reference_id) {
    this.reference_id = reference_id;
  }

  public long getCleardoc_id() {
    return cleardoc_id;
  }

  public void setCleardoc_id(long cleardoc_id) {
    this.cleardoc_id = cleardoc_id;
  }

  public long getInvoice_days() {
    return invoice_days;
  }

  public void setInvoice_days(long invoice_days) {
    this.invoice_days = invoice_days;
  }

  public long getBill_doc() {
    return bill_doc;
  }

  public void setBill_doc(long bill_doc) {
    this.bill_doc = bill_doc;
  }

  public long getOld_contract_id() {
    return old_contract_id;
  }

  public void setOld_contract_id(long old_contract_id) {
    this.old_contract_id = old_contract_id;
  }

  public long getLoad_execution_id() {
    return load_execution_id;
  }

  public void setLoad_execution_id(long load_execution_id) {
    this.load_execution_id = load_execution_id;
  }

  public Timestamp getInvoice_date() {
    return invoice_date;
  }

  public void setInvoice_date(Timestamp invoice_date) {
    this.invoice_date = invoice_date;
  }

  public Timestamp getInvoice_due_date() {
    return invoice_due_date;
  }

  public void setInvoice_due_date(Timestamp invoice_due_date) {
    this.invoice_due_date = invoice_due_date;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  public Timestamp getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Timestamp updated_at) {
    this.updated_at = updated_at;
  }

  public Timestamp getLast_listener_run() {
    return last_listener_run;
  }

  public void setLast_listener_run(Timestamp last_listener_run) {
    this.last_listener_run = last_listener_run;
  }

  public Timestamp getTrx_date() {
    return trx_date;
  }

  public void setTrx_date(Timestamp trx_date) {
    this.trx_date = trx_date;
  }

  public Timestamp getBaseline_date() {
    return baseline_date;
  }

  public void setBaseline_date(Timestamp baseline_date) {
    this.baseline_date = baseline_date;
  }

  public String getInvoice_no() {
    return invoice_no;
  }

  public void setInvoice_no(String invoice_no) {
    this.invoice_no = invoice_no;
  }

  public String getInvoice_description() {
    return invoice_description;
  }

  public void setInvoice_description(String invoice_description) {
    this.invoice_description = invoice_description;
  }

  public String getImport_note() {
    return import_note;
  }

  public void setImport_note(String import_note) {
    this.import_note = import_note;
  }

  public String getBranch_code() {
    return branch_code;
  }

  public void setBranch_code(String branch_code) {
    this.branch_code = branch_code;
  }

  public String getInvoice_pic_code() {
    return invoice_pic_code;
  }

  public void setInvoice_pic_code(String invoice_pic_code) {
    this.invoice_pic_code = invoice_pic_code;
  }

  public String getInvoice_pic_name() {
    return invoice_pic_name;
  }

  public void setInvoice_pic_name(String invoice_pic_name) {
    this.invoice_pic_name = invoice_pic_name;
  }

  public String getLine_of_business() {
    return line_of_business;
  }

  public void setLine_of_business(String line_of_business) {
    this.line_of_business = line_of_business;
  }

  public String getAccount_num() {
    return account_num;
  }

  public void setAccount_num(String account_num) {
    this.account_num = account_num;
  }

  public String getRecon_acct() {
    return recon_acct;
  }

  public void setRecon_acct(String recon_acct) {
    this.recon_acct = recon_acct;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTax_code() {
    return tax_code;
  }

  public void setTax_code(String tax_code) {
    this.tax_code = tax_code;
  }

  public String getClearing_doc() {
    return clearing_doc;
  }

  public void setClearing_doc(String clearing_doc) {
    this.clearing_doc = clearing_doc;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getAssignment() {
    return assignment;
  }

  public void setAssignment(String assignment) {
    this.assignment = assignment;
  }

  public String getPayment_reference() {
    return payment_reference;
  }

  public void setPayment_reference(String payment_reference) {
    this.payment_reference = payment_reference;
  }

  public String getDocument_no() {
    return document_no;
  }

  public void setDocument_no(String document_no) {
    this.document_no = document_no;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getSpec_gl_ind() {
    return spec_gl_ind;
  }

  public void setSpec_gl_ind(String spec_gl_ind) {
    this.spec_gl_ind = spec_gl_ind;
  }

  public String getPost_key() {
    return post_key;
  }

  public void setPost_key(String post_key) {
    this.post_key = post_key;
  }

  public String getInv_ref() {
    return inv_ref;
  }

  public void setInv_ref(String inv_ref) {
    this.inv_ref = inv_ref;
  }

  public String getPayment_term() {
    return payment_term;
  }

  public void setPayment_term(String payment_term) {
    this.payment_term = payment_term;
  }

  public String getContract_num() {
    return contract_num;
  }

  public void setContract_num(String contract_num) {
    this.contract_num = contract_num;
  }

  public String getInvoice_reference() {
    return invoice_reference;
  }

  public void setInvoice_reference(String invoice_reference) {
    this.invoice_reference = invoice_reference;
  }

  public boolean isIn_aging() {
    return in_aging;
  }

  public void setIn_aging(boolean in_aging) {
    this.in_aging = in_aging;
  }

  public boolean isUpdateRow() {
    return updateRow;
  }

  public void setUpdateRow(boolean updateRow) {
    this.updateRow = updateRow;
  }

  public double getInvoice_amount() {
    return invoice_amount;
  }

  public void setInvoice_amount(double invoice_amount) {
    this.invoice_amount = invoice_amount;
  }

  public double getInvoice_outstanding() {
    return invoice_outstanding;
  }

  public void setInvoice_outstanding(double invoice_outstanding) {
    this.invoice_outstanding = invoice_outstanding;
  }

  public double getInvoice_outstanding_with_interest() {
    return invoice_outstanding_with_interest;
  }

  public void setInvoice_outstanding_with_interest(double invoice_outstanding_with_interest) {
    this.invoice_outstanding_with_interest = invoice_outstanding_with_interest;
  }

  public double getGst() {
    return gst;
  }

  public void setGst(double gst) {
    this.gst = gst;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public double getTax_amount() {
    return tax_amount;
  }

  public void setTax_amount(double tax_amount) {
    this.tax_amount = tax_amount;
  }

  public double getAmount_dc() {
    return amount_dc;
  }

  public void setAmount_dc(double amount_dc) {
    this.amount_dc = amount_dc;
  }

  public double getKv_amount() {
    return kv_amount;
  }

  public void setKv_amount(double kv_amount) {
    this.kv_amount = kv_amount;
  }

  public double getKv_amount_dc() {
    return kv_amount_dc;
  }

  public void setKv_amount_dc(double kv_amount_dc) {
    this.kv_amount_dc = kv_amount_dc;
  }

  
  
  
  

}
