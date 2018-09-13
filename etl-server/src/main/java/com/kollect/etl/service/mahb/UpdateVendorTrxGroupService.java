package com.kollect.etl.service.mahb;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateVendorTrxGroupService {
    @Autowired
    private IReadWriteServiceProvider iReadWriteServiceProvider;
    @Autowired
    private BatchHistoryService batchHistoryService;
    
    private Object execute(Integer batch_id, String source, String query1, String query2, Object args){
        Long startTime = System.nanoTime();
        Integer rowCount1 = iReadWriteServiceProvider.updateQuery(source, query1, args);
        Integer rowCount2 = iReadWriteServiceProvider.updateQuery(source, query2, args);
        Long endTime = System.nanoTime();
        batchHistoryService.runBatchHistory(batch_id, rowCount1+rowCount2, endTime-startTime/1000000,
                source, "Success");
        return rowCount1+rowCount2;
    }

    public Object updateVendorTrxGroup1(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup1",
                "mergeVendorTrxForInvoiceCasesGroup1",
                null);
    }

    public Object updateVendorTrxGroup2(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup2",
                "mergeVendorTrxForInvoiceCasesGroup2",
                null);
    }

    public Object updateVendorTrxGroup3(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup3",
                "mergeVendorTrxForInvoiceCasesGroup3",
                null);
    }

    public Object updateVendorTrxGroup4(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup4",
                "mergeVendorTrxForInvoiceCasesGroup4",
                null);
    }

    public Object updateVendorTrxGroup5(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup5",
                "mergeVendorTrxForInvoiceCasesGroup5",
                null);
    }

    public Object updateVendorTrxGroup6(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup6",
                "mergeVendorTrxForInvoiceCasesGroup6",
                null);
    }
    public Object updateVendorTrxGroup7(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup7",
                "mergeVendorTrxForInvoiceCasesGroup7",
                null);
    }

    public Object updateVendorTrxGroup8(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup8",
                "mergeVendorTrxForInvoiceCasesGroup8",
                null);
    }

    public Object updateVendorTrxGroup9(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup9",
                "mergeVendorTrxForInvoiceCasesGroup9",
                null);
    }

    public Object updateVendorTrxGroup10(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup10",
                "mergeVendorTrxForInvoiceCasesGroup10",
                null);
    }

    public Object updateVendorTrxGroup11(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup11",
                "mergeVendorTrxForInvoiceCasesGroup11",
                null);
    }

    public Object updateVendorTrxGroup12(Integer batch_id) {
        return execute(batch_id, "MAHB_Prod4", "mergeVendorTrxForPaymentCasesGroup12",
                "mergeVendorTrxForInvoiceCasesGroup12",
                null);
    }
    
}
