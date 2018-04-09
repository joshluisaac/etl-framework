package com.kollect.etl.controller;

import com.kollect.etl.util.FileAppender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileChecker {

    @GetMapping("/filechecker")
    public Object getFileChecker(){
        return "fileChecker";
    }

    @PostMapping("/filechecker")
    public Object runFileChecker(@RequestParam String path, @RequestParam String prefix) throws Exception{
        String [] args = {path};
        FileAppender.main(args);
        return "fileChecker";
    }
}
