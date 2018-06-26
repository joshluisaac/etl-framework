package com.kollect.etl.service.app;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class LogErrorVisualizerService {
    private static final String UPLOAD_DIR = "./etl-server/uploads/";

    public void readLogFile(Model model, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, bytes);
        Scanner scanner = new Scanner(new File(path.toString()));
        ArrayList<String> initialList = new ArrayList<>();
        ArrayList<String> modifiedListPart1 = new ArrayList<>();
        ArrayList<String> modifiedListPart2 = new ArrayList<>();
        /* read log line by line */
        while (scanner.hasNextLine()) {
            initialList.add(scanner.nextLine());
        }
        /*Get logs that start with "Caused by:" */
        for (String aList : initialList) {
            if (aList.startsWith("Caused by:")) {
                String[] parts = aList.split(":");
                /* we only need part 2 and 3 */
                modifiedListPart1.add(parts[1]);
                modifiedListPart2.add(parts[2]);
            }
        }
        model.addAttribute("part1", modifiedListPart1);
        model.addAttribute("part2", modifiedListPart2);
        scanner.close();
        System.out.println(path.toString());
    }
}
