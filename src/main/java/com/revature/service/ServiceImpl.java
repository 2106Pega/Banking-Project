package com.revature.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.DBHandler;
import com.revature.util.MoneyUtils;

public class ServiceImpl implements Service{

	private DBHandler dbHandler;
	
	public ServiceImpl(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	// Customer Methods
	
	@Override
	public boolean makeMoneyTransfer(BankAccount sender, BankAccount receiver, double amount) {
		amount = MoneyUtils.round(amount);
		changeBalance(sender, sender.getBalance() - amount);
		changeBalance(receiver, receiver.getBalance() + amount);
		return false;
	}

	@Override
	public void changeBalance(BankAccount acc, double amount) {
		amount = MoneyUtils.round(amount);
		acc.setBalance(amount);
		dbHandler.updateBalance(acc, amount);
	}

	@Override
	public void createNewUser(String firstName, String lastName, String username, String password) {
		dbHandler.insertNewUser(new User(username, password, firstName, lastName, false));
		
	}

	@Override
	public BankAccount createNewBankAccount(User user, String name, double balance) {
		balance = MoneyUtils.round(balance);
		BankAccount newBankAccount = new BankAccount(0, user.getUsername(), name, balance, false);
		
		dbHandler.insertNewBankAccount(newBankAccount);
		newBankAccount = dbHandler.selectBankAccountByName(user, name);
		
		return newBankAccount;
	}
	
	@Override
	public User logIn(String username, String password) {
		return dbHandler.selectUserByUsernameAndPassword(username, password);
	}

	// Employee Methods
	
	@Override
	public List<BankAccount> getBankAccountsAwaitingApproval() {
		return dbHandler.selectBankAccountsToBeApproved();
	}

	@Override
	public List<BankAccount> getCustomerBankAccounts(User user) {
		return dbHandler.selectBankAccountsByUserName(user.getUsername());
	}
	
	@Override
	public void validateBankAccount(BankAccount acc) {
		acc.setApproved(true);
		dbHandler.updateBankAccountApproval(acc);
	}

	@Override
	public List<User> getCustomersOrderedByLastName() {
		
		return dbHandler.selectCustomersOrderedByLastName();
	}
	
	// Helper Methods
	
	@Override
	public boolean isValidLogIn(String username, String password) {
		return stringMatchesPattern(username, GENERAL_PATTERN) && stringMatchesPattern(password, GENERAL_PATTERN);
	}
	
	@Override
	public boolean isValidUsername(String username) {		
		if(username.length() <= MAX_CHARACTERS && username.length() >= MIN_CHARACTERS) {
			if(stringMatchesPattern(username, GENERAL_PATTERN)) {
				if(dbHandler.selectUserByUsername(username) == null) {
					return true;
				}
				else {
					System.out.println(username + " already exists. Please, choose a different username.");
					return false;
				}
			}
			else {
				return false;
			}
		}
		
		System.out.println("Your username does not meet the length requirement.");
		return false;
	}

	@Override
	public boolean isValidPassword(String password) {		
		if(password.length() <= MAX_CHARACTERS && password.length() >= MIN_CHARACTERS) {
			return stringMatchesPattern(password, GENERAL_PATTERN);
		}
		
		System.out.println("Your password does not meet the length requirement.");
		return false;
	}

	@Override
	public boolean passwordsMatch(String password1, String password2) {
		return password1.equals(password2);
	}

	@Override
	public boolean isValidName(String name) {		
		if(!isProblematicName(name)) {
			if(name.length() <= MAX_CHARACTERS) {
				return stringMatchesPattern(name, NAME_PATTERN);
			}
			else {
				return false;
			}
		}
		
		System.out.println("Your name exceeds the maximum length. Please, exclude hyphenated names and middle names.");
		return false;
	}
	
	@Override
	public boolean isValidBankAccountName(String name) {
		if(!isProblematicName(name)) {
			if(name.length() <= MAX_CHARACTERS) {
				return stringMatchesPattern(name, GENERAL_PATTERN);
			}
			else {
				return false;
			}
		}
		
		System.out.println("The bank account provided exceeds the maximum length. Please, try again.");
		return false;
	}
	
	public boolean stringMatchesPattern(String str, Pattern p) {
		Matcher m = p.matcher(str);
		if(!m.find()) { // m matches pattern p
			return true;
		}
		
		System.out.println(str + " is incorrectly formatted.");
		return false;
	}

	@Override
	public User getCustomerByUsername(String username) {
		return dbHandler.selectCustomerByUsername(username);
	}
	
	private boolean isProblematicName(String name) {
		if(name.equals("back")) {
			System.out.println("You are not allowed to use " + name + " in this scenario.");
			return true;
		}
		return false;
	}
}
