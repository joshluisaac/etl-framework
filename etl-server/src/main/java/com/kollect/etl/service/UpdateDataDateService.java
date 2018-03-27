package com.kollect.etl.service;

import com.kollect.etl.dataaccess.UpdateDataDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UpdateDataDateService {
    @Autowired
    private UpdateDataDateDao updateDataDateDao;
    private boolean lock;
    @Autowired
    private BatchHistoryService batchHistoryService;

    public int updateDataDate() {
        return this.updateDataDateDao.updateDataDate();
    }

    public int runupdateDataDate(@RequestParam Integer batch_id){
        int numberOfRows = 1;
        long startTime = System.nanoTime();
        if (!lock) {
            lock = true;
            this.updateDataDate();
        }
        lock = false;
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime)/1000000;
        this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);

        return numberOfRows;
    }
}
