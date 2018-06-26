package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.LogErrorVisualizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        this.logErrorVisualizerService.readLogFile(model, file);
        return "logErrorVisualizer";
    }
}
