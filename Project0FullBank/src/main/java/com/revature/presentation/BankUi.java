package com.revature.presentation;

public interface BankUi {
	
	int displayWelcomeMenu();
	
	boolean registerForCustomerAccount();
	boolean registerForEmployeeAccount();
	void applyForNewBankAccount(String username);
	
	boolean viewBalance(String username);
	boolean deposit(String username);
	boolean withdraw(String username);
	boolean transferMoney(String username);
	
	boolean validateNewAccount();
	boolean viewCustomerAccounts();
	void viewTransactionLog();

}
