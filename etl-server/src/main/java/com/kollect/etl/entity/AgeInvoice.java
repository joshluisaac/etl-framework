package com.kollect.etl.entity;

public class AgeInvoice {
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String invoice_due_date;

    public String getInvoice_due_date() {
        return invoice_due_date;
    }

    private Integer tenant_id;

    public Integer getTenant_id() {
        return tenant_id;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    private String dpd;
}
