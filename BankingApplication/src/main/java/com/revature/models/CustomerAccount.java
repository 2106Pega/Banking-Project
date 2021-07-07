package com.revature.models;

import org.apache.log4j.Logger;

import com.revature.services.CustomerDao;
import com.revature.services.CustomerDaoImpl;

public class CustomerAccount {
	
	final static Logger logger = Logger.getLogger(CustomerAccount.class);
	
	private CustomerDao customerDao = new CustomerDaoImpl();

	
	public void createAccount(User user, BankAccount bankAccount){
		
		try{
			// calling DOA method to create customer table 
			//customerDao.createAccount(bankAccount.getFirstName(), bankAccount.getLastName());

			// getting returned cutomer_id from the customer table and setting it into BankAccount object customerID field
			bankAccount.setCustomerID(customerDao.createAccount(bankAccount.getFirstName(), bankAccount.getLastName()));
			
			user.setid(bankAccount.getCustomerID());
			// calling DOA method to create customer login table
			customerDao.createLogin(user.getid(), user.getUsername(), user.getPassword());
			
			//calling DOA method to create customer bank account table;
			customerDao.createBankTable(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), bankAccount.getAccountType(), bankAccount.getAccountBalance(), bankAccount.isAccountApproved());
			
			//calling DOA method to create case history so employees and approve/reject 
			String caseSubject = "Needs Account Approvel";
			String caseDescription = null;
			boolean caseResolved = false;
			customerDao.createCaseHistory(bankAccount.getAccountNumber(), caseSubject, caseDescription, caseResolved);
			
			logger.info("New user created the customer account and added to the database with customer_id = " + bankAccount.getCustomerID());
			
		}catch(Exception e){
			e.printStackTrace();		
		}    	
	}

	public BankAccount loginValidation(User user) {
		BankAccount bankAccount ;

		user = customerDao.customerLoginValidation(user.getUsername(), user.getPassword());
		if(user != null)
		{
			if (user.getid() > 0) {
				bankAccount = loadCustomerInfoByID(user.getid());
				
				return bankAccount;
			}
			else return null;
			
		}
		else {
			return null;
		}
		
		// return true;
		
	}
	
	public BankAccount loadCustomerInfoByID(int customerID) {
		BankAccount  bankAccount = null;
		
		try {
			bankAccount = customerDao.selectCustomerInfoByID(customerID);

			return bankAccount;
		}
		catch (Exception e){
			System.out.println("Cannot find the customer account");
			return bankAccount;
		}
		
	}
	
	public BankAccount addMoney(BankAccount bankAccount, double depositAmount) {
		
		if(depositAmount >= 0 ) {
			double totalBalance = (bankAccount.getAccountBalance()) + depositAmount ;
			
			customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance);
			
			if (customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance)) {
				bankAccount = loadCustomerInfoByID(bankAccount.getCustomerID());
				
				return bankAccount;
			}
			bankAccount = loadCustomerInfoByID(bankAccount.getCustomerID());
			
			return bankAccount;
		}
		else {
			return bankAccount = null;
		}	
	}
	
	public BankAccount withdrawMoney(BankAccount bankAccount, double withdrawAmout) {
		
		if( (bankAccount.getAccountBalance()) >= withdrawAmout ) {
			double totalBalance = (bankAccount.getAccountBalance()) - withdrawAmout ;
			
			if(totalBalance > 0) {
				customerDao.updateAccountBalance(bankAccount.getCustomerID(), bankAccount.getAccountNumber(), totalBalance);
				bankAccount = loadCustomerInfoByID(bankAccount.getCustomerID());
				
				return bankAccount;	
			}
			else
			{
				return bankAccount = null;
			}
		}
		else {
			return bankAccount;
		}
		
		
	}

	public BankAccount transferingMoney(BankAccount tranferFromAccount, int transferAccountNumber, double transferAmount) {

		BankAccount transferToAccount = new BankAccount();
		transferToAccount = customerDao.selectCustomerByAccountNumber(transferAccountNumber);
		
		if(transferToAccount != null) {	
			if(transferToAccount.isAccountApproved()) {
				transferToAccount = addMoney(transferToAccount, transferAmount);
				withdrawMoney(tranferFromAccount, transferAmount);
			}
			else{
				System.out.println("\nBankAccount of " + transferToAccount.getAccountNumber() + " is not Approved ");
				transferToAccount = null;
			}
			
		}
		else
		{
			System.out.println("\nAccount number in invalid. Tranfer process cancelled");
		}
		
		return transferToAccount;
	}
	

	
}
