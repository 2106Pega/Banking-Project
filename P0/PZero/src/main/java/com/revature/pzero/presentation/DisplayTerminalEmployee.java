package com.revature.pzero.presentation;

import java.util.ArrayList;
import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystemImpl;

public class DisplayTerminalEmployee extends DisplayTerminal{
	
	public DisplayTerminalEmployee() {
		Bank bank = new BankImpl();
		bankSystem = new BankSystemImpl(bank);
		userType = "Employee";
	}
	
	//Beginning of Employee side.
	@Override
	protected void accessAccount(User user) {
		if(user.getUserType().equals("Customer")) {
			System.out.println("Invalid credentials. Are you using the wrong page?\n"
					+ "Make sure you press [1] to enter the customer portal. Thank you!\n");
			return;
		}
		
		//what to do as an employee thats logged in
		boolean quit = false;
		while(quit == false) {
			String prompt = "What would you like to do?\n"
					+ "[1] View a Customer Account\n"
					+ "[2] Delete an account\n"
					+ "[3] View unapproved accounts\n"
					+ "[4] View all accounts\n"
					+ "[5] View unapproved users\n"
					+ "[6] Reset customer password\n"
					+ "[-1] Logout";
			
			System.out.println("\nWelcome " + user.getFirstName() + " " + user.getLastName() + "\n");
			System.out.println(prompt);
			
			int decision = numberDecisions(prompt);
			
			switch(decision) {
				case 1:
					customerOrAccountId();
					break;
				case 2:
					deleteAccount();
					break;
				case 3:
					getUnapprovedAccounts(user);
					break;
				case 4:
					displayAllAccounts();
					break;
				case 5:
					getUnapprovedUsers();
					break;
				case 6:
					resetCustomerPassword();
					break;
				case -1:
					System.out.println("Thank you for your hard work!\nHave a nice day!\n");
					quit = true;
					break;
				default:
					System.out.println("Please input a valid option.");
					System.out.println(prompt);
					break;
			}
		}
		
	}	
	
	//reset a customer's password through user name -> grants temporary 
	//password to employee to give to customer
	public boolean resetCustomerPassword() {
		boolean success = false;
		String prompt = "Please enter a valid username: ";
		System.out.print(prompt);
		User userInQuestion = null;
		
		String username = validStringInput(prompt);
		
		List<User> users = bankSystem.getAllUsers();
		for(User u : users) {
			if(u.getUserName().equals(username)) {
				userInQuestion = u;
			}
		}
		
		if(userInQuestion == null) {
			System.out.println("No users exists.");
			System.out.println("Password reset failed.");
		}else {
			String newTempPassword = (userInQuestion.getLastName() + "" + userInQuestion.hashCode()).substring(0, 10);
			userInQuestion.setUserPassword(newTempPassword);
			bankSystem.updateUserPassword(userInQuestion);
			System.out.println("Password successfully reset. Please provide the new temp password (" + newTempPassword + ") to user.");
			success = true;
		}
		return success;
	}

	//can only delete accounts if they are 1.) frozen / unapproved AND 2.) have no balance.
	public void deleteAccount() {
		List<Account> allAccounts = bankSystem.getAllAccounts();
		displayListOfAccount(allAccounts);
		
		String prompt = "Please enter a valid account id to delete or [-1] to quit: ";
		
		int decision;
		Account a = null;
		boolean quit = false;
		while(quit == false) {
			System.out.print(prompt);
			decision = numberDecisions(prompt);
			
			if(decision == -1) {
				quit = quitDecision();
				System.out.println("Invalid account number. Try again: ");
				if(quit == false)
					System.out.println(prompt);
				else
					break;
			}
			
			if(decision < -1 || decision >= allAccounts.size()) {
				System.out.println("Invalid account number. Try again: ");
				continue;
			}
			
			a = bankSystem.verifyAccount(allAccounts.get(decision).getId());
			if(a == null) {
				quit = quitDecision();
				System.out.println("Invalid account number. Try again: ");
				if(quit == false)
					System.out.println(prompt);
			}else {
				break;
			}
		}
		
		if(a != null) {
			System.out.println(divider);
			a.displayBalance();
			System.out.println(divider);
			
			boolean hasBalance = false;
			boolean isAccountNotFrozen = false;
			
			if(a.getBalance() > 0) {
				hasBalance = true;
			}
			
			if(a.isApproved() == true) {
				isAccountNotFrozen = true;
			}
			
			if(hasBalance && isAccountNotFrozen) {
				System.out.println("Account has to be both frozen and must have a balance of $0.00.\nAccount #" + a.getId() + " was not deleted.\n");
			}else if(hasBalance == true && isAccountNotFrozen == false) {
				System.out.println("Account still has a balance. Balance must be $0.00 before being deleted.\nAccount #" + a.getId() + " was not deleted.\n");
			}else if(hasBalance == false && isAccountNotFrozen == true) {
				System.out.println("Account isn't frozen. Account must be frozen before being deleted.\nAccount #" + a.getId() + " was not deleted.\n");
			}else {
				prompt = "Account is both frozen and contains a balance of $0.00. Account #" + a.getId() + " can be deleted.\nProceed [yes/no]?";
				System.out.println(prompt);
				boolean yesOrNo = yesOrNoDecision(prompt);
				boolean success = false;
				if(yesOrNo == true) {
					success = bankSystem.closeAccount(a);
					if(success == true) {
						System.out.println("Account was successfully deleted.");
					}
				}else {
					System.out.println("Very well.");
				}
			}
			
		}
	}
	
	//acquire unapproved account to see which need approval
	private void getUnapprovedAccounts(User user) {
		List<Account> listOfUnapprovedAccounts = bankSystem.accountsToBeApproved(user.getId());
		boolean quit = false;
		
		while(quit == false) {
			if(listOfUnapprovedAccounts == null || listOfUnapprovedAccounts.size() == 0) {
				System.out.println("No accounts to be approved at this time!");
				quit = true;
			}else {
				displayListOfAccount(listOfUnapprovedAccounts);
				String prompt = "Select an account to approve or select [-1] to exit.";
				System.out.println(prompt);
				int decision = numberDecisions(prompt);
				
				if(decision < -1 || decision > listOfUnapprovedAccounts.size()) {
					System.out.println("Input was invalid. Please select a number beside the account you wish to view.");
				}else {
					
					if(decision == -1) {
						quit = true;
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
	}
	
	//display all accounts if needing to browse through
	private void displayAllAccounts() {
		List<Account> listOfAllAccounts = bankSystem.getAllAccounts();
		displayListOfAccount(listOfAllAccounts);
		System.out.println(divider + "\n");
	}
	
	//helper method to print list of accounts to console
	private void displayListOfAccount(List<Account> listOfAccounts) {
		int i = 0;
		for(Account a : listOfAccounts){
			System.out.println("[" + i++ + "]: " + a.toString());
		}
	}
	
	//helper method for choosing between customer ID or account ID
	private void customerOrAccountId() {
		String prompt = "Would you like to enter a [1] Customer iD or [2] Account iD? ";
		System.out.print(prompt);
		int decision;// = numberDecisions(prompt);
	
		boolean quit = false;
		while(quit == false) {
			decision = numberDecisions(prompt);
			if(decision == 1) {
				enterCustomerId();
				break;
			}else if(decision == 2) {
				enterAccountId();
				break;
			}else {
				System.out.println("No ID chosen.");
				quit = quitDecision();
				if(quit == false)
					System.out.println(prompt);
			}
		}
	}
	
	//display to enter customer id
	private void enterCustomerId() {
		String prompt = "Please enter the customer id: ";
		boolean successful = false;
		boolean quit = false;
		int customerId = -1;
		User user = null;
		
		while(successful == false && quit == false) {
			System.out.print(prompt);
			customerId = numberDecisions(prompt);
			
			if(customerId == -1) {
				//quit
				quit = true;
				break;
			}
			
			user = bankSystem.getUserById(customerId);
			
			if(user == null || user.getUserType().equals(userType)) { //cannot access other employee accounts
				System.out.println("Invalid ID.");
				quit = quitDecision();
			}else {
				successful = true;
			}
		}
		
		if(quit != true) {
			accessCustomerAccount(user);
		}
	}
	
	//display to enter account id
	private void enterAccountId() {
		String prompt = "Please enter the account id: ";
		boolean successful = false;
		boolean quit = false;
		int accountId = -1;
		Account account = null;
		
		while(successful == false && quit == false) {
			System.out.print(prompt);
			accountId = numberDecisions(prompt);
			
			if(accountId == -1) {
				//quit
				quit = true;
				break;
			}
			
			account = bankSystem.getAccountById(accountId);
			
			if(account == null) {
				System.out.println("Invalid ID.");
				quit = quitDecision();
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
	
	//view a customer's account
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
			
			System.out.print("Select an account: ");
			
			boolean quit = false;
			while(quit == false) {
				accountToView = numberDecisions(prompt);
				if(accountToView < 0 || accountToView > listOfCustomerAccounts.size()) {
					System.out.println("Invalid input");
					quit = quitDecision();
					if(quit == false)
						System.out.println(prompt);
				}else {
					break;
				}
			}
		}
		
		if(accountToView != -1) {
			viewAccountDisplay(user, listOfCustomerAccounts.get(accountToView));
		}
	}
	
	//view specific user account and can freeze it if needed
	public void viewAccountDisplay(User user, Account account) {
		System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
		
		if(!account.getNickName().isBlank())
			System.out.println("Account #: " + account.getId() + " \"" + account.getNickName() + "\"");
		else
			System.out.println("Account #: " + account.getId());
		
		System.out.println(divider + "\n");
		
		account.displayBalance();
		
		String prompt = "";
		boolean yesOrNo;
		
		if(account.isApproved() == false) {
			prompt = "Would you like to unfreeze the account [yes/no]?";
			System.out.print(prompt);
			yesOrNo = yesOrNoDecision(prompt);
		}else {
			prompt = "Would you like to freeze this account [yes/no]?";
			System.out.print(prompt);
			yesOrNo = yesOrNoDecision(prompt);
		}
		
		if(yesOrNo == true) {
			account.setApproved(!account.isApproved());
			boolean success = bankSystem.approveAccount(account);
			if(success)
				System.out.println("Account #" + account.getId() + " has been frozen. All transactions will be halted for this account.");
			else
				System.out.println("Account approval failed. Need manager approval.");
		}else {
			System.out.println("Ending account view.\n");
		}
	}
	
	//helper method to print list of users to the console
	private void displayListOfUsers(List<User> listOfUsers) {
		int i = 0;
		for(User u : listOfUsers){
			System.out.println("[" + i++ + "]: " + u.toString());
		}
	}
	
	//helper method to acquire unapproved users
	private void getUnapprovedUsers() {
		boolean quit = false;
		List<User> listOfUsers = bankSystem.getAllUsers();
		List<User> listOfUnapprovedUsers = new ArrayList<User>();
		
		for(User u: listOfUsers) {
			if(u.isUserApproved() == false)
				listOfUnapprovedUsers.add(u);
		}
		
		listOfUnapprovedUsers.sort((User u1, User u2) -> u1.getUserType().compareTo(u2.getUserType()));
		
		while(quit == false) {
			if(listOfUnapprovedUsers == null || listOfUnapprovedUsers.size() == 0) {
				System.out.println("No users to be approved at this time!");
				quit = true;
			}else {
				
				System.out.println("UNAPPROVED USERS:\n" + divider + "\n");
				displayListOfUsers(listOfUnapprovedUsers);
				
				String prompt = "Select a user to approve or select [-1] to exit.";
				System.out.println(prompt);
				int decision = numberDecisions(prompt);
				
				if(decision < -1 || decision >= listOfUnapprovedUsers.size()) {
					System.out.println("No user selected. Please select a number beside the user you wish to approve.");
				}else {
					
					if(decision == -1) {
						quit = true;
					}else {
						
						boolean successful = bankSystem.approveUser(listOfUnapprovedUsers.get(decision));
						
						if(successful == true) {
							listOfUnapprovedUsers.remove(decision);
							System.out.println("Successfully approved.");
						}else {
							System.out.println("Approval ERROR. User not approved.");
						}
					}
				}
			}
		}
	}
	
	@Override 
	public void displaySignUp() {
		super.displaySignUp();
		System.out.println("Thank you for signing up. Please alert your manager that you have complete the\n"
				+ "sign up process in order to get your Employee account approved.");
	}
	
}


