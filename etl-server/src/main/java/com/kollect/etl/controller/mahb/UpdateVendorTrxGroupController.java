package com.kollect.etl.controller.mahb;


import com.kollect.etl.service.mahb.UpdateVendorTrxGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateVendorTrxGroupController {

    @Autowired
    private UpdateVendorTrxGroupService service;

    @PostMapping(value ="/loadvendorinvoicesgroup1")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup1(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup1(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup2")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup2(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup2(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup3")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup3(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup3(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup4")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup4(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup4(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup5")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup5(@RequestParam Integer batch_id) {
        return  service.updateVendorTrxGroup5(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup6")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup6(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup6(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup7")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup7(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup7(batch_id);
    }

    @PostMapping(value ="/loadvendorinvoicesgroup8")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup8(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup8(batch_id);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup9")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup9(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup9(batch_id);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup10")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup10(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup10(batch_id);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup11")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup11(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup11(batch_id);
    }
    @PostMapping(value ="/loadvendorinvoicesgroup12")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object updateVendorTrxGroup12(@RequestParam Integer batch_id) {
        return service.updateVendorTrxGroup12(batch_id);
    }

}
