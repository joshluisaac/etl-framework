package com.kollect.etl.service;

import com.kollect.etl.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

  String pathName = "/home/joshua/martian/kvworkspace/dataconnector-MAHB/sourceFiles/20171118003002/CUSTLIST_20171118003004.csv";

  private List<String> readCsv() throws IOException {
    return new FileUtils().readFile(new File(pathName));
  }

  public List<Map<String, String>> buildListOfMap() throws IOException {
    List<Map<String, String>> listMap = new ArrayList<>();
    List<String> csvList = readCsv();

    for (int i = 0; i < csvList.size(); i++) {
      String[] arr = csvList.get(i).split("\\|");
      Map<String, String> map = new HashMap<>();
      map.put("name", arr[0]);
      map.put("name1", arr[1]);
      map.put("sname", arr[2]);
      map.put("name2", arr[3]);
      listMap.add(map);
    }
    return listMap;
  }
}
