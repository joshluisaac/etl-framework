package com.kollect.etl.service;

import com.kollect.etl.dataaccess.BatchHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchHistoryService {
    @Autowired
    private BatchHistoryDao batchHistoryDao;

    public void insertBatchHistory(Object object){
        this.batchHistoryDao.insertBatchHistory(object);
    }

    public List<Object> viewLatestBatchHistory(@RequestParam Integer batch_id, Model model){
        List<Object> LatestBatchHistoryList = this.batchHistoryDao.viewLatestBatchHistory(batch_id);
        model.addAttribute("LatestBatchHistoryList", LatestBatchHistoryList);
        System.out.println("This is the list: "+LatestBatchHistoryList);
        return LatestBatchHistoryList;
    }

    public List<Object> viewBatchHistory(@RequestParam Integer batch_id, Model model){
        List<Object> BatchHistoryList = this.batchHistoryDao.viewBatchHistory(batch_id);
        model.addAttribute("BatchHistoryList", BatchHistoryList);
        return BatchHistoryList;
    }

    public void runBatchHistory(@RequestParam Integer batch_id, int numberOfRows, long timeTaken){
        Map<Object, Object> args = new HashMap<>();
        args.put("batch_id", batch_id);
        args.put("number_of_records_updated", numberOfRows);
        args.put("time_taken", timeTaken);
        this.insertBatchHistory(args);
    }
}
