package com.revature.pzero.repository;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public interface Bank {

	//CRUD
	
	//create
	boolean newUser(User u, String userType);
	boolean newAccount(Account a);
	
	//read
	User login(String username, String password);
	Account viewAccountByAccountId(int accountId);
	List<Account> viewAccountByUserID(int userID);
	List<Account> viewAllAccounts();
	List<String> viewUsernames(); //returns a list of usernames
	User getUserById(int userID);
	User getUserFromAccountId(int accountId);
	List<Account> viewUnapprovedAccounts();
	
	//update
	boolean withdraw(Account a, double newAmount);
	boolean deposit(Account a, double newAmount);
	boolean transfer(Account a, Account b, double transferAmount);
	boolean updateApproval(Account a);
	
	//delete
	boolean closeAccount(Account a);
	boolean deleteUser(User u);
}
