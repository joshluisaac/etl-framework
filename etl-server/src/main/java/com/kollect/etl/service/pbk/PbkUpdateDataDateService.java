package com.kollect.etl.service.pbk;

import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PbkUpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    @Autowired
    public PbkUpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                    @Value("#{'${app.datasource_all}'.split(',')}") List<String> dataSource, BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int runupdateDataDate(Integer batch_id){
        int numberOfRows = 1;
        for (String src : dataSource){
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                this.rwProvider.updateQuery(src, "updateDataDate", null);
                lock = false;
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime ) / 1000000;
                this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src);
            }
        }
        return numberOfRows;
    }
}
