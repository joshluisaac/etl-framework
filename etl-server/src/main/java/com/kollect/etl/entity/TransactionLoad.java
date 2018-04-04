package com.kollect.etl.entity;

public class TransactionLoad {
  
  int id;
  int accountId;
  String accountNo;
  boolean isCommercial, invoice, creditNote;
  
  String line_of_business, invoice_no, invoice_reference;
  int invoice_status_id;
  boolean in_aging;
  
  
  public String getLine_of_business() {
    return line_of_business;
  }
  public void setLine_of_business(String line_of_business) {
    this.line_of_business = line_of_business;
  }
  public String getInvoice_no() {
    return invoice_no;
  }
  public void setInvoice_no(String invoice_no) {
    this.invoice_no = invoice_no;
  }
  public String getInvoice_reference() {
    return invoice_reference;
  }
  public void setInvoice_reference(String invoice_reference) {
    this.invoice_reference = invoice_reference;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getAccountId() {
    return accountId;
  }
  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }
  public String getAccountNo() {
    return accountNo;
  }
  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }
  public boolean isCommercial() {
    return isCommercial;
  }
  public void setCommercial(boolean isCommercial) {
    this.isCommercial = isCommercial;
  }
  public boolean isInvoice() {
    return invoice;
  }
  public void setInvoice(boolean invoice) {
    this.invoice = invoice;
  }
  public boolean isCreditNote() {
    return creditNote;
  }
  public void setCreditNote(boolean creditNote) {
    this.creditNote = creditNote;
  }
  public int getInvoice_status_id() {
    return invoice_status_id;
  }
  public void setInvoice_status_id(int invoice_status_id) {
    this.invoice_status_id = invoice_status_id;
  }
  public boolean isIn_aging() {
    return in_aging;
  }
  public void setIn_aging(boolean in_aging) {
    this.in_aging = in_aging;
  }
  @Override
  public String toString() {
    return "TransactionLoad [id=" + id + ", accountId=" + accountId + ", accountNo=" + accountNo + ", isCommercial="
        + isCommercial + ", invoice=" + invoice + ", creditNote=" + creditNote + ", line_of_business="
        + line_of_business + ", invoice_no=" + invoice_no + ", invoice_reference=" + invoice_reference
        + ", invoice_status_id=" + invoice_status_id + ", in_aging=" + in_aging + "]";
  }
  
  
  
  

}
