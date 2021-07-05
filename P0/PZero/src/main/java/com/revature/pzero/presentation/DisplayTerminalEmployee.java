package com.revature.pzero.presentation;

import java.util.ArrayList;
import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.service.BankSystemImpl;

public class DisplayTerminalEmployee extends DisplayTerminal{
	
	/*
	 * 
	 * protected void displayLogin()
	 * protected User checkLogin()
	 * protected boolean quitDecision()
	 * protected boolean yesOrNoDecision(String prompt)
	 * protected boolean areYouSure()
	 * protected void logout()
	 * 
	 */
	
	public void DisplayTerminalEmployee() {
		bankSystem = new BankSystemImpl(bank);
		userType = "Employee";
	}
	
	@Override
	protected void accessAccount(User user) {
		//what to do as an employee thats logged in
		
		String prompt = "What would you like to do?\n"
				+ "[1] View a Customer Account\n"
				+ "[2] View history log\n"
				+ "[3] View accounts that need approval\n"
				+ "[-1] Logout";
		
		System.out.println(prompt);
		
		int decision = numberDecisions(prompt);
		
		switch(decision) {
			case 1:
				customerOrAccountId();
				break;
			case 2:
				logDisplay();
				break;
			case 3:
				getUnapprovedAccounts(user);
				break;
			case -1:
				System.out.println("Thank you for your hard work!\nHave a nice day!");
				break;
			default:
				System.out.println("ERROR. -> accessAccount (Employee)");
		}
		
		
	}	
	
	/////////////////////////////////////////////////////
	public void logDisplay() {
		System.out.println("DISPLAY LOGOUT");
		
	}
	
//	public void verifyAccountDisplay() {
//		//can freeze account if violations on it on user end
//	}
	
	private void getUnapprovedAccounts(User user) {
		List<Account> listOfUnapprovedAccounts = bankSystem.accountsToBeApproved(user.getId());
		boolean quit = false;
		
		while(quit == false) {
			if(listOfUnapprovedAccounts == null || listOfUnapprovedAccounts.size() == 0)
				System.out.println("No accounts to be approved at this time!");
			else {
				displayListOfAccount(listOfUnapprovedAccounts);
				String prompt = "Select an account to approve or select [-1] to exit.";
				System.out.println(prompt);
				int decision = numberDecisions(prompt);
				
				if(decision < 0 || decision > listOfUnapprovedAccounts.size()) {
					System.out.println("ERROR -> getUnapprovedAccounts()");
				}else {
					boolean successful = bankSystem.approveAccount(listOfUnapprovedAccounts.get(decision));
					
					if(successful == true) {
						listOfUnapprovedAccounts.remove(decision);
						System.out.println("Successfully approved.");
					}else {
						System.out.println("Approval ERROR. Account not approved.");
					}
				}
			}
		}
	}
	
	private void displayListOfAccount(List<Account> listOfAccounts) {
		int i = 0;
		for(Account a : listOfAccounts){
			System.out.println("[" + i++ + "]: " + a.toString());
		}
	}
	
	private void customerOrAccountId() {
		String prompt = "Would you like to enter a [1] Customer id or [2] Account id?";
		
		int decision = numberDecisions(prompt);
		
		if(decision == 1) {
			enterCustomerId();
		}else if(decision == 2) {
			enterAccountId();
		}else {
			System.out.println("ERROR -> customerOrAccountId");
		}
	}
	
	private void enterCustomerId() {
		String prompt = "Please enter the customer id.";
		boolean successful = false;
		boolean quit = false;
		int customerId = -1;
		User user = null;
		
		while(successful == false && quit == false) {
			customerId = numberDecisions(prompt);
			
			if(customerId == -1) {
				//quit
				quit = true;
				break;
			}
			
			user = bankSystem.getUserById(customerId);
			
			if(user == null || user.getUserType().equals(userType)) { //cannot access other employee accounts
				System.out.println("Invalid ID.");
			}else {
				successful = true;
			}
		}
		
		if(quit != true) {
			accessCustomerAccount(user);
		}else {
			//quit
		}
	}
	
	private void enterAccountId() {
		String prompt = "Please enter the account id.";
		boolean successful = false;
		boolean quit = false;
		int accountId = -1;
		Account account = null;
		
		while(successful == false && quit == false) {
			accountId = numberDecisions(prompt);
			
			if(accountId == -1) {
				//quit
				quit = true;
				break;
			}
			
			account = bankSystem.getAccountById(accountId);
			
			if(account == null) {
				System.out.println("Invalid ID.");
			}else {
				successful = true;
			}
		}
		
		if(quit != true) {
			User user = bankSystem.getUserViaAccountNumber(account.getId());
			viewAccountDisplay(user, account);
		}else {
			//quit
		}
	}
	
	public void accessCustomerAccount(User user) {
		System.out.println("\nAccounts for " + user.getFirstName() + " " + user.getLastName());
		System.out.println(divider);
		int i = 0;
		String prompt = "";
		int accountToView = -1;
		
		List<Account> listOfCustomerAccounts = bankSystem.getCustomerAccounts(user.getId());
		
		if(listOfCustomerAccounts == null || listOfCustomerAccounts.size() == 0) {
			System.out.println("Customer " + user.getId() + " has no open accounts at this time.");
		}else {
			for(Account a : listOfCustomerAccounts) {
				prompt += "[" + i + "]: " + a.toString() + "\n";
				System.out.println("[" + i++ + "]: " + a.toString());
			}
			
			//select an account
			accountToView = numberDecisions(prompt);
		}
		
		if(accountToView != -1) {
			viewAccountDisplay(user, listOfCustomerAccounts.get(accountToView));
		}
	}
	
	//view specific user account and can freeze it if desired
	public void viewAccountDisplay(User user, Account account) {
		System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
		System.out.println("Account #: " + account.getId() + " \"" + account.getNickName() + "\"");
		System.out.println(divider + "\n");
		
		account.displayBalance();
		
		String prompt = "";
		boolean yesOrNo;
		
		if(account.isApproved() == false) {
			prompt = "Would you like to unfreeze the account?";
			System.out.println(prompt);
			yesOrNo = yesOrNoDecision(prompt);
		}else {
			prompt = "Would you like to freeze this account?";
			System.out.println(prompt);
			yesOrNo = yesOrNoDecision(prompt);
		}
		
		if(yesOrNo == true) {
			account.setApproved(!account.isApproved());
		}
	}
}


