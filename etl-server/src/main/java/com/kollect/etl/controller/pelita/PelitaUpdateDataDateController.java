package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.pelita.PelitaUpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaUpdateDataDateController {
    @Autowired
    private PelitaUpdateDataDateService pelitaUpdateDataDateService;

    @PostMapping("/pelitaupdatedatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id) {
        return this.pelitaUpdateDataDateService.runupdateDataDate(batch_id);
    }
}
