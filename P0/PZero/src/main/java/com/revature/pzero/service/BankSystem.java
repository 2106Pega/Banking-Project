package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;

public interface BankSystem {
	
	//approve 
//	boolean approveRequest(); 
	
	//get the user associated with the login credentials 
	User login(String username, String password); // check if a part of database
	
	//get the user associated with the user ID
	User getUserById(int userId);
	
	//get the account associated with the account ID
	Account getAccountById(int accountId);
	
	//get the user associated with the account ID
	User getUserViaAccountNumber(int accountId);
	
	//create a valid new account
	boolean createNewAccount(Double balance, String nickName, boolean approved);
	
	//create a valid new user
	boolean createNewUser(String fName, String lName, String userType, String username, String password);
	
//	//place a new user into the db
//	boolean register(User user); // put in database
	
	//view history log
	boolean viewLog(); //call serialization
	
	//make sure both username and password go with the same user inorder to login
	boolean authenticate(String username, String password);
	
//	//check to see if username is valid
//	boolean authenticateLoginUsername(String usernameInput);
//	
//	//check to see if password is valid
//	boolean authenticateLoginPassword(String passwordInput);
	
	//check to see if account id is valid
	Account verifyAccount(int accountId);
	
	//obtain list of accounts all under user
	List<Account> getCustomerAccounts(int id);
	
	//check to see if withdraw amount is possible to be done on account
	boolean canWithdraw(Account account, double withdrawAmount);
	
	//withdraw 'withdrawAmount' from account
	boolean withdraw(Account account, double withdrawAmount);
	
	//check to see if depositAmount is possible to be taken from the account
	boolean canDeposit(Account account, double depositAmount);
	
	//deposit 'depositAmount' into the supplied account
	boolean deposit(Account account, double depositAmount);
	
	//check to see if transfer is possible with the accompanying transfer amount
	boolean canTransfer(Account transferingFrom, double transferAmount);
	
	//transfer money from one account to another
	boolean transfer(Account originAccount, Account transferToAccount, double transferAmount);
	
	//list of accounts that need to be approved (approved = false)
	List<Account> accountsToBeApproved(int userId);
	
	//update account approval to the opposite of what it is.
	boolean approveAccount(Account account);
}
