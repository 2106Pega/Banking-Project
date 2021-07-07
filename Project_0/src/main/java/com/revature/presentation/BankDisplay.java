package com.revature.presentation;

import java.util.List;
import java.util.Scanner;

import com.revature.exceptions.CustomerIsNullException;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Transactions;
import com.revature.service.CustomerManager;
import com.revature.service.EmployeeManager;
import com.revature.service.TransactionManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class BankDisplay {
	
	Scanner sc = new Scanner(System.in);
	CustomerManager cM;
	EmployeeManager eM;
	TransactionManager tM;
	final static Logger Loggy = Logger.getLogger(BankDisplay.class);
	
	//Login Menu
	public void displayInitialLogin() {
		boolean selectedExit = false;
		do {
			System.out.println("Please select a number from the following options to login.");
			System.out.println("1) Customer");
			System.out.println("2) Employee");
			System.out.println("3) Exit");
			
			String userInput = sc.nextLine();
			switch(userInput) {
				case "1":
					customerInitial();
					break;
				case "2":
					employeeInitial();
					break;
				case "3":
					System.out.println("Exiting banking application.");
					selectedExit = true;
					break;
				default:
					System.out.println("Incorrect selection, please select again.");
			}
		}while(!selectedExit);		
	}
	
	//Customer Menus
	private void customerInitial() {
		boolean returnMenu = false;
		do {
			System.out.println("Hello Customer, are you a returning customer or would you like to open an account?");
			System.out.println("1) Returning Customer");
			System.out.println("2) New Customer");
			System.out.println("3) Return to Login Menu");
			String userInput = sc.nextLine();
			switch(userInput) {
				case "1":
					customerReturnLogin();
					break;
				case "2":
					registerForCustomerAccount();
					break;
				case "3":
					returnMenu = true;
					break;
				default:
					System.out.println("Incorrect selection, please select again.");
			}
		}while(!returnMenu);		
	}
	
	private void registerForCustomerAccount() {
		System.out.println("To register, you'll need to enter your name.");
		System.out.println("Please enter your first name:");
		String firstname = sc.nextLine();
		System.out.println("Please enter your last name:");
		String lastname = sc.nextLine();
		System.out.println("Please enter your Social Security Number:");
		int ssn = Integer.valueOf(sc.nextLine()).intValue();
		int accountNumber = cM.createCustomer(firstname, lastname, ssn);
		System.out.println("Your customer banking account number is: " + accountNumber);
	}

	private void customerReturnLogin() {
		boolean returnMenu = false;
		do {
			int accountNumber = enterAccountNumber();
			if(accountNumber == -1)
				returnMenu = true;
			else {
				if(cM.getCustomerAccount(accountNumber)) { //TODO
					customerReturnMainMenu();
				}
				else
					System.out.println("Account number not found, please enter another.");
			}
		}while(!returnMenu);	
	}
	
	private void customerReturnMainMenu() {
		Customer cust = cM.getCustomer();
		boolean returnMenu = false;
		double amount = 0;
		int accountNumber;
		do {
			System.out.println("Hello " + cust.getFirst_name() + " " + cust.getLast_name() + ", please select from the following options:");
			System.out.println("1) View Balance");
			System.out.println("2) Withdraw");
			System.out.println("3) Deposit");
			System.out.println("4) Post Money Transfer");
			System.out.println("5) Apply for new account");
			System.out.println("6) Return to Customer Login Menu");
			String userInput = sc.nextLine();
			switch(userInput) {
				case "1":
					customerViewBalance();
					break;
				case "2":
					accountNumber = enterAccountNumber();
					if(accountNumber == -1) {
						break;
					}
					else {
						amount = enterAmount("Withdraw");
						if(cM.withdrawAmount(accountNumber, amount)) {
							Loggy.info("Withdraw of " + amount + " from " + cust.getFirst_name() + " " 
										+ cust.getLast_name() + " account: #" + accountNumber);
							tM.createTransactionRecord(accountNumber, amount, "Withdraw");
						}
						else {
							System.out.println("Not enough funds to be withdrawn.");
							Loggy.error("LACK OF FUNDS: Withdraw of " + amount + " from " + cust.getFirst_name() + " " 
									+ cust.getLast_name() + " account: #" + accountNumber + " failed");
						}
					}
					break;
				case "3":
					accountNumber = enterAccountNumber();
					if(accountNumber == -1) {
						break;
					}
					else {
						amount = enterAmount("Deposit");
						if(cM.depositAmount(accountNumber, amount)) {
							Loggy.info("Deposit of " + amount + " to " + cust.getFirst_name() + " " 
										+ cust.getLast_name() + " account: #" + accountNumber);
							tM.createTransactionRecord(accountNumber, amount, "Deposit");
						}
						else {
							System.out.println("Error occurred upon deposit.");
							Loggy.error("ERROR: Deposit of " + amount + " to " + cust.getFirst_name() + " " 
									+ cust.getLast_name() + " account: #" + accountNumber + " failed.");
						}
					}
					break;
				case "4":
					System.out.println("Transfer from:");
					accountNumber = enterAccountNumber();
					if(accountNumber == -1) {
						break;
					}
					else {
						System.out.println("Transfer to:");
						int transferToNumber = enterAccountNumber();
						if(accountNumber == -1) {
							break;
						}
						else {
							amount = enterAmount("Transfer");
							if(cM.transferAmount(accountNumber, transferToNumber, amount)) { //TODO
								Loggy.info("Transfer of " + amount + " from " + cust.getFirst_name() + " " 
											+ cust.getLast_name() + " account: #" + accountNumber 
											+ " to account: #" + transferToNumber);
								tM.createTransactionRecord(accountNumber, amount, "Deposit", transferToNumber);
							}
							else {
								System.out.println("Error occurred upon transfer.");
								Loggy.error("ERROR: Transfer of " + amount + " from " + cust.getFirst_name() + " " 
										+ cust.getLast_name() + " account: #" + accountNumber 
										+ " to account: #" + transferToNumber + " failed.");
							}
							
						}
					}
					break;
				case "5": //TODO
					System.out.println("5) Apply for new account");
					amount = enterAmount("new account");
					if(amount != -1) {
						boolean loopAgain = true;
						do {
							System.out.println("Please select a number from the following options, for the bank account type:");
							System.out.println("1) Checking");
							System.out.println("2) Savings");
							System.out.println("3) Cancel");
							userInput = sc.nextLine();
							switch(userInput) {
								case "1":
									try {
										cM.accountApplication(amount, "Checking");
										Loggy.info("Application for checking account with $" + amount + " deposited from " + cust.getFirst_name() + " " 
												+ cust.getLast_name());
										tM.createTransactionRecord(amount, "Apply", "Checking");
									} catch (CustomerIsNullException e) {
										System.out.println("Customer account is missing. Please exit and reload customer account.");
										e.printStackTrace();
										Loggy.error("Applying for Checking Account: Customer Object was not saved within the Manager.");
									}
									break;
								case "2":
									try {
										cM.accountApplication(amount, "Savings");
										Loggy.info("Application for savings account with $" + amount + " deposited from " + cust.getFirst_name() + " " 
												+ cust.getLast_name());
										tM.createTransactionRecord(amount, "Apply", "Savings");
									} catch (CustomerIsNullException e) {
										System.out.println("Customer account is missing. Please exit and reload customer account.");
										e.printStackTrace();
										Loggy.error("Applying for Savings Account: Customer Object was not saved within the Manager.");
									}
									break;
								case "3":
									loopAgain = false;
									break;
								default:
									System.out.println("Incorrect selection, please select again.");
							}
						}while(loopAgain);
					}
					break;
				case "6":
					returnMenu = true;
					break;
				default:
					System.out.println("Incorrect selection, please select again.");
			}
		}while(!returnMenu);
	}
	
	private void customerViewBalance() {
		boolean returnMenu = false;
		do {
			int accountNumber = enterAccountNumber();
			
			if(accountNumber == -1)
				returnMenu = true;
			else {
				try {					
					if(cM.getBankAccount(accountNumber)) {
						System.out.println("Account #/t/tBalance");
						System.out.println(accountNumber + "/t/t" + cM.displayBalance(accountNumber));					
					}
					else {
						System.out.println("Account number not found, please enter another.");
						Loggy.warn("Account number " + accountNumber + " was not found.");
					}
				} catch (CustomerIsNullException e) {
					System.out.println("Customer account is missing. Please exit and reload customer account.");
					e.printStackTrace();
					Loggy.error("Customer Viewing Balance: Customer Object was not saved within the Manager.");
				}
			}
		}while(!returnMenu);			
	}

	//Employee Menus
	private void employeeInitial() {
		boolean returnMenu = false;
		do {
			System.out.println("Hello Employee, please select a number from the following options:");
			System.out.println("1) Review New Accounts");
			System.out.println("2) View Customer's Account");
			System.out.println("3) View all transactions");
			System.out.println("4) Return to Login Menu");
			String userInput = sc.nextLine();
			switch(userInput) {
				case "1":
					List<Account> applications = eM.reviewNewAccounts();
					approveAccounts(applications);
					break;
				case "2":
					int accountNumber = enterAccountNumber();
					List<Account> custBankAccounts = eM.viewCustomerAccounts(accountNumber);
					for(Account nextAccount : custBankAccounts) {
						nextAccount.toString();
					}
					break;
				case "3":
					viewAllTransactions();
					break;
				default:
					System.out.println("Incorrect selection, please select again.");
			}
		}while(!returnMenu);		
	}
	
	private void viewAllTransactions() {
		List<Transactions> allTransactions = tM.getAllTransactions();
		for(Transactions nextTransaction : allTransactions) {
			System.out.println(nextTransaction.toString());
		}
	}

	private void approveAccounts(List<Account> applications) {
		boolean cancelEarly = false;
		for(Account nextApp : applications){
			System.out.println("Hello Employee, do you approve or reject the following application:");
			cM.getCustomerAccount(nextApp.getCustomerID());
			Customer custTemp = cM.getCustomer();
			System.out.println("Customer Name: " + custTemp.getFirst_name() + " " + custTemp.getLast_name());
			System.out.println("Customer Id: " + custTemp.getBank_membership_number());
			System.out.println("Bank Account Type: " + nextApp.getType());
			System.out.println("Bank Balance: " + nextApp.getBalance());
			System.out.println("1) Approve");
			System.out.println("2) Reject");
			System.out.println("3) Cancel");
			String userInput = sc.nextLine();
			switch(userInput) {
				case "1":
					cM.createBankAccount(nextApp.getType(), nextApp.getBalance(), 
							custTemp.getBank_membership_number());
				case "2":
					eM.removeApplication(nextApp.getAccountID());
					break;
				case "3":
					cancelEarly = true;
					break;
				default:
					System.out.println("Incorrect selection, please select again.");
			}
			if(cancelEarly)
				break;
		}		
	}

	//General Menus	
	private int enterAccountNumber() {
		System.out.println("Please enter the account number or -1 to exit: ");
		String userInput = sc.nextLine();
			
		return Integer.valueOf(userInput).intValue();
	}
	
	private double enterAmount(String type) {
		boolean positiveAmount = false;
		double amount = -1;
		do {
			System.out.println("Please enter positive amount to " + type + " or -1 to exit: ");
			String userInput = sc.nextLine();
			amount = Double.valueOf(userInput).doubleValue();
			if(amount > 0 || amount == -1)
				positiveAmount = true;
			else
				System.out.println("Not a positive amount.");
		}while(!positiveAmount);	
		return amount;
	}
}
