package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.HostDao;
import com.kollect.etl.entity.Host;

@Service
public class HostService {
	@Autowired
	private HostDao hostDao;

	public int insertHost(Object object) {
		return this.hostDao.insertHost(object);
	}

	public List<Object> viewHost(Object object) {
		return this.hostDao.viewHost(object);
		// TODO Auto-generated method stub

	}

	public List<Host> getHostById(Object object) {
		return this.hostDao.getHostById(object);
	}

	public int updateHost(Object object) {
		return this.hostDao.updateHost(object);
	}
	
	public int deleteHost(Object object) {
		return this.hostDao.deleteHost(object);
	}
}
