package com.kollect.etl.controller;


import com.kollect.etl.service.UpdateVendorInvoicesGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UpdateVendorInvoicesGroupController {

    @Autowired
    private UpdateVendorInvoicesGroupService service;

    @PostMapping(value ="/loadvendorinvoicesgroup1")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup1() {
        return service.UpdateVendorInvoicesGroup1(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup2")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup2() {
        return service.UpdateVendorInvoicesGroup2(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup3")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup3() {
        return service.UpdateVendorInvoicesGroup3(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup4")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup4() {
        return service.UpdateVendorInvoicesGroup4(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup5")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup5() {
        return  service.UpdateVendorInvoicesGroup5(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup6")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup6() {
        return service.UpdateVendorInvoicesGroup6(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup7")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup7() {
        return service.UpdateVendorInvoicesGroup7(null);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup8")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup8() {
        return service.UpdateVendorInvoicesGroup8(null);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup9")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup9() {
        return service.UpdateVendorInvoicesGroup9(null);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup10")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup10() {
        return service.UpdateVendorInvoicesGroup10(null);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup11")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup11() {
        return service.UpdateVendorInvoicesGroup11(null);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup12")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorInvoicesGroup12() {
        return service.UpdateVendorInvoicesGroup12(null);
    }

}
