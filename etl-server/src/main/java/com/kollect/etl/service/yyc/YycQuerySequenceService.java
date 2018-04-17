package com.kollect.etl.service.yyc;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
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
        for (String src : dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                this.rwProvider.executeQuery(src, "getUpdateCustomerSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateAccountSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateInvoiceSequence", null);
                this.rwProvider.executeQuery(src, "getUpdatePaymentTrnxSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateCustomerPicSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateCustomerPhoneNumberSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateCustomerAddressSequence", null);
                this.rwProvider.executeQuery(src, "getUpdateCustomerEmailSequence", null);
                lock = false;
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime ) / 1000000;
                this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src);
            }
        }
        return numberOfRows;
    }
}
