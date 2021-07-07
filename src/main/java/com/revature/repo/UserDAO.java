package com.revature.repo;

import com.revature.models.User;

public interface UserDAO {
	public User getUserByUsernameAndPassword(String username, String password);
	public boolean insertUser(User user);
}
