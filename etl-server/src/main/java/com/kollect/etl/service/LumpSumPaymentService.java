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

    public int numbOfRowsFunction(){
        int numberOfRecords = -1;
        List<Object> selectLumSumPaymentList = this.getSumAmount(null);
        numberOfRecords = selectLumSumPaymentList.size();
        System.out.println("Number of rows: " + numberOfRecords);
        for (int x = 0; x < selectLumSumPaymentList.size(); x++) {

            Map<Object, Object> map = (Map<Object, Object>) selectLumSumPaymentList.get(x);
            Map<Object, Object> args = new HashMap<>();
            args.put("account_id", map.get("account_id"));
            args.put("net_lump_sum_amount", map.get("net_lump_sum_amount"));
            int updateCount = this.updateGetSumAmount(args);
            if (updateCount == 0) {
                this.insertGetSumAmount(args);
            }
        }

        return numberOfRecords;
    }
}
