package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.DataProfilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataProfilerController {
    private DataProfilerService dProService;

    @Autowired
    public DataProfilerController(DataProfilerService dProService) {
        this.dProService = dProService;
    }

    @GetMapping("/dataprofiler")
    public Object getDataProfiler(@RequestParam(required = false) Integer id, Model model){
        return this.dProService.getDataProfiler(id, model);
    }

    @PostMapping("/savedataprofiler")
    public Object saveDataProfiler(@RequestParam (required = false) Integer id,@RequestParam String name, @RequestParam String base_path, @RequestParam String prefix,
                                  @RequestParam String cloneAs, @RequestParam (required = false) boolean cloneBeforeUnique,
                                   @RequestParam (required = false) boolean cloneFile, @RequestParam String uniqueKeyFields,
                                   @RequestParam String uniqueKeyIndex, @RequestParam (required = false) boolean generateHash,
                                   @RequestParam String regex, @RequestParam String replacement, @RequestParam Integer expectedLength){
        return this.dProService.saveDataProfiler(id, name, base_path, prefix, cloneAs, cloneBeforeUnique, cloneFile, uniqueKeyFields,
                uniqueKeyIndex, generateHash, regex, replacement, expectedLength);
    }

    @PostMapping("/rundataprofiler")
    public Object runDataProfiler(){
        return null;
    }
}
