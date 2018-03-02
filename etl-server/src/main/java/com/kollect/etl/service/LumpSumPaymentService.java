package com.kollect.etl.service;

import java.util.List;

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
}
