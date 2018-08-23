package com.kollect.etl.controller.app;

import com.kollect.etl.util.CsvReadWriteUtils;
import com.kollect.etl.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExtractionEmailUpdateController {
    private final CsvReadWriteUtils csvReadWriteUtils = new CsvReadWriteUtils(new com.kollect.etl.util.Utils());
    @Value("${app.extractionEmailLogPath}")
    private String extractionEmailLog;
    private FileUtils fileUtils = new FileUtils();

    @GetMapping("/extractionemailupdate")
    public String extractionEmailReport(Model model) {
        model.addAttribute("resultList",csvReadWriteUtils.readCsvToListMap(
                fileUtils.getFileFromClasspath(extractionEmailLog).toString()));
        return "extractionEmailUpdate";
    }
}
