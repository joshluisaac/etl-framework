package com.kollect.etl.controller;

import com.kollect.etl.service.DataProfilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataProfilerController {
    private DataProfilerService dProService;

    @Autowired
    public DataProfilerController(DataProfilerService dProService) {
        this.dProService = dProService;
    }

    @GetMapping("/filechecker")
    public Object getFileChecker(){
        return "dataProfilerController";
    }

    @PostMapping("/savefilechecker")
    public Object saveFileChecker(@RequestParam String name, @RequestParam String base_path, @RequestParam String prefix,
                                  @RequestParam String cloneAs, @RequestParam boolean cloneBeforeUnique, @RequestParam boolean cloneFile,
                                  @RequestParam String uniqueKeyFields, @RequestParam String uniqueKeyIndex,
                                  @RequestParam boolean generateHash, @RequestParam String regex, @RequestParam String replacement,
                                  @RequestParam Integer expectedLength){
        this.dProService.saveDataProfiler(name, base_path, prefix, cloneAs, cloneBeforeUnique, cloneFile, uniqueKeyFields,
                uniqueKeyIndex, generateHash, regex, replacement, expectedLength);
        return "dataProfilerController";
    }
}
