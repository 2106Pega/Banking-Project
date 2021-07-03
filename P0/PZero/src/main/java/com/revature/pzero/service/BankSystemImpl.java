package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public class BankSystemImpl implements BankSystem{

	public BankSystemImpl() {
		
	}
	
	public boolean approveRequest() { return false; } 
	
	
	public boolean login(String username, String password) { return false; } // check if a part of database 
	
	public boolean register(User user, String username, String password) { return false; } // put in database
	
	public boolean viewLog() { return false; } //call serialization

	@Override
	public boolean authenticate() {
		return false;
	}

	@Override
	public boolean authenticateLoginUsername(String usernameInput) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticateLoginPassword(String passwordInput) {
		// TODO Auto-generated method stub
		return false;
	}

	public User getUserViaLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Account> getCustomerAccounts(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean verifyAccount(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean withdraw(Account account, double withdrawAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deposit(Account account, double depositAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean transfer(Account account, double transferAmount) {
		// TODO Auto-generated method stub
		return false;
	}
}
