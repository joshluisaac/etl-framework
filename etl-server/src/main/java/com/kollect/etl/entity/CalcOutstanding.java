package com.kollect.etl.entity;

public class CalcOutstanding {
  private Integer id, tenant_id;
  private float total_transactions, invoice_plus_gst, invoice_outstanding;
  boolean initial_debit;

  public CalcOutstanding(Integer id, Integer tenant_id,float total_transactions, float invoice_plus_gst, float invoice_outstanding, boolean initial_debit) {
    super();
    this.id = id;
    this.tenant_id=tenant_id;
    this.total_transactions = total_transactions;
    this.invoice_plus_gst = invoice_plus_gst;
    this.invoice_outstanding = invoice_outstanding;
  }

}
