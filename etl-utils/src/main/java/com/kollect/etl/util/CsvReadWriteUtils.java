package com.kollect.etl.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReadWriteUtils {
    private final Utils util;

    public CsvReadWriteUtils(Utils util){
        this.util=util;
    }

    private String formatToCsvString(List<String> randomSingleList){
        String result = "";
        for (String aRandomList : randomSingleList) {
            String formattedString = aRandomList.concat("|");
            result = result.concat(formattedString);
        }
        return result+"\n";
    }

    public void persistToCsv(List<String> randomSingleList, String path){
        util.writeTextFile(path, formatToCsvString(randomSingleList), true);
    }

    private List<Map<String,String>> splitCsvToListMap(List<String> result){
        List<Map<String, String>> listMap = new ArrayList<>();
        for (String aResult : result) {
            String[] array = aResult.split("\\|");
            Map<String, String> map = new HashMap<>();
            int arrSize = array.length;
            for (int k = 0; k < arrSize; k++) {
                map.put("map" + k, array[k]);
            }
            listMap.add(map);
        }
        return listMap;
    }

    public List<Map<String, String>> readCsvToListMap(String path){
        List<String> result = util.readFile(new File(path));
        return splitCsvToListMap(result);
    }
}
