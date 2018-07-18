package com.kollect.etl.service.yyc;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YycQuerySequenceService {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;
    private static final Logger LOG = LoggerFactory.getLogger(YycQuerySequenceService.class);

    @Autowired
    public YycQuerySequenceService(IReadWriteServiceProvider rwProvider,
                                   @Value("#{'${app.datasource_all2}'.split(',')}") List<String> dataSource,
                                   BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int runYycSequenceQuery(Integer batch_id){
        int numberOfRows = 8;
        long timeTaken = 0;
        String status;
        for (String src : dataSource) {
            try {
                if (!lock) {
                    long startTime = System.nanoTime();
                    lock = true;
                    this.rwProvider.executeQuery(src, "getUpdateCustomerSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateAccountSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateInvoiceSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdatePaymentTrxSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateCustomerPicSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateCustomerPhoneNumberSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateCustomerAddressSequence", null);
                    this.rwProvider.executeQuery(src, "getUpdateCustomerEmailSequence", null);
                    lock = false;
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
