package com.kollect.etl.controller;

import com.kollect.etl.service.TransactionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TransactionUpdateController {
    @Autowired
    TransactionUpdateService service;

    @PostMapping(value ="/loadinvoices_ab")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_AB() {
        List<Object> transList =  service.getTransactionAB(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_rg")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_RG() {
        List<Object> transList =  service.getTransactionRG(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yy")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YY() {
        List<Object> transList =  service.getTransactionYY(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_gi")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_GI() {
        List<Object> transList =  service.getTransactionGI(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_ri")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_RI() {
        List<Object> transList =  service.getTransactionRI(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_rm")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_RM() {
        List<Object> transList =  service.getTransactionRM(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_rv")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_RV() {
        List<Object> transList =  service.getTransactionRV(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_ry")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_RY() {
        List<Object> transList =  service.getTransactionRY(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yc")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YC() {
        List<Object> transList =  service.getTransactionYC(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yd")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YD() {
        List<Object> transList =  service.getTransactionYD(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yh")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YH() {
        List<Object> transList =  service.getTransactionYH(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yi")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YI() {
        List<Object> transList =  service.getTransactionYI(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yj")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YJ() {
        List<Object> transList =  service.getTransactionYJ(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yl")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YL() {
        List<Object> transList =  service.getTransactionYL(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yn")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YN() {
        List<Object> transList =  service.getTransactionYN(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yo")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YO() {
        List<Object> transList =  service.getTransactionYO(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yp")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YP() {
        List<Object> transList =  service.getTransactionYP(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yq")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YQ() {
        List<Object> transList =  service.getTransactionYQ(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yr")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YR() {
        List<Object> transList =  service.getTransactionYR(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_ys")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YS() {
        List<Object> transList =  service.getTransactionYS(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yt")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YT() {
        List<Object> transList =  service.getTransactionYT(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yu")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YU() {
        List<Object> transList =  service.getTransactionYU(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yv")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YV() {
        List<Object> transList =  service.getTransactionYV(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yw")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YW() {
        List<Object> transList =  service.getTransactionYW(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yx")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YX() {
        List<Object> transList =  service.getTransactionYX(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yk")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YK() {
        List<Object> transList =  service.getTransactionYK(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_y1")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_Y1() {
        List<Object> transList =  service.getTransactionY1(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_ye")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YE() {
        List<Object> transList =  service.getTransactionYE(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_ym")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YM() {
        List<Object> transList =  service.getTransactionYM(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_yf")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_YF() {
        List<Object> transList =  service.getTransactionYF(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_zz")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoices_ZZ() {
        List<Object> transList =  service.getTransactionZZ(null);
        return this.service.processTransactionList(transList);
    }

    @PostMapping(value ="/loadinvoices_others")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateInvoicesOthers() {
        List<Object> transList =  service.getTransactionOthers(null);
        return this.service.processTransactionList(transList);
    }
}
