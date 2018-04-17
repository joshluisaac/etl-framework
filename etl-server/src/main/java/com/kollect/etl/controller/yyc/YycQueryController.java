package com.kollect.etl.controller.yyc;

import com.kollect.etl.service.yyc.YycQuerySequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class YycQueryController {
    @Autowired
    private YycQuerySequenceService yycQuerySequenceService;

    @ResponseBody
    @PostMapping("/runyycsequence")
    public Object runYycSequence(@RequestParam Integer batch_id){
        return this.yycQuerySequenceService.runYycSequenceQuery(batch_id);
    }
}
