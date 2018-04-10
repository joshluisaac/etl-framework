package com.kollect.etl.dataaccess;

import com.kollect.etl.entity.Host;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostDao {
	IAbstractSqlSessionProvider sqlSessionProvider;

	public HostDao() {
		sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
	}

	public void insertHost(Object object) {
		sqlSessionProvider.insert("insertHost", object);
	}

	public List<Object> viewHost(Object object) {
		return sqlSessionProvider.queryObject("viewHost", object);
	}

	public List<Host> getHostById(Object object) {
		return sqlSessionProvider.queryObject("getHostById", object);
	}

	public int updateHost(Object object) {
		return sqlSessionProvider.insert("updateHost", object);
	}

	public void deleteHost(Object object) {
		sqlSessionProvider.delete("deleteHost", object);
	}
}
