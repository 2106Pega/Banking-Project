package com.revature.service;

//import com.revature.exceptions.AccountIsNullException;
import com.revature.exceptions.CustomerIsNullException;
import com.revature.models.Customer;

public interface CustomerManager {

	boolean getCustomerAccount(int accountNumber);

	Customer getCustomer();

	boolean getBankAccount(int accountNumber) throws CustomerIsNullException;

	double displayBalance(int accountNumber);
	
	int createCustomer(String firstname, String lastname, int ssn);

	boolean withdrawAmount(int accountNumber, double amount);

	boolean depositAmount(int accountNumber, double amount);

	boolean transferAmount(int accountNumber, int transferToNumber, double amount);

	void accountApplication(double amount, String string) throws CustomerIsNullException;

	void createBankAccount(String type, double balance, int bank_membership_number);
}
