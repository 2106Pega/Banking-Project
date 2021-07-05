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
	
	public DisplayTerminalCustomer() {
		bankSystem = new BankSystemImpl(bank);
		userType = "Customer";
	}
	
//	public void welcomeCustomer() {
//		System.out.println("CUSTOMER WELCOME");
//	}	
	
	@Override
	protected void accessAccount(User user) {
		List<Account> listOfCustomerAccounts = bankSystem.getCustomerAccounts(user.getId());
		String prompt = "";
		
		if(listOfCustomerAccounts.equals(null)) {
			prompt = "Would you like to create one? [yes/no]";
			boolean quit = false;
			
			while(quit == false) {
				System.out.println("There are no accounts under this name." + prompt);
				boolean yesOrNo = yesOrNoDecision(prompt);
	
				if(yesOrNo == true) {
					boolean accountCreated = displayNewAccountCreation(user);
					if(accountCreated == true) {
						break;
					}else {
						System.out.println("Account creation stopped.");
						quit = quitDecision();
					}
				}
			}
			if(quit == true) {
				return;
			}
		}else {
			displayAccounts(user, listOfCustomerAccounts);
		}
	}	
	
	//differs from Employee at this point
	public void displayAccounts(User user, List<Account> listOfCustomerAccounts) {
		System.out.println("\nAccounts for " + user.getFirstName() + " " + user.getLastName());
		System.out.println(divider);
		
		int i = 0;
		String prompt = "";
		for(Account a : listOfCustomerAccounts) {
			prompt += "[" + i + "]: " + a.toString() + "\n";
			System.out.println("[" + i++ + "]: " + a.toString());
		}
		
		//select an account
		int decision = numberDecisions(prompt);
		
		displayUserAccount(user, listOfCustomerAccounts.get(decision));		
	}
	
	///////////////REDO
	public boolean displayNewAccountCreation(User u) { 
		//int id, Double balance, String nickName
		
		System.out.println("\nCREATING A NEW ACCOUNT\n" + divider + "\n");
		
		Account newAccount = new Account();
		double startingAmount = 0.0;
		boolean quit = false;
		boolean successful = false;
		
		while(quit == false) {
			if(startingAmount != 0.0)
				startingAmount = addMoneyPrompt(newAccount);
			if(startingAmount < 0.0) { //quitting
				quit = true;
			}else {
				newAccount.setBalance(startingAmount);
				System.out.println("Would you like to add a nickname to the account?");
				String s = scanner.next();
				if(areYouSure() == true) {
					newAccount.setNickName(s);
					System.out.println("All set! Your new account is waiting to be approved.");
					successful = true;
					break;
				}else {
					quit = quitDecision();
				}
			}
		}
		
		return successful; 
	}
	
	public void displayUserAccount(User user, Account account) {
		boolean quit = false;
		while(quit == false) {
			System.out.println("\n" + divider);
			account.displayBalance();
			System.out.println(divider + "\n");
			
			String prompt = "What would you like to do?\n\n"
					+ "[0] Withdraw\n"
					+ "[1] Deposit\n"
					+ "[2] Transfer\n"
					+ "[3] Change Accounts\n"
					+ "[4] Create a new account\n"
					+ "[-1] Logout\n";
					
			System.out.println(prompt);
			
			int decision = numberDecisions(prompt);
			
			boolean success = false;
			
			switch(decision) {
				case 0: //withdraw
					success = displayWithdraw(account);
					displayOptionStatus("WITHDRAW", success);
					break;
				case 1: //deposit
					success = displayDeposit(account);
					displayOptionStatus("DEPOSIT", success);
					break;
				case 2: //transfer
					success = displayTransfer(account);
					displayOptionStatus("TRANSFER", success);
					break;
				case 3: //change accounts
					accessAccount(user);
					break;
				case 4: //create a new account	
					displayNewAccountCreation(user);
					break;
				case -1: //logout
					System.out.println("Thank you for using " + bankName);
					quit = true;
					break;
				default:
					System.out.println("ERROR");
					break;
			}	
		}
		
		if(quit == true) {
			welcome(); 
		}
	}
	
	private void displayOptionStatus(String option, boolean success) {
		if(success == true)
			System.out.println(option + " successful."); //make sure balance is updated
		else
			System.out.println(option + " failed.");
	}
	
	public boolean displayTransfer(Account account) { 
		boolean quit = false;
		boolean successful = false;
		double transferAmount = 0;
		String userInput = "";
		int accountToTransfer = -1;
		String prompt = "Please input an account you wish to transfer to.\n";
		
		Account accountToTransferTo = null;
		
		while(quit == false) {
			System.out.println("TRANSFER\n" + divider + "\n");
			account.displayBalance();
			
			System.out.println(prompt);
			accountToTransfer = numberDecisions(prompt);
			
			accountToTransferTo = verifyAccountId(accountToTransfer);
			
			if(accountToTransferTo == null) {
				System.out.println("Please input a valid id.");
				quit = quitDecision();
			}
		}
			
		if(accountToTransferTo != null && quit == false) {
			prompt = "How much would you like to transfer?\nPlease input a number:";
			while(quit == false && successful == false) {	
				System.out.print(prompt);
				
				transferAmount = moneyDecisions(prompt);
				
				if(checkTransferAmount(account, transferAmount) == true) {
					successful = true;
				}else {
					System.out.println("Please input a valid amount.");
					quit = quitDecision();
				}
			}
		}
		
		if(quit != true) {
			if(areYouSure())
				successful = bankSystem.transfer(account, accountToTransferTo, transferAmount);
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
	
	public boolean displayWithdraw(Account account) {
		boolean quit = false;
		boolean successful = false;
		double withdrawAmount = 0;
		String prompt = "How much would you like to withdraw?\nPlease input a number:";
		
		while(quit == false && successful == false) {
			System.out.println("WITHDRAW\n" + divider + "\n");
			account.displayBalance();
			
			withdrawAmount = moneyDecisions(prompt);
			
			if(withdrawAmount == -1) {
				quit = true;
				break;
			}
			
			if(checkWithdrawAmount(account, withdrawAmount) == true) {
				successful = true;
			}else {
				System.out.println("Please input a valid number.");
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			successful = bankSystem.withdraw(account, withdrawAmount);
//			successful = true;
		}
		
		return successful;
	}
	
	/////////////////////
	public boolean displayDeposit(Account account) {
		boolean successful = false;
		System.out.println("DEPOSIT\n" + divider + "\n");
		
		double depositAmount = addMoneyPrompt(account);
		
		if(depositAmount > 0.001) {
			successful = bankSystem.deposit(account, depositAmount);
		}
		
		return successful;
	}
	
	private double addMoneyPrompt(Account account) {
		boolean quit = false;
		boolean successful = false;
		
		double depositAmount = 0;
		String prompt = "How much would you like to add?\nPlease input a number:";
		
		while(quit == false && successful == false) {
			if(account.getId() != -1) { //not a new account
				System.out.println("DEPOSIT\n" + divider + "\n");
				account.displayBalance();
			}
			
			System.out.print(prompt);
			depositAmount = moneyDecisions(prompt);
			
			if(depositAmount == -1) {
				quit = true;
				break;
			}
			
			if(checkDepositAmount(account, depositAmount) == true) {
				successful = true;
			}else {
				System.out.println("Please input a valid number.");
				quit = quitDecision();
			}
		}
		
		if(quit == true)
			return -1;
		
		return depositAmount;
		
	}
}
