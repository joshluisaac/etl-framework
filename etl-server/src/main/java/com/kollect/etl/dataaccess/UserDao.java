package com.kollect.etl.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kollect.etl.entity.User;

@Repository
public class UserDao {
	IAbstractSqlSessionProvider sqlSessionProvider;

	public UserDao() {
		sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
	}

	public int insertUser(Object object) {
		return sqlSessionProvider.insert("insertUser", object);
	}

	public List<Object> viewUser(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.queryObject("viewUser", object);
	}

	public int updateUser(Object object) {
		return sqlSessionProvider.insert("updateUser", object);
	}

	public List<User> getUserById(Object object) {
		// TODO Auto-generated method stub
		return sqlSessionProvider.queryObject("getUserById", object);
	}
}
