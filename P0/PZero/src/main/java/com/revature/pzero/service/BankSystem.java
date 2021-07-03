package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public interface BankSystem {
	
	
	boolean approveRequest(); 
	
	boolean login(String username, String password); // check if a part of database
	
	boolean register(User user, String username, String password); // put in database
	
	boolean viewLog(); //call serialization
	
	boolean authenticate();
	
	boolean authenticateLoginUsername(String usernameInput);
	
	boolean authenticateLoginPassword(String passwordInput);
	
	User getUserViaLogin(String username, String password);
	
	List<Account> getCustomerAccounts(int id);
	
	boolean verifyAccount(int id);
	
	boolean withdraw(Account account, double withdrawAmount);
	
	boolean deposit(Account account, double depositAmount);
	
	boolean transfer(Account account, double transferAmount);
}
