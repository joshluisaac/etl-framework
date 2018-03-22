package com.kollect.etl.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.LumpSumPaymentDao;

@Service
public class LumpSumPaymentService {

    @Autowired
    private LumpSumPaymentDao LumpSumPaymentDao;
    private boolean lock;

    public List<Object> getSumAmount(Object object) {
        return this.LumpSumPaymentDao.getSumAmount(object);
    }

    public int updateGetSumAmount(Object object) {
        return this.LumpSumPaymentDao.updateGetSumAmount(object);
    }

    public int insertGetSumAmount(Object object) {
        return this.LumpSumPaymentDao.insertGetSumAmount(object);
    }

    public int deleteNetLumpSum(Object object) {

        try{
            this.LumpSumPaymentDao.deleteNetLumpSum(object);
        }

        catch(SQLException e){
            System.out.println(e);
           e.printStackTrace();
        }

        return -1;
    }

    public int combinedLumpSumPaymentService(){
        int numberOfRows = -1;
        if (!lock) {
            lock = true;
            List<Object> selectLumSumPaymentList = this.getSumAmount(null);
            this.deleteNetLumpSum(null);
            int numberOfRecords = selectLumSumPaymentList.size();
            for (int x = 0; x < numberOfRecords; x++) {

                Map<Object, Object> map = (Map<Object, Object>) selectLumSumPaymentList.get(x);
                Map<Object, Object> args = new HashMap<>();
                args.put("account_id", map.get("account_id"));
                args.put("net_lump_sum_amount", map.get("net_lump_sum_amount"));
                int updateCount = this.updateGetSumAmount(args);
                if (updateCount == 0) {
                    this.insertGetSumAmount(args);
                }
            }
            lock = false;
            numberOfRows = numberOfRecords;
        }
        System.out.println("LumpSumPayment - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }
}
