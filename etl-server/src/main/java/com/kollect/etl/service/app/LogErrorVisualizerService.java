package com.kollect.etl.service.app;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class LogErrorVisualizerService {
    public void readLogFile(Model model) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("./etl-server/uploads/stacktrace.log"));
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
    }
}
