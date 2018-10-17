package com.kollect.etl.service.pelita;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UpdateInvoiceNumber {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private boolean lock;
    private BatchHistoryService batchHistoryService;

    @Autowired
    public UpdateInvoiceNumber(IReadWriteServiceProvider rwProvider,
                                    @Value("#{'${app.datasource_kv_uat}'.split(',')}") List<String> dataSource, BatchHistoryService batchHistoryService) {
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int execute(Integer batch_id, String getQuery, String updateQuery) {
        int numberOfRows = -1;
        long timeTaken = 0;
        String status;
        String invoiceNo;
        Integer id;
        String updatedInvoiceNo = "";
        for (String src:dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                List<HashMap<String, Object>> invoiceNoList = rwProvider.executeQuery(
                        src, getQuery, null);
                HashMap<String, Object> args = new HashMap<>();

                for (HashMap obj : invoiceNoList){
                    invoiceNo = obj.get("invoice_no").toString();
                    id = Integer.valueOf(obj.get("id").toString());
                    if (invoiceNo.length() == 5)
                        updatedInvoiceNo = invoiceNo + "/" + obj.get("month").toString() + "/" +
                                obj.get("year").toString();
                    else if (invoiceNo.length() == 4)
                        updatedInvoiceNo = "0" + invoiceNo + "/" + obj.get("month").toString() + "/" +
                                obj.get("year").toString();
                    else if (invoiceNo.length() == 3)
                        updatedInvoiceNo = "00" + invoiceNo + "/" + obj.get("month").toString() + "/" +
                                obj.get("year").toString();
                    else if (invoiceNo.length() == 2)
                        updatedInvoiceNo = "000" + invoiceNo + "/" + obj.get("month").toString() + "/" +
                                obj.get("year").toString();
                    else if (invoiceNo.length() == 1)
                        updatedInvoiceNo = "0000" + invoiceNo + "/" + obj.get("month").toString() + "/" +
                                obj.get("year").toString();

                    args.put("id", id);
                    args.put("updated_invoice_no", updatedInvoiceNo);
                    rwProvider.updateQuery(src, updateQuery, args);
                    numberOfRows += 1;
                }
                lock = false;
                long endTime = System.nanoTime();
                timeTaken = (endTime - startTime) / 1000000;
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
