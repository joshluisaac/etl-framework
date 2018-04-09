package com.kollect.etl.service;

import com.kollect.etl.entity.DataProfiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class DataProfilerService {
    private IReadWriteServiceProvider rwProvider;

    @Autowired
    public DataProfilerService(IReadWriteServiceProvider rwProvider){
        this.rwProvider = rwProvider;
    }

    public void saveDataProfiler(@RequestParam String name, @RequestParam String base_path, @RequestParam String prefix,
                                 @RequestParam String cloneAs, @RequestParam boolean cloneBeforeUnique, @RequestParam boolean cloneFile,
                                 @RequestParam String uniqueKeyFields, @RequestParam String uniqueKeyIndex,
                                 @RequestParam boolean generateHash, @RequestParam String regex, @RequestParam String replacement,
                                 @RequestParam Integer expectedLength){
        DataProfiler newProfiler = new DataProfiler(name, base_path, prefix, cloneAs, cloneBeforeUnique, cloneFile, uniqueKeyFields,
                uniqueKeyIndex, generateHash, regex, replacement, expectedLength);
    }

}
