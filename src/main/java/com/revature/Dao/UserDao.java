package com.revature.Dao;

import com.revature.models.User;

public interface UserDao {
	public boolean login(User u);
	public boolean apply(User u);
}
