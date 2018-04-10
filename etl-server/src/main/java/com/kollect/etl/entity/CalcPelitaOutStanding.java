package com.kollect.etl.entity;

public class CalcPelitaOutStanding {

    private int id;
    private double invoice_amount, gst, invoice_outstanding;

    public CalcPelitaOutStanding(int id, double invoice_amount, double gst, double invoice_outstanding) {
        this.id = id;
        this.invoice_amount = invoice_amount;
        this.gst = gst;
        this.invoice_outstanding = invoice_outstanding;
    }


}
