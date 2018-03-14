package com.kollect.etl.service;

import com.kollect.etl.dataaccess.AgeInvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeInvoiceService {
    @Autowired
    private AgeInvoiceDao ageInvoiceDao;

    public List<Object> getAgeInvoiceById(Object object) {
        return this.ageInvoiceDao.getAgeInvoiceById(object);
        // TODO Auto-generated method stub

    }

    public int updateAgeInvoice(Object object) {
        return this.ageInvoiceDao.updateAgeInvoice(object);
    }

}
