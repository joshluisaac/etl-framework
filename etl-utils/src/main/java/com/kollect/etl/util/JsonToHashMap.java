package com.kollect.etl.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reads in json file using path using gson and returns a map.
 *
 * @author hashim
 */

public class JsonToHashMap {
    private final JsonUtils jsonUtils;
    private final Utils util;

    public JsonToHashMap(JsonUtils jsonUtils,
                         Utils util){
        this.jsonUtils=jsonUtils;
        this.util=util;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> toHashMapFromJson(String path){
        return jsonUtils.fromJson(this.util.listToBuffer(util.readFile(new File(path))).toString(),HashMap.class);
    }

    public void fromHashMapToJson(Map<String, String> m, String path){
        util.writeTextFile(path, jsonUtils.toJson(m), false);
    }
}
