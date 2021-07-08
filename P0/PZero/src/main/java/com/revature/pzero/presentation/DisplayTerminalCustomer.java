package com.revature.pzero.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystemImpl;

public class DisplayTerminalCustomer extends DisplayTerminal{

	public DisplayTerminalCustomer() {
		Bank bank = new BankImpl();
		bankSystem = new BankSystemImpl(bank);
		userType = "Customer";
	}
	
	//Beginning of customer side. 
	@Override
	protected void accessAccount(User user) {
		if(user.getUserType().equals("Employee")) {
			System.out.println("Invalid credentials. Are you using the wrong page?\n"
					+ "Make sure you press [2] to enter the employee portal. Thank you!\n");
			return;
		}
		
		List<Account> listOfCustomerAccounts = bankSystem.getCustomerAccounts(user.getId());
		String prompt = "";
		
		if(listOfCustomerAccounts == null || listOfCustomerAccounts.size() == 0) {
			prompt = "Would you like to create one? [yes/no]";
			boolean quit = false;
			
			while(quit == false) {
				System.out.println("There are no accounts under this name. " + prompt);
				boolean yesOrNo = yesOrNoDecision(prompt);
	
				if(yesOrNo == true)
					displayNewAccountCreation(user);	
				quit = quitDecision();
			}
			
			if(quit == true) {
				return;
			}
		}else {
			displayAccounts(user, listOfCustomerAccounts);
		}
	}	
	
	//Differs from Employee at this point -> displays customer accounts
	public void displayAccounts(User user, List<Account> listOfCustomerAccounts) {
		boolean noActionCanBeDone = true;
		System.out.println("\nAccounts for " + user.getFirstName() + " " + user.getLastName());
		System.out.println(divider);
		
		int i = 1;
		String prompt = "";
		
		System.out.println("\nSelect [0] to Create a New Account");
		
		for(Account a : listOfCustomerAccounts) {
			prompt += "  [" + i + "]:  " + a.toString() + "\n";
			System.out.println("  [" + i++ + "]:  " + a.toString());
			if(a.isApproved() == true) { noActionCanBeDone = false; }
		}
			
		if(noActionCanBeDone == false) {
			boolean validInt = false;
			int decision = -1;
			while(validInt == false) {
			//select an account
				System.out.print("\nSelect an option: ");
				decision = numberDecisions(prompt + "\nSelect an option: ");
				
				if(decision == -1) {
					break;
				}
					
				if(decision > listOfCustomerAccounts.size() || decision < 0) {
					System.out.println("Invalid number. The numbers in [] correspond to the respective prompt.");
				}else {
					if(decision != 0) {
						Account a = listOfCustomerAccounts.get(decision-1);
						if(a.isApproved() == false) {
							if(a.getBalance() == 0)
								System.out.println("Account has yet to be approved. No tranactions can be done at this time.");
							else{
								System.out.println("Account is frozen. No transactions can be done at this time on this account.");
							}
						}else {
							validInt = true;
						}
					}else {
						validInt = true;
					}
				}
			}
				
			if(decision == 0) {
				displayNewAccountCreation(user);
			}else if(decision == -1){
				return;
			} else {
				displayUserAccount(user, listOfCustomerAccounts.get(--decision));
			}
			
		}else {
			System.out.println("\nAll accounts are either frozen or awaiting approval.\n"
					+ "No action can be done at this time on any existing accounts.\n"
					+ "Please call " +  bankCustomerServiceNumber + " for assistance.\n");
		}
	}
	
	//Account creation method. Sends request to "offices" for approval before customer can utilize.
	public void displayNewAccountCreation(User u) { 
		String prompt = "Would you like to give your account a nickname\n(i.g Tom's Savings Account)?";
		String nickName = "";
		
		System.out.println("Thank you for your interest in creating an account.\n"
				+ "We have sent a notification to our offices that will get your account\n"
				+ "up and running in no time depending on your history with us.\n"
				+ "In the mean time, would you like to give your account a nickname\n"
				+ "(i.g Tom's Savings Account)? [yes/no]");
		
		boolean yesOrNo = yesOrNoDecision(prompt);
		if(yesOrNo == true) {
			prompt = "Enter your account's nickname: ";
			System.out.println(prompt);
			nickName = validStringInput(prompt);
		}
		
		boolean success = bankSystem.createNewAccount(u.getId(), 0.0, nickName, false);
		
		if(success) {
			System.out.println("Alright! The approval process takes at least a day, so\n"
					+ "be sure to check in after 24hrs to start using your account!");
		}else {
			System.out.println("I'm sorry. It looks like our servers are down. Please try again later while\n"
					+ "we try to get them back up. We are sorry for the inconvenience.");
		}
	}
	
	//Displays a specific user account. User can choose what they want to do with the account.
	public void displayUserAccount(User user, Account account) {
		boolean quit = false;
		boolean success = false;
		while(quit == false) {
			System.out.println("\n" + divider);
			account.displayBalance();
			System.out.println(divider + "\n");
			
			String prompt = "What would you like to do?\n\n"
					+ "[0] Withdraw\n"
					+ "[1] Deposit\n"
					+ "[2] Transfer\n"
//					+ "[3] Change Accounts\n"
					+ "[-1] Logout\n";
					
			System.out.println(prompt);
			
			int decision = numberDecisions(prompt);
			switch(decision) {
				case 0: //withdraw
					success = displayWithdraw(account);
					break;
				case 1: //deposit
					success = displayDeposit(account);
					break;
				case 2: //transfer
					success = displayTransfer(account);
					break;
//				case 3: //change accounts
//					return;
					//accessAccount(user);
//					break;
				case -1: //logout
					System.out.println("You have logged out.");
					quit = true;
					break;
				default:
				 	System.out.println("Please input a valid option.");
					break;
			}
				
			if(quit == true) {
				break; 
			}
		}
	}
	
	
	//sets user up to transfer money to desired account. Can be owned by current user or a different user
	public boolean displayTransfer(Account account) { 
		boolean quit = false;
		boolean successful = false;
		double transferAmount = 0;
		String userInput = "";
		int accountToTransfer = -1;
		String prompt = "Please input an account you wish to transfer to: ";
		
		Account accountToTransferTo = null;
		
		
		//obtains account to transfer to from user
		while(quit == false) {
			System.out.println("TRANSFER\n" + divider + "\n");
			account.displayBalance();
			
			System.out.print(prompt);
			accountToTransfer = numberDecisions(prompt);
			
			if(accountToTransfer == -1) {
				quit = true;
				break;
			}
		
			accountToTransferTo = verifyAccountId(accountToTransfer);
			
			if(accountToTransferTo == null) {
				quit = quitDecision();
			}else {
				break;
			}
		}
			
		//obtains amount desired to transfer to different account
		if(accountToTransferTo != null && quit == false) {
			prompt = "How much would you like to transfer: $";
			while(successful == false && quit == false) {	
				System.out.print(prompt);
				
				transferAmount = moneyDecisions(prompt);
				
				if(checkTransferAmount(account, transferAmount) == true) {
					successful = true;
				}else {
					quit = quitDecision();
				}
			}
		}
		
		if(quit != true) {
			if(areYouSure()) {
				successful = bankSystem.transfer(account, accountToTransferTo, transferAmount);
				if(successful == true)
					System.out.println("Transfer Successful.");
			}
		}
		
		return successful; 
	}
	
	private Account verifyAccountId(int id) { 
		return bankSystem.verifyAccount(id); 
	}
	
	private boolean checkTransferAmount(Account account, double transferAmount) { 
		return bankSystem.canTransfer(account, transferAmount);
	}
	
	private boolean checkWithdrawAmount(Account account, double withdrawAmount) {
		return bankSystem.canWithdraw(account, withdrawAmount);
	}
	
	private boolean checkDepositAmount(Account account, double depositAmount) {
		return bankSystem.canDeposit(account, depositAmount);
	}
	
	
	//sets user up to withdraw money
	public boolean displayWithdraw(Account account) {
		boolean quit = false;
		boolean successful = false;
		double withdrawAmount = 0;
		String prompt = "How much would you like to withdraw: $";
		
		while(quit == false && successful == false) {
			account.displayBalance();
			System.out.print(divider + "\nWITHDRAW\n" + divider + "\n" + prompt);
			
			withdrawAmount = moneyDecisions(prompt);
			
			if(withdrawAmount == -1) {
				quit = true;
				break;
			}
			
			if(checkWithdrawAmount(account, withdrawAmount) == true) {
				successful = true;
			}else {
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			successful = bankSystem.withdraw(account, withdrawAmount);
			if(successful)
				System.out.println("WITHDRAW successful.");
		}
		
		return successful;
	}
	
	//sets user up to deposit money
	public boolean displayDeposit(Account account) {
		boolean successful = false;
		
		double depositAmount = addMoneyPrompt(account);
		
		if(depositAmount > 0.01) {
			successful = bankSystem.deposit(account, depositAmount);
			if(successful)
				System.out.println("Deposit successful.");
		}
		
		return successful;
	}
	
	//helper method for inputting money to an account
	private double addMoneyPrompt(Account account) {
		boolean quit = false;
		boolean successful = false;
		
		double depositAmount = 0;
		String prompt = "How much would you like to add: $";
		
		while(quit == false && successful == false) {
			account.displayBalance();
			System.out.print(divider + "\nDEPOSIT\n" + divider + "\n" + prompt);
			
			
			depositAmount = moneyDecisions(prompt);
			
			if(depositAmount == -1) {
				quit = true;
				break;
			}
			
			if(checkDepositAmount(account, depositAmount) == true) {
				successful = true;
			}else {
				depositAmount = 0;
				System.out.println("Invalid transaction.");
				quit = quitDecision();
			}
		}
		
		if(quit == true)
			return -1;
		
		return depositAmount;
		
	}
	
	@Override 
	public void displaySignUp() {
		super.displaySignUp();
		System.out.println("Thank you for your interest in creating an account with us.\n"
				+ "We have sent a notification to our offices that will get your account\n"
				+ "up and running in no time depending on the information you have entered.\n"
				+ "Be sure to check in after 24hrs to start using your account!\n"
				+ "If you need any assistance, please call our customer service line at " + bankCustomerServiceNumber);
	}
}
