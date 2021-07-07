/*
 * Used when the user logs in for the first time
 */
package com.revature.DatabaseTools;

import com.revature.DAO.LoginDAOImpl;
import com.revature.Models.User;

public class UserLogin 
{
	public User getLogin(String username, String password)
	{
		LoginDAOImpl database = new LoginDAOImpl();
		return database.login(username, password); 
	}
}
