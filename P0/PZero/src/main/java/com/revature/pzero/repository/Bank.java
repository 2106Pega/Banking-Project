package com.revature.pzero.repository;

import java.util.List;

import com.revature.pzero.models.Account;

public interface Bank {

	//CRUD
	
	//create
	void newAccount(Account a);
	
	//read
	Account viewAccountByAccountId(int accountId);
	List<Account> viewAccountByUserID(int userID);
	List<Account> viewAllAccounts();
	
	//update
	void withdraw(Account a);
	void deposit(Account a);
	void transfer(Account a, Account b);
	
	//delete
	void closeAccount();
}
