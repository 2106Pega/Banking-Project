package com.revature.dao;

import java.util.List;

import com.revature.models.Accounts;
import com.revature.models.Users;



public interface UsersDao {
	
	
	
	
	//CRUD
	//CREATE
	boolean createUser(Users s);

	
	//READ
	 List<Users> selectAllUsers();
	 Users selectUserByUsername(String userName);
	 Users selectAccountsById(int id);

	 

}
