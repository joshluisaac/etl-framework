package com.kollect.etl.dataaccess;

import com.kollect.etl.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
	IAbstractSqlSessionProvider sqlSessionProvider;

	public UserDao() {
		sqlSessionProvider = new AbstractSqlSessionProvider("postgres");
	}

	public void insertUser(Object object) {
		sqlSessionProvider.insert("insertUser", object);
	}

	public List<Object> viewUser(Object object) {
		return sqlSessionProvider.queryObject("viewUser", object);
	}

	public int updateUser(Object object) {
		return sqlSessionProvider.insert("updateUser", object);
	}

	public List<User> getUserById(Object object) {
		return sqlSessionProvider.queryObject("getUserById", object);
	}
}
