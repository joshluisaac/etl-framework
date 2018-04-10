package com.kollect.etl.service;


import com.kollect.etl.dataaccess.PelitaAgeInvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PelitaAgeInvoiceService {
    @Autowired
    private PelitaAgeInvoiceDao PelitaAgeInvoiceDao;
    private boolean lock;
    @Autowired
    private BatchHistoryService batchHistoryService;


    public List<Object> getAgeInvoiceById(Object object) {
        return this.PelitaAgeInvoiceDao.getAgeInvoiceById(object);
        // TODO Auto-generated method stub

    }

    public int updateAgeInvoice(Object object) {
        return this.PelitaAgeInvoiceDao.updateAgeInvoice(object);
    }

    public int combinedPelitaAgeInvoiceService(@RequestParam(required = false) Integer tenant_id,@RequestParam Integer batch_id) {
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> ageInvoiceList = this.getAgeInvoiceById(tenant_id);
            int numberOfRecords = ageInvoiceList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) ageInvoiceList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("in_aging", map.get("in_aging"));
                args.put("id", map.get("id"));
                this.updateAgeInvoice(args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);

        }
        System.out.println("AgeInvoice - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }
}
