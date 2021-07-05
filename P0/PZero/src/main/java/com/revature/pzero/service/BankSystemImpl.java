package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;

public class BankSystemImpl implements BankSystem{

	private Bank bank;
	
	public BankSystemImpl(Bank bank) {
		this.bank = bank;
	}

	@Override
	public boolean approveRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account createNewAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createNewUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean viewLog() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticate() {
		// TODO Auto-generated method stub
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

	@Override
	public List<Account> getCustomerAccounts(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean withdraw(Account account, double withdrawAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deposit(Account account, double depositAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transfer(Account originAccount, Account transferToAccount, double transferAmount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
