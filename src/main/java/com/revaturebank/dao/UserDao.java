package com.revaturebank.dao;

import com.revaturebank.model.User;

public interface UserDao {
	//READ
	public User getUser(String userName) throws Exception;
	
	//UPDATE
	public void saveUser(User user) throws Exception;

}
