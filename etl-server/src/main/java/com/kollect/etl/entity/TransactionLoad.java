package com.kollect.etl.entity;

public class TransactionLoad {
  
  int id;
  int accountId;
  String accountNo;
  boolean isCommercial, invoice, creditNote;
  
  
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
  
  @Override
  public String toString() {
    return "TransactionLoad [id=" + id + ", accountId=" + accountId + ", accountNo=" + accountNo + ", isCommercial="
        + isCommercial + ", invoice=" + invoice + ", creditNote=" + creditNote + "]";
  }
  
  
  
  

}
