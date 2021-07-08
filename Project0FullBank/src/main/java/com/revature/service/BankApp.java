package com.revature.service;

import java.util.List;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.exceptions.UsernameAlreadyExistsException;

public interface BankApp {
	
	boolean validateCustomerLogin(String username, String password) throws NoSuchAccountException;
	boolean validateEmployeeLogin(String username, String password) throws NoSuchAccountException;
	
	void createNewCustomerAccount(String username, String password) throws UsernameAlreadyExistsException;
	void createNewEmployeeAccount(String username, String password) throws UsernameAlreadyExistsException;
	void createNewBankAccount(double balance, int customerId);
	
	int getCustomerIdFromUsername(String username) throws NoSuchAccountException;
	List<Integer> getBankAccountIds();
	List<Integer> getBankAccountIds(String username) throws NoSuchAccountException;
	List<Integer> getUnvalidatedAccountIds();
	
	double getBalance(int id) throws NoSuchAccountException;
	void makeDeposit(int id, double amount) throws BadInputException, NoSuchAccountException;
	void makeWithdrawal(int id, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException;
	void makeTransfer(int id1, int id2, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException;
	
	void approveAccount(int id) throws NoSuchAccountException;
	void rejectAccount(int id) throws NoSuchAccountException;

}
