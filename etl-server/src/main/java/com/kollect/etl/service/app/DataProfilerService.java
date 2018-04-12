package com.kollect.etl.service.app;

import com.kollect.etl.entity.app.DataProfiler;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class DataProfilerService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

    @Autowired
    public DataProfilerService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

    public Object saveDataProfiler(Integer id, String name, String base_path, String prefix,
                                   String cloneAs, boolean cloneBeforeUnique, boolean cloneFile,
                                   String uniqueKeyFields, String uniqueKeyIndex,
                                   boolean generateHash, String regex, String replacement,
                                   Integer expectedLength){
        DataProfiler newProfiler = new DataProfiler(name, base_path, prefix, cloneAs, cloneBeforeUnique, cloneFile, uniqueKeyFields,
                uniqueKeyIndex, generateHash, regex, replacement, expectedLength);

        boolean insertFlag = false;
        if (id != null)
            newProfiler.setId(id);
        int updateCount = this.rwProvider.updateQuery(dataSource, "updateDataProfiler", newProfiler);
        if (updateCount == 0) {
            this.rwProvider.insertQuery(dataSource, "insertDataProfiler", newProfiler);
            insertFlag = true;
        }
        if (insertFlag)
            return "redirect:/dataprofiler";
        return "redirect:/dataprofiler?id=" + id;
    }

    public Object getDataProfiler(Integer id, Model model){
        model.addAttribute("DProList", this.rwProvider.executeQuery(dataSource, "viewDataProfiler", null));
        List<Object> dProfiler = this.rwProvider.executeQuery(dataSource, "getDataProfilerById", id);
        if (dProfiler.size()>0){
            model.addAttribute("DProEditList", dProfiler.get(0));
        }
        else
            model.addAttribute("DProEditList", null);
        return "dataProfiler";
    }

}
