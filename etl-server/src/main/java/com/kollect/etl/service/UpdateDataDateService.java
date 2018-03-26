package com.kollect.etl.service;

import com.kollect.etl.dataaccess.UpdateDataDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDataDateService {
    @Autowired
    private UpdateDataDateDao updateDataDateDao;
    private boolean lock;

    public int updateDataDate() {
        return this.updateDataDateDao.updateDataDate();
    }

    public int runupdateDataDate(){
        int numberOfRows = 1;
        if (!lock) {
            lock = true;
            this.updateDataDate();
        }
        lock = false;
        return numberOfRows;
    }
}
