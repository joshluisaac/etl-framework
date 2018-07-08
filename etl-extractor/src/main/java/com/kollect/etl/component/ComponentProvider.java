package com.kollect.etl.component;

import com.kollect.etl.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ComponentProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ComponentProvider.class);

    /**
     * A sleep method to let the application rest for given seconds
     */
    public void taskSleep() {
        try {
            LOG.info("Rejuvenating for ten seconds...");
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            LOG.error("An error occurred during thread sleep." + e);
        }
    }

    public List<String> readFile(String paths) throws IOException {
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

}
