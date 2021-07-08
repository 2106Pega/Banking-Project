package com.revature.service;

import com.revature.models.User;

public interface BankManager {

	boolean authenticate(String username);

	boolean checkPassword(String username, String password);
	
	boolean validPassword(String password);

	int checkAccountType(String username);

	void applyBankAccount(String username, double startingBalance);

	String checkStartingBalance(String startingBalance);
	
	void approveBankAccount(String username, double startingBalance);
	
	User selectUser(String username);
	
	StringBuilder readLog();
	
	
}
