package com.kollect.etl.service.app;

import com.kollect.etl.component.ComponentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvUploadService {
    private static final String UPLOAD_DIR = "./etl-server/uploads/";
    private List<String> listCSV;
    public int arrSize;
    private List<Map<String, String>> listMap;
    @Autowired
    private ComponentProvider componentProvider;

    /**
     * Split List
     * Mapping into Thymeleaf
     */
    private void RegexSplitter(int i) {
        String[] array = listCSV.get(i).split("\\|");
        Map<String, String> map = new HashMap<>();
        arrSize = array.length;
        for (int k = 0; k < arrSize; k++) {
            map.put("map" + k, array[k]);

        }
        listMap.add(map);
    }

    public List<Map<String, String>> buildListOfMap(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, bytes);
        listMap = new ArrayList<>();
        listCSV = componentProvider.readFile(path.toString());
        int MapSize = listCSV.size();
        for (int i = 0; i < MapSize; i++) {
            RegexSplitter(i);
        }
        return listMap;
    }
}