package com.kollect.etl.controller;

import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataProfiler {
    private IReadWriteServiceProvider rwProvider;

    @Autowired
    public DataProfiler(IReadWriteServiceProvider rwProvider) {
        this.rwProvider = rwProvider;
    }

    @GetMapping("/filechecker")
    public Object getFileChecker(){
        return "dataProfiler";
    }

    @PostMapping("/savefilechecker")
    public Object saveFileChecker(@RequestParam String name, @RequestParam String base_path){
        return "dataProfiler";
    }
}
