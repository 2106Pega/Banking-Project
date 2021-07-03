package com.revature.pzero.repository;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public interface Bank {

	//CRUD
	
	//create
	boolean newUser(User u);
	boolean newAccount(Account a);
	
	//read
	Account viewAccountByAccountId(int accountId);
	List<Account> viewAccountByUserID(int userID);
	List<Account> viewAllAccounts();
	
	//update
	boolean withdraw(Account a);
	boolean deposit(Account a);
	boolean transfer(Account a, Account b);
	
	//delete
	boolean closeAccount();
	boolean deleteUser();
}
