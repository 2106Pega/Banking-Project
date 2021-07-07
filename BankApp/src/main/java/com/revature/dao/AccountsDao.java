package com.revature.dao;

import java.util.List;

import com.revature.models.Accounts;
import com.revature.models.Users;

public interface AccountsDao {
	
	
	//CRUD
	//CREATE
	boolean createAccount(Accounts s);

	
	//READ
	List<Accounts> selectAccountsByUserId(int userId);
	Accounts selectAccountByAccountId(int AccountId);
	


	 
	 
	 //UPDATE
	 boolean approveAccounts(int id);
	 boolean updateAccounts(Accounts account, double amount);



	 



}
