package com.kollect.etl.service.pbk;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PbkLumpSumPaymentService {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private boolean lock;
    private BatchHistoryService batchHistoryService;
    private static final Logger LOG = LoggerFactory.getLogger(PbkLumpSumPaymentService.class);

    @Autowired
    public PbkLumpSumPaymentService(IReadWriteServiceProvider rwProvider,
                                    @Value("#{'${app.datasource_all}'.split(',')}") List<String> dataSource, BatchHistoryService batchHistoryService) {
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int combinedLumpSumPaymentService(@RequestParam Integer batch_id){
        int numberOfRows = -1;
        long timeTaken = 0;
        String status;
        for (String src:dataSource){
            try {
                if (!lock) {
                    long startTime = System.nanoTime();
                    lock = true;
                    List<Object> selectLumSumPaymentList = this.rwProvider.executeQuery(src, "getPbkSumAmount", null);
                    this.rwProvider.updateQuery(src, "truncatePbkNetLumpSum", null);
                    int numberOfRecords = selectLumSumPaymentList.size();
                    for (Object aSelectLumSumPaymentList : selectLumSumPaymentList) {
                        Map<Object, Object> map = (Map<Object, Object>) aSelectLumSumPaymentList;
                        Map<Object, Object> args = new HashMap<>();
                        args.put("account_id", map.get("account_id"));
                        args.put("net_lump_sum_amount", map.get("net_lump_sum_amount"));
                        int updateCount = this.rwProvider.updateQuery(src, "updatePbkSumAmount", args);
                        if (updateCount == 0) {
                            this.rwProvider.insertQuery(src, "insertPbkSumAmount", args);
                        }
                    }
                    lock = false;
                    numberOfRows = numberOfRecords;
                    long endTime = System.nanoTime();
                    timeTaken = (endTime - startTime) / 1000000;
                }
            }catch (Exception e){
                LOG.error("An error occurred when running the batch." + e);
            }
            if (lock)
                status = "Failed";
            else
                status = "Success";
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows,
                    timeTaken, src, status);
        }
        /* Necessary in case a batch fails bec DB issues, release the lock so it can
         * run next time. */
        lock = false;
        return numberOfRows;
    }
}
