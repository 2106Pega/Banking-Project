package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

public interface ApplicationDao {

	//CRUD
	
	//CREATE
	boolean createAccount(int customerId, String accountType, double amount);
	//READ
	List<Account> selectAllApplications();
	//UPDATE
		//No update needed for applications
	//DELETE
	boolean removeApplication(Account account);
	boolean removeApplication(int id);
}
