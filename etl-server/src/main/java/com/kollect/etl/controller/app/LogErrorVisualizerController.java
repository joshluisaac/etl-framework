package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.LogErrorVisualizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;

@Controller
public class LogErrorVisualizerController {
    @Autowired
    private LogErrorVisualizerService logErrorVisualizerService;

    @GetMapping("/logerrorvisualizer")
    public Object viewLogErrorVisualizer(Model model) throws FileNotFoundException {
        this.logErrorVisualizerService.readLogFile(model);
        return "logErrorVisualizer";
    }
}