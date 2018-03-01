package com.kollect.etl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.UserDao;
import com.kollect.etl.entity.User;

@Service
public class UserManagementService {
	@Autowired
	private UserDao userDao;

	public int insertUser(Object object) {
		return this.userDao.insertUser(object);
	}

	public List<Object> viewUser(Object object) {
		return this.userDao.viewUser(object);
		// TODO Auto-generated method stub

	}

	public int updateUser(Object object) {
		return this.userDao.updateUser(object);
	}

	public List<User> getUserById(Object object){
    return this.userDao.getUserById(object);
  }

}