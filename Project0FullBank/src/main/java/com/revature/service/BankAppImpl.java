package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.repository.BankDao;

public class BankAppImpl implements BankApp {
	
	private BankDao dao;
	
	public BankAppImpl(BankDao dao) {
		this.dao = dao;
	}

	/**
	 * Looks for username in database and checks whether the given password is correct
	 * @param	username - the username given to the console by the user
	 * @param	password - the password given to the console by the user
	 * @return	true if password matches password in database, false otherwise
	 * @throws	NoSuchAccountException if account with username cannot be found
	 */
	@Override
	public boolean validateCustomerLogin(String username, String password) throws NoSuchAccountException {
		
		CustomerAccount account = dao.selectCustomerAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return (password.equals(account.getPassword()));
		
	}
	
	/**
	 * Looks for username in database and checks whether the given password is correct
	 * @param	username - the username given to the console by the user
	 * @param	password - the password given to the console by the user
	 * @return	true if password matches password in database, false otherwise
	 * @throws	NoSuchAccountException if account with username cannot be found
	 */
	@Override
	public boolean validateEmployeeLogin(String username, String password) throws NoSuchAccountException {
		
		EmployeeAccount account = dao.selectEmployeeAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return (password.equals(account.getPassword()));
		
	}

	/**
	 * Creates a new customer account in the database with the given username and password as
	 * 	well as the customer's first and last name
	 * @param	firstName - the user's first name
	 * @param	lastName - the user's last name
	 * @param	username - the user's created username
	 * @param	password - the user's created password
	 */
	@Override
	public void createNewCustomerAccount(String username, String password) throws UsernameAlreadyExistsException {
		
		if (dao.selectCustomerAccountByUsername(username) != null) {
			throw new UsernameAlreadyExistsException();
		}
		
		CustomerAccount account = new CustomerAccount(0, username, password);
		
		dao.insertCustomerAccount(account);
		
	}
	
	/**
	 * Creates a new employee account in the database with the given username and password
	 * @param	firstName - the user's first name
	 * @param	lastName - the user's last name
	 * @param	username - the user's created username
	 * @param	password - the user's created password
	 */
	@Override
	public void createNewEmployeeAccount(String username, String password) throws UsernameAlreadyExistsException {
		
		if (dao.selectEmployeeAccountByUsername(username) != null) {
			throw new UsernameAlreadyExistsException();
		}
		
		EmployeeAccount account = new EmployeeAccount(0, username, password);
		
		dao.insertEmployeeAccount(account);
		
	}
	
	/**
	 * Creates a new bank account in the database with the given balance and customer ID
	 * @param	balance - the starting balance for the account specified by user
	 * @param	customerID - the user's unique identification key
	 */
	@Override
	public void createNewBankAccount(double balance, int customerId) {
		
		BankAccount account = new BankAccount(0, balance, false, customerId);
		
		dao.insertBankAccount(account);
		
	}
	
	
	/**
	 * Obtains a customer's ID number knowing their username
	 * @param 	username - the username to find the ID for
	 * @return 	the customer's account ID
	 */
	@Override
	public int getCustomerIdFromUsername(String username) throws NoSuchAccountException {
		
		CustomerAccount account = dao.selectCustomerAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return account.getId();
	}
	
	/**
	 * Obtains a list of IDs for all the bank accounts in the bank
	 * @return 	a list of IDs for all the bank accounts in the bank
	 */
	@Override
	public List<Integer> getBankAccountIds() {
		
		List<Integer> idList = new ArrayList<>();
		
		for (BankAccount b: dao.selectAllBankAccounts()) {
			if (b.isApproved()) {
				idList.add(b.getId());
			}
		}
		
		return idList;
	}

	/**
	 * Obtains a list of IDs for all the bank accounts associated with a given user
	 * @param	username - the username of the user to find the accounts for
	 * @return 	a list of IDs for all the bank accounts associated with the username
	 */
	@Override
	public List<Integer> getBankAccountIds(String username) throws NoSuchAccountException {
		
		CustomerAccount cAccount = dao.selectCustomerAccountByUsername(username);
		
		if (cAccount == null) {
			throw new NoSuchAccountException();
		}
		
		int id = cAccount.getId();
		List<Integer> idList = new ArrayList<>();
		
		for (BankAccount b: dao.selectBankAccountsByCustomer(id)) {
			if (b.isApproved()) {
				idList.add(b.getId());
			}
		}
		
		return idList;
	}
	
	/**
	 * Returns a list of account IDs for accounts that have not been approved
	 * 	by an employee only.
	 * @return	a list of account IDs for the unvalidated accounts
	 */
	@Override
	public List<Integer> getUnvalidatedAccountIds() {
		
		List<Integer> idList = new ArrayList<>();
		
		for (BankAccount b: dao.selectAllBankAccounts()) {
			if (!b.isApproved()) {
				idList.add(b.getId());
			}
		}
		
		return idList;
	}

	/**
	 * Finds the account with the given ID number and returns its balance
	 * @param	id - the ID number of the account to search for
	 * @return	the account balance
	 * @throws 	NoSuchAccountException if account does not exist
	 */
	@Override
	public double getBalance(int id) throws NoSuchAccountException {
		
		BankAccount account = dao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return account.getBalance();
	}

	/**
	 * Deposits the given amount into the bank account with the given ID number
	 * @param	id - the ID of the bank account to deposit to
	 * @param	amount - the amount of money to be deposited
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void makeDeposit(int id, double amount) throws BadInputException, NoSuchAccountException {

		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account = dao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		account.setBalance(account.getBalance() + amount);
		
		dao.updateBankAccount(account);
		
	}

	/**
	 * Withdraws the given amount from the bank account with the given ID number
	 * @param	id - the ID of the bank account to withdraw from
	 * @param	amount - the amount of money to be withdrawn
	 * @throws	OverdrawnException if amount is greater than balance in account
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void makeWithdrawal(int id, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account = dao.selectBankAccountById(id);
		if (account == null) {
			throw new NoSuchAccountException();
		}
		double balance = account.getBalance();
		
		if (amount > balance) {
			throw new OverdrawnException();
		}
		
		account.setBalance(balance - amount);
		
		dao.updateBankAccount(account);
		
	}
	
	/**
	 * Transfers the given amount of money from one account to another.
	 * @param	id1 - the ID of the account to transfer from
	 * @param 	id2 - the ID of the account to transfer to
	 * @param 	amount - the amount of money to transfer
	 * @throws	OverdrawnException if amount is greater than balance in first account
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if either account does not exist
	 */
	@Override
	public void makeTransfer(int id1, int id2, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException {
		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account1 = dao.selectBankAccountById(id1);
		BankAccount account2 = dao.selectBankAccountById(id2);
		
		if (account1 == null || account2 == null) {
			throw new NoSuchAccountException();
		}
		double balance = account1.getBalance();
		
		if (amount > balance) {
			throw new OverdrawnException();
		}
		
		dao.transfer(account1, account2, amount);
	}

	/**
	 * Approves the account with the given ID, allowing the customer to use it.
	 * @param	id - the ID of the account to be approved
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void approveAccount(int id) throws NoSuchAccountException {
		
		BankAccount account = dao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		account.setApproved(true);
		
		dao.updateBankAccount(account);
		
	}

	/**
	 * Rejects the account with the given ID, removing it from the system.
	 * @param	id - the ID of the account to be rejected
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void rejectAccount(int id) throws NoSuchAccountException {
		
		BankAccount account = dao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		dao.deleteBankAccount(account);
		
	}
	
	

}
