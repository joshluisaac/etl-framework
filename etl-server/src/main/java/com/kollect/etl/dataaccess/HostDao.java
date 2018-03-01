package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kollect.etl.entity.Host;

@Repository
public class HostDao {
	IAbstractSqlSessionProvider sqlSessionProvider;

	public HostDao() {
		sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
	}

	public int insertHost(Object object) {
		return sqlSessionProvider.insert("insertHost", object);
	}

	public List<Object> viewHost(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.queryObject("viewHost", object);
	}

	public List<Host> getHostById(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.queryObject("getHostById", object);
	}

	public int updateHost(Object object) {
		return sqlSessionProvider.insert("updateHost", object);
	}

	public int deleteHost(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.delete("deleteHost", object);	
	}
}
