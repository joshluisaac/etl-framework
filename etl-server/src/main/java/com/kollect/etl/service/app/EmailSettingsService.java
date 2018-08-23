package com.kollect.etl.service.app;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EmailSettingsService {
    private JsonUtils jsonUtils = new JsonUtils();
    private Utils utils = new Utils();
    private JsonToHashMap jsonToHashMap = new JsonToHashMap(jsonUtils, utils);

    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;
    private FileUtils fileUtils = new FileUtils();

	public Object getEmailSettings(){
        return jsonToHashMap.toHashMapFromJson(fileUtils.getFileFromClasspath(
                generalEmailJsonPath).toString());
    }

	public void addUpdateEmailSettings(HashMap<String, String> generalEmailSettings) {
        String jsonOut = jsonUtils.toJson(generalEmailSettings);
        utils.writeTextFile(fileUtils.getFileFromClasspath(
                generalEmailJsonPath).toString(), jsonOut, false);
    }
}
