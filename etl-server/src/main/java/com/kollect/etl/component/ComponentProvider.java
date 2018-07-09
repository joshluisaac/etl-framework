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
     * Will resolve the account as either commercial or non commercial
     *
     * @param accountNo
     * @param pattern
     * @return boolean value of regex result
     */
    public boolean isCommericalResolver(String accountNo, String pattern) {
        return new StringUtils().hasMatch(accountNo, pattern) ? true : false;

    }


    @SuppressWarnings("unchecked")
    public void buildMapArgs(List<Object> list) {
        for (Object obj : list) {
            Map<String, Object> m = (Map<String, Object>) obj;
            Map<String, Object> args = new HashMap<>();
            for (Map.Entry<String, Object> x : m.entrySet()) {
                args.put(x.getKey(), x.getValue());
            }

            ///update statement
        }

    }

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
