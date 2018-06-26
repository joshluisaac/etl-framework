package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.LogErrorVisualizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class LogErrorVisualizerController {
    @Autowired
    private LogErrorVisualizerService logErrorVisualizerService;

    @GetMapping("/logerrorvisualizer")
    public Object viewLogFile(){
        return "logErrorVisualizer";
    }

    @PostMapping("/logerrorvisualizer")
    public Object getLogFile(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        /* Check if file is too large, redirect to error message if true. */
        if (file.getSize() > 1000000){
            model.addAttribute("size", "File size is too large, please upload only up to 1MB.");
        }
        if (file.isEmpty())
            model.addAttribute("content", "File is empty, please re-upload file.");
        if (file.getSize() < 1000001 && !file.isEmpty()){
            this.logErrorVisualizerService.readLogFile(model, file);
        }
        System.out.println("This is the size..."+file.getSize());
        return "logErrorVisualizer";
    }
}
