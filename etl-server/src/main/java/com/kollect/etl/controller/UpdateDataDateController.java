package com.kollect.etl.controller;

import com.kollect.etl.service.UpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateDataDateController {
    @Autowired
    UpdateDataDateService updateDataDateService;

    @PostMapping("/updatedatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id){
        return this.updateDataDateService.runupdateDataDate(batch_id);
    }

}
