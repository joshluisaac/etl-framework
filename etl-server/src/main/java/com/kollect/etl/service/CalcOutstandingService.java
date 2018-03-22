package com.kollect.etl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.CalcOutstandingDao;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CalcOutstandingService {
    @Autowired
    private CalcOutstandingDao calcOutstandingDao;
    private boolean lock;

    public List<Object> getOutstandingByTenantId(Object object) {
        return this.calcOutstandingDao.getOutstandingByTenantId(object);
        // TODO Auto-generated method stub

    }

    public int updateOutstanding(Object object) {
        return this.calcOutstandingDao.updateOutstanding(object);
    }

    public int combinedCalcOutstanding(@RequestParam(required = false) Integer tenant_id) {
        int numberOfRows = -1;
        if (!lock) {
            lock = true;
            List<Object> outstandingList = this.getOutstandingByTenantId(tenant_id);
            int numberOfRecords = outstandingList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) outstandingList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_plus_gst", map.get("invoice_plus_gst"));
                args.put("total_transactions", map.get("total_transactions"));
                args.put("invoice_id", map.get("invoice_id"));
                this.updateOutstanding(args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
        }
        return numberOfRows;
    }
}
