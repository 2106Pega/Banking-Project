package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public interface BankSystem {
	
	
	boolean approveRequest(); 
	
	User login(String username, String password); // check if a part of database
	
	Account createNewAccount();
	
	User createNewUser();
	
	boolean register(User user); // put in database
	
	boolean viewLog(); //call serialization
	
	boolean authenticate();
	
	boolean authenticateLoginUsername(String usernameInput);
	
	boolean authenticateLoginPassword(String passwordInput);
	
	List<Account> getCustomerAccounts(int id);
	
	boolean withdraw(Account account, double withdrawAmount);
	
	boolean deposit(Account account, double depositAmount);
	
	boolean transfer(Account originAccount, Account transferToAccount, double transferAmount);
}
