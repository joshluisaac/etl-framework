package com.kollect.etl.service.app;

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
    public int arrSize, MapSize;
    private List<String> ListCSV;
    private List<Map<String, String>> listMap;

    private List<String> readCsv(String paths) throws IOException {
        List<String> list;
        try (BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(paths))))) {
            String line;
            list = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                if (list.size() <= 100) {
                    list.add(line);
                } else {
                    break;
                }
            }

        }
        return list;
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
        listMap = new ArrayList<>();
        ListCSV = readCsv(path.toString());
        MapSize = ListCSV.size();
        for (int i = 0; i < MapSize; i++) {
            RegexSplitter(i);
        }

        return listMap;
    }
}