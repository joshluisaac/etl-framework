package com.kollect.etl.controller.app;

import com.kollect.etl.service.CsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;


@Controller
public class IndexController {
  
  private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
  @Autowired
  CsvService csvService;

  @GetMapping("/viewCsvJson")
  @ResponseBody
  public Object viewCsvJson() throws IOException {
    return csvService.buildListOfMap();
  }

  @GetMapping("/viewCsv")
  public String getCsv(Model model) throws IOException {

    model.addAttribute("csvList", csvService.buildListOfMap());
    return "csv";
  }

  @RequestMapping("/index")
  public String index(Model model) {
    
    Map<String, Integer> map = new HashMap<>();
    map.put("accessRightsId", 100);
    List<Map<String, Integer>> mapList = new ArrayList<>();
    mapList.add(map);
    model.addAttribute("accessRights", mapList);
    
    return "index";
  }

  @GetMapping("")
  public String redirectToHome(){
    return "redirect://index";
  }
  
  @RequestMapping("test")
  public String test(Model model) {
    
    Map<String, Object> map = new HashMap<>();
    map.put("accessRightsId", 100);
    map.put("currentDate", new Date());
    map.put("name", "Josh Luisaac");
    
    Map<String, Map<String, Object>> userMap = new HashMap<>();
    userMap.put("user", map);
    
    
    List<Map<String, Object>> mapList = new ArrayList<>();
    mapList.add(map);
    model.addAttribute("accessRightList", mapList);
    model.addAttribute("sessionMap", userMap);
    
    return "test";
  }

}
