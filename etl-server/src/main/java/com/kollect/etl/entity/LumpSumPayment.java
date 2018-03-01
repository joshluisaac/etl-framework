package com.kollect.etl.entity;

public class LumpSumPayment {
  private double net_lump_sum_amount;
  private int account_id;

  public LumpSumPayment(double net_lump_sum_amount, int account_id) {
    this.net_lump_sum_amount = net_lump_sum_amount;
    this.account_id = account_id;
  }

}