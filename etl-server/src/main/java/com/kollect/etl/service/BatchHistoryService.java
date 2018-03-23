package com.kollect.etl.service;

import com.kollect.etl.dataaccess.BatchHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchHistoryService {
    @Autowired
    private BatchHistoryDao batchHistoryDao;

    public int insertBatchHistory(Object object){
        return this.batchHistoryDao.insertBatchHistory(object);
    }

    public List<Object> viewBatchHistory(Object object){
        return this.batchHistoryDao.viewBatchHistory(object);
    }
}
