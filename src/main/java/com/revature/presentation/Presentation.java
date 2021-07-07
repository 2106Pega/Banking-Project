package com.revature.presentation;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface Presentation {

	// Everyone Methods
	
	public void welcomeMessage();
	
	public User logInOrCreateNewUserPrompt();
	
	public User logInPrompt();
	
	// Customer Methods
	
	public void createNewUserPrompt();
	
	public void displayCustomerOptions(User user);
	
	public void displayBankAccountOptions(User user, BankAccount acc);
	
	public BankAccount createNewBankAccountPrompt(User user);

	public BankAccount pickBankAccount(User user);
	
	public void checkBalance(BankAccount acc);
	
	public void deposit(BankAccount acc);
	
	public void withdraw(BankAccount acc);
	
	public void makeMoneyTransferToSelf(User user, BankAccount acc);
	
	public void makeMoneyTransferToOther(BankAccount acc);
	
	// Employee Methods
	
	public void displayEmployeeOptions(User user);
	
	public void approveBankAccountsPrompt();
	
	public void viewCustomerBankAccountsPrompt();
	
	public void viewLog();
}
