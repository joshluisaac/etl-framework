package com.kollect.etl.service.app;

import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchHistoryService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

    @Autowired
    public BatchHistoryService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_uat_8}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

    public void viewLatestBatchHistory(Integer batch_id, Model model){
        List<Object> latestBatchHistoryList = this.rwProvider.executeQuery(dataSource, "viewLatestBatchHistory", batch_id);
        model.addAttribute("latestBatchHistoryList", latestBatchHistoryList);
    }

    public void viewBatchHistory(Integer batch_id, Model model){
        List<Object> batchHistoryList = this.rwProvider.executeQuery(dataSource, "viewBatchHistory", batch_id);
        model.addAttribute("batchHistoryList", batchHistoryList);
    }

    public void runBatchHistory(Integer batch_id, int numberOfRows, long timeTaken, String dataFrom){
        Map<Object, Object> args = new HashMap<>();
        args.put("batch_id", batch_id);
        args.put("number_of_records_updated", numberOfRows);
        args.put("time_taken", timeTaken);
        args.put("data_source", dataFrom);
        this.rwProvider.insertQuery(dataSource, "insertBatchHistory", args);
    }

    public void viewAllBatchHistory(Model model){
        List<Object> allBatchHistoryList = this.rwProvider.executeQuery(dataSource, "viewAllBatchHistory", null);
        model.addAttribute("allBatchHistoryList", allBatchHistoryList);
    }

    public List<Object> viewPbkAfterScheduler(){
        return this.rwProvider.executeQuery(dataSource, "viewPbkAfterScheduler", null);
    }

    public List<Object> viewPelitaAfterScheduler(){
        return this.rwProvider.executeQuery(dataSource, "viewPelitaAfterScheduler", null);
    }

    public List<Object> viewYycAfterScheduler(){
        return this.rwProvider.executeQuery(dataSource, "viewYycAfterScheduler", null);
    }
}
