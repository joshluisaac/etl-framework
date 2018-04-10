package com.kollect.etl.service;

import com.kollect.etl.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BatchService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

    @Autowired
    public BatchService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

    public Object getBatch(@RequestParam(required = false) Integer id, Model model){
        model.addAttribute("batchList", this.rwProvider.executeQuery(dataSource, "viewBatch", null));
        List<Object> batches = this.rwProvider.executeQuery(dataSource, "getBatchById", id);
        if (batches.size() > 0)
            model.addAttribute("batchEditList", batches.get(0));
        else
            model.addAttribute("batchEditList", null);

        return "addBatch";
    }

    public Object addUpdateBatch(@RequestParam(required = false) Integer id,@RequestParam String code, @RequestParam String name, @RequestParam String description, @RequestParam (required = false)
            boolean disable){
        Batch newBatch = new Batch(code, name, description, disable);
        boolean insertFlag = false;
        if (id != null)
            newBatch.setId(id);
        int updateCount = this.rwProvider.updateQuery(dataSource, "updateBatch" , newBatch);
        if (updateCount == 0){
            this.rwProvider.insertQuery(dataSource, "insertBatch", newBatch);
            insertFlag = true;
        }
        if (insertFlag)
            return "redirect:/addbatch";
        return "redirect:/addbatch?id=" + id;
    }
}
