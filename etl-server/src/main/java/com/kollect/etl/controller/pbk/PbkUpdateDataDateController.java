package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.pbk.PbkUpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PbkUpdateDataDateController {
    @Autowired
    PbkUpdateDataDateService pbkUpdateDataDateService;

    @PostMapping("/updatedatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id){
        return this.pbkUpdateDataDateService.runupdateDataDate(batch_id);
    }

}
