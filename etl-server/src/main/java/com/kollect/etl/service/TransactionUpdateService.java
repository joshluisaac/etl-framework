package com.kollect.etl.service;

import com.kollect.etl.dataaccess.TransactionUpdateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionUpdateService {

    @Autowired
    private TransactionUpdateDao TransactionUpdateDao;

    public List<Object> getTransactionAB(Object object) {
        return this.TransactionUpdateDao.getTransactionAB(object);
    }

    public List<Object> getTransactionRG(Object object) {
        return this.TransactionUpdateDao.getTransactionRG(object);
    }

    public List<Object> getTransactionYY(Object object) {
        return this.TransactionUpdateDao.getTransactionYY(object);
    }

    public List<Object> getTransactionGI(Object object) {
        return this.TransactionUpdateDao.getTransactionGI(object);
    }

    public List<Object> getTransactionRI(Object object) {
        return this.TransactionUpdateDao.getTransactionRI(object);
    }

    public List<Object> getTransactionRM(Object object) {
        return this.TransactionUpdateDao.getTransactionRM(object);
    }

    public List<Object> getTransactionRV(Object object) {
        return this.TransactionUpdateDao.getTransactionRV(object);
    }

    public List<Object> getTransactionRY(Object object) {
        return this.TransactionUpdateDao.getTransactionRY(object);
    }

    public List<Object> getTransactionYC(Object object) {
        return this.TransactionUpdateDao.getTransactionYC(object);
    }

    public List<Object> getTransactionYD(Object object) {
        return this.TransactionUpdateDao.getTransactionYD(object);
    }

    public List<Object> getTransactionYH(Object object) {
        return this.TransactionUpdateDao.getTransactionYH(object);
    }

    public List<Object> getTransactionYI(Object object) {
        return this.TransactionUpdateDao.getTransactionYI(object);
    }

    public List<Object> getTransactionYJ(Object object) {
        return this.TransactionUpdateDao.getTransactionYJ(object);
    }

    public List<Object> getTransactionYL(Object object) {
        return this.TransactionUpdateDao.getTransactionYL(object);
    }

    public List<Object> getTransactionYN(Object object) {
        return this.TransactionUpdateDao.getTransactionYN(object);
    }

    public List<Object> getTransactionYO(Object object) {
        return this.TransactionUpdateDao.getTransactionYO(object);
    }

    public List<Object> getTransactionYP(Object object) {
        return this.TransactionUpdateDao.getTransactionYP(object);
    }

    public List<Object> getTransactionYQ(Object object) {
        return this.TransactionUpdateDao.getTransactionYQ(object);
    }

    public List<Object> getTransactionYR(Object object) {
        return this.TransactionUpdateDao.getTransactionYR(object);
    }

    public List<Object> getTransactionYS(Object object) {
        return this.TransactionUpdateDao.getTransactionYS(object);
    }

    public List<Object> getTransactionYT(Object object) {
        return this.TransactionUpdateDao.getTransactionYT(object);
    }

    public List<Object> getTransactionYU(Object object) {
        return this.TransactionUpdateDao.getTransactionYU(object);
    }

    public List<Object> getTransactionYV(Object object) {
        return this.TransactionUpdateDao.getTransactionYV(object);
    }

    public List<Object> getTransactionYW(Object object) {
        return this.TransactionUpdateDao.getTransactionYW(object);
    }

    public List<Object> getTransactionYX(Object object) {
        return this.TransactionUpdateDao.getTransactionYX(object);
    }

    public List<Object> getTransactionYK(Object object) {
        return this.TransactionUpdateDao.getTransactionYK(object);
    }

    public List<Object> getTransactionY1(Object object) {
        return this.TransactionUpdateDao.getTransactionY1(object);
    }

    public List<Object> getTransactionYE(Object object) {
        return this.TransactionUpdateDao.getTransactionYE(object);
    }

    public List<Object> getTransactionYM(Object object) {
        return this.TransactionUpdateDao.getTransactionYM(object);
    }

    public List<Object> getTransactionYF(Object object) {
        return this.TransactionUpdateDao.getTransactionYF(object);
    }

    public List<Object> getTransactionZZ(Object object) {
        return this.TransactionUpdateDao.getTransactionZZ(object);
    }

    public List<Object> getTransactionOthers(Object object) {
        return this.TransactionUpdateDao.getTransactionOthers(object);
    }

    public int updateTransactionLoad(Object object) {
        return this.TransactionUpdateDao.updateTransactionLoad(object);
    }

    public int processTransactionList(final List<Object> list){
        int rowCount = list.size();
        for (int i = 0; i < rowCount; i++) {
            Map<Object, Object> map = (Map<Object, Object>) list.get(i);
            Map<Object, Object> args = new HashMap<>();
            args.put("line_of_business", map.get("line_of_business"));
            args.put("invoice_no", map.get("invoice_no"));
            args.put("invoice_reference", map.get("invoice_reference"));
            args.put("load_id", map.get("load_id"));
            this.updateTransactionLoad(args);
        }

        return rowCount;
    }

}
