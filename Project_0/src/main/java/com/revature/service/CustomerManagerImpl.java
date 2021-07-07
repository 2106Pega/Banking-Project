package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ApplicationDao;
import com.revature.dao.CustomerDao;
import com.revature.exceptions.CustomerIsNullException;
import com.revature.models.Account;
import com.revature.models.Customer;

public class CustomerManagerImpl implements CustomerManager {

	CustomerDao cDao;
	AccountDao aDao;
	ApplicationDao appDao;
	Customer currentCustomerInstance;
	Account currentAccountInstance;
	
	public CustomerManagerImpl(CustomerDao cDao, AccountDao aDao) {
		super();
		this.cDao = cDao;
		this.aDao = aDao;
		currentCustomerInstance = null; //resetting the customer account
	}

	/*
	 * The impl stores a single customer, so if there is already a customer
	 */
	@Override
	public boolean getCustomerAccount(int accountNumber) {
		currentCustomerInstance = cDao.selectCustomerById(accountNumber);
		if(currentCustomerInstance == null)
			return false;
		return true;
	}

	@Override
	public Customer getCustomer() {		
		return currentCustomerInstance;
	}

	@Override
	public boolean getBankAccount(int accountNumber) throws CustomerIsNullException {
		if(currentCustomerInstance == null)
			throw new CustomerIsNullException();
		aDao.getCustomerBankAccount(accountNumber);
		return false;
	}

	@Override
	public double displayBalance(int accountNumber){
		return aDao.getAccountBalance(accountNumber);
	}

	@Override
	public int createCustomer(String firstname, String lastname, int ssn) {
		currentCustomerInstance = new Customer(firstname, lastname, 0, ssn);
		int accNumber = cDao.createCustomer(currentCustomerInstance);
		currentCustomerInstance.setBank_membership_number(accNumber);			
		return accNumber;
	}

	@Override
	public boolean withdrawAmount(int accountNumber, double amount) {
		currentAccountInstance = aDao.getCustomerBankAccount(accountNumber);
		if(currentAccountInstance != null) {
			if(currentAccountInstance.getBalance() >= amount) {
				currentAccountInstance.modifyBalance(amount);
				aDao.updateBalance(accountNumber, currentAccountInstance.getBalance());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean depositAmount(int accountNumber, double amount) {
		currentAccountInstance = aDao.getCustomerBankAccount(accountNumber);
		if(currentAccountInstance != null) {
			if(currentAccountInstance.getBalance() >= amount) {
				currentAccountInstance.modifyBalance(amount);
				aDao.updateBalance(accountNumber, currentAccountInstance.getBalance());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean transferAmount(int accountNumber, int transferToNumber, double amount) {
		if(withdrawAmount(accountNumber, amount)){
			if(depositAmount(transferToNumber, amount))
				return true;
		}
		return false;
	}

	@Override
	public void accountApplication(double amount, String string) throws CustomerIsNullException {
		if(currentCustomerInstance == null)
			throw new CustomerIsNullException();
		appDao.createAccount(currentCustomerInstance.getBank_membership_number(), string, amount);		
	}

	@Override
	public void createBankAccount(String type, double balance, int bank_membership_number) {
		aDao.createAccount(bank_membership_number, type, balance);		
	}
}
