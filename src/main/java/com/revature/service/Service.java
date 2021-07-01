package com.revature.service;

import java.util.List;

import com.revature.models.BankAccount;

public interface Service {
	
	public boolean makeMoneyTransfer(BankAccount sender, String receiverUserName);
	
	public double getBalance(BankAccount acc);
	
	public void addToBalance(double amount);
	
	public void subtractFromBalance(double amount);
	
	public void createNewAccount(String firstName, String lastName, String username, String password);
	
	public void logIn(String username, String password);
	
//	public boolean changePassword(BankAccount acc, String oldPassword, String newPassword);
	
	
	// Employee methods
	public List<BankAccount> getAccountsAwaitingApproval();
	
	public List<BankAccount> getCustomersAccounts();
}