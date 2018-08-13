package main.java.controller;

import com.kollect.etl.util.CsvReadWriteUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private final CsvReadWriteUtils csvReadWriteUtils = new CsvReadWriteUtils(new com.kollect.etl.util.Utils());

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("resultList",csvReadWriteUtils.readCsvToListMap("emailConfig/extractorEmailLog.csv"));
        return "index";
    }
}
