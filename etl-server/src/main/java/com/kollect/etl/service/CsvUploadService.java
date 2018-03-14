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

	private static final String UPLOAD_DIR = "./uploads/";
	public int arrSize;

	private List<String> readCsv(String paths) throws IOException {

		return new FileUtils().readFile(new File(paths));
	}

	public List<Map<String, String>> buildListOfMap(MultipartFile file) throws IOException {
        String uploadDirFinale = new File(UPLOAD_DIR).getAbsolutePath();
		byte[] bytes = file.getBytes();
		Path path = Paths.get(uploadDirFinale + "/" + file.getOriginalFilename());
		Files.write(path, bytes);
		List<Map<String, String>> listMap = new ArrayList<>();
		List<String> ListCSV = readCsv(path.toString());
		for (int i = 0; i < ListCSV.size(); i++) {
			String[] array = ListCSV.get(i).split("\\|");
			arrSize = array.length;
			Map<String, String> map = new HashMap<>();
			for (int k = 0; k < arrSize; k++) {
				map.put("map" + k, array[k]);
			}
			listMap.add(map);

		}
		return listMap;
	}
}