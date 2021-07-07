package com.revature.DAO;

import com.revature.Models.User;

public interface LoginDAO 
{
	public User login(String username, String password);
}
