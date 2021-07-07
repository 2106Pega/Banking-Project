package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

public interface AccountDao {

	//CRUD
	
	//CREATE
	boolean createAccount(int customerId, String accountType, double amount);
	//READ
	List<Account> getAllCustomerAccounts(int customerId);
	Account getCustomerBankAccount(int accountId);
	double getAccountBalance(int accountId);
	//UPDATE
	boolean updateBalance(int accountId, double newBalance);
	//DELETE
}
