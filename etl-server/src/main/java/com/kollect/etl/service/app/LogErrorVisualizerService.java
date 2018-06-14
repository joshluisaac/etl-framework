package com.kollect.etl.service.app;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogErrorVisualizerService {
    public void readLogFile(Model model) {
        try {
            File file = new File("./etl-server/uploads/stacktrace.log");
            FileInputStream fStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
            String strLine;
            List<String> causedByList = new ArrayList<>();
            List<String> reasonList = new ArrayList<>();
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                /*Get logs that start with "Caused by:" */
                if (strLine.startsWith("Caused by:")) {
                    String parts[] = strLine.split(":", 3);
                    /* we only need part 2 and 3 */
                    String part2 = parts[1];
                    causedByList.add(part2);
                    String part3 = parts[2];
                    reasonList.add(part3);
                    model.addAttribute("part2", causedByList);
                    model.addAttribute("part3", reasonList);
                }
            }
            fStream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
