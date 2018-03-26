package com.kollect.etl.service;

import com.kollect.etl.dataaccess.BatchDao;
import com.kollect.etl.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BatchService {
    @Autowired
    private BatchDao batchDao;

    public int insertBatch(Object object){
        return this.batchDao.insertBatch(object);
    }

    public List<Object> viewBatch(Object object){
        return this.batchDao.viewBatch(object);
    }

    public List<Object> getBatchById(Object object){
        return this.batchDao.getBatchById(object);
    }

    public int updateBatch(Object object){
        return this.batchDao.updateBatch(object);
    }

    public Object getBatch(@RequestParam(required = false) Integer id, Model model){
        model.addAttribute("batchList", this.viewBatch(null));
        List<Object> batches = this.getBatchById(id);
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
        int updateCount = this.updateBatch(newBatch);
        if (updateCount == 0){
            this.insertBatch(newBatch);
            insertFlag = true;
        }
        if (insertFlag)
            return "redirect:/addbatch";
        return "redirect:/addbatch?id=" + id;
    }
}
