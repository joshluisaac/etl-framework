package com.kollect.etl.controller;


import com.kollect.etl.service.UpdateVendorInvoicesGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateVendorInvoicesGroupController {

    @Autowired
    private UpdateVendorInvoicesGroupService service;

    @PostMapping(value ="/loadvendorinvoicesgroup1")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup1() {
        service.UpdateVendorInvoicesGroup1(null);
        return "loadvendorinvoicesgroup1";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup2")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup2() {
        service.UpdateVendorInvoicesGroup2(null);
        return "loadvendorinvoicesgroup2";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup3")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup3() {
        service.UpdateVendorInvoicesGroup3(null);
        return "loadvendorinvoicesgroup3";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup4")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup4() {
        service.UpdateVendorInvoicesGroup4(null);
        return "loadvendorinvoicesgroup4";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup5")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup5() {
        service.UpdateVendorInvoicesGroup5(null);
        return "loadvendorinvoicesgroup5";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup6")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup6() {
        service.UpdateVendorInvoicesGroup6(null);
        return "loadvendorinvoicesgroup6";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup7")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup7() {
        service.UpdateVendorInvoicesGroup7(null);
        return "loadvendorinvoicesgroup7";
    }

    @PostMapping(value ="/loadvendorinvoicesgroup8")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup8() {
        service.UpdateVendorInvoicesGroup8(null);
        return "loadvendorinvoicesgroup8";
    }
    @PostMapping(value ="/loadvendorinvoicesgroup9")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup9() {
        service.UpdateVendorInvoicesGroup9(null);
        return "loadvendorinvoicesgroup9";
    }
    @PostMapping(value ="/loadvendorinvoicesgroup10")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup10() {
        service.UpdateVendorInvoicesGroup10(null);
        return "loadvendorinvoicesgroup10";
    }
    @PostMapping(value ="/loadvendorinvoicesgroup11")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup11() {
        service.UpdateVendorInvoicesGroup11(null);
        return "loadvendorinvoicesgroup11";
    }
    @PostMapping(value ="/loadvendorinvoicesgroup12")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup12() {
        service.UpdateVendorInvoicesGroup12(null);
        return "loadvendorinvoicesgroup12";
    }

}
