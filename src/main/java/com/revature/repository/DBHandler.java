package com.revature.repository;

import java.util.List;

import com.revature.models.BankAccount;

public interface DBHandler {
	//CRUD
	
	//Create
	public void insertBankAccount(BankAccount acc);
	
	//Read
	public List<BankAccount> selectBankAccountsByCustomerId(int id);
	public List<BankAccount> selectBankAccountsByName(String name);
	public BankAccount selectBankAccountByUserName(String username);
	public List<BankAccount> selectBankAccountsToBeApproved();
	
	//Update
	public void updateBankAccount(BankAccount acc);
//	public void updatePassword(BankAccount acc);
	public void updateBalance(BankAccount acc);
	
	//Delete
	public void deleteBankAccount(BankAccount acc);
}
