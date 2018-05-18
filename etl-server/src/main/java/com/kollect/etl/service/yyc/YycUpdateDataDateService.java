package com.kollect.etl.service.yyc;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YycUpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    public YycUpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                    @Value("#{'${app.datasource_all2}'.split(',')}") List<String> dataSource,
                                    BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int runUpdateDataDate(Integer batch_id){
        int numberOfRows = 1;
        for (String src : dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                this.rwProvider.updateQuery(src, "yycUpdateDataDate", null);
                lock = false;
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime) / 1000000;
                this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src);
            }
        }
        return numberOfRows;
    }
}
