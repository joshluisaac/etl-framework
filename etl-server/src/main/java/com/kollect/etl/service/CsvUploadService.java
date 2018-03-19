package com.kollect.etl.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kollect.etl.util.FileUtils;

@Service
public class CsvUploadService {
    private static final String UPLOAD_DIR = "./etl-server/uploads/";
    public int arrSize, MapSize, displaySizeLimit = 101;
    private List<String> ListCSV;
    private List<Map<String, String>> listMap;

    private List<String> readCsv(String paths) throws IOException {

        return new FileUtils().readFile(new File(paths));
    }

    /**
     * Split List
     * Mapping into Thymeleaf
     */
    public void RegexSplitter(int i) {
        String[] array = ListCSV.get(i).split("\\|");
        Map<String, String> map = new HashMap<>();
        arrSize = array.length;
        for (int k = 0; k < arrSize; k++) {
            map.put("map" + k, array[k]);

        }
        listMap.add(map);
    }

    public List<Map<String, String>> buildListOfMap(MultipartFile file) throws IOException {
        String uploadDirFinale = new File(UPLOAD_DIR).getAbsolutePath();
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDirFinale + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
        listMap =new ArrayList<>();
        ListCSV = readCsv(path.toString());
        MapSize = ListCSV.size();
        if (MapSize < displaySizeLimit) {
            for (int i = 0; i < MapSize; i++) {
                RegexSplitter(i);
            }
        } else {
            for (int i = 0; i < displaySizeLimit; i++) {
                RegexSplitter(i);
            }
        }

        return listMap;
    }
}