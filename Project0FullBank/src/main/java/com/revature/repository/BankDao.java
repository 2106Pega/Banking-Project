package com.revature.repository;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;

public interface BankDao {

	// Create
	int insertBankAccount(BankAccount account);
	boolean insertCustomerAccount(CustomerAccount account);
	boolean insertEmployeeAccount(EmployeeAccount account);
	
	// Read
	BankAccount selectBankAccountById(int id);
	List<BankAccount> selectBankAccountsByCustomer(int customerId);
	List<BankAccount> selectAllBankAccounts();
	CustomerAccount selectCustomerAccountByUsername(String username);
	EmployeeAccount selectEmployeeAccountByUsername(String username);
	
	// Update
	void updateBankAccount(BankAccount account);
	void updateCustomerAccount(CustomerAccount account);
	
	// Delete
	void deleteBankAccount(BankAccount account);
	void deleteCustomerAccount(CustomerAccount account);
	
	// Custom Stored Procedure
	void transfer(BankAccount sender, BankAccount receiver, double amount);
	
}
