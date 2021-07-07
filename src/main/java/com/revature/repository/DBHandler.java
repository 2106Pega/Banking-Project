package com.revature.repository;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface DBHandler {
	//CRUD
	
	//Create
	public void insertNewUser(User user);
	
	public void insertNewBankAccount(BankAccount acc, User user);
	
	//Read
	public BankAccount selectBankAccountByName(User user, String name);
	
	public List<BankAccount> selectBankAccountsByUserName(String username);
	
	public List<BankAccount> selectBankAccountsToBeApproved();
	
	public User selectUserByUsername(String username);
	
	public List<User> selectCustomersOrderedByLastName();
	
	public User selectCustomerByUsername(String username);
	
	public User selectUserByUsernameAndPassword(String username, String password);
	
	//Update
//	public void updatePassword(BankAccount acc);
	
	public void updateBalance(BankAccount acc, User user, double newBalance);
	
	public void updateBankAccountApproval(BankAccount acc, User employee);
	
	//Delete
//	public void deleteBankAccount(BankAccount acc);
}
