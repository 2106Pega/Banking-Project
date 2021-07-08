package com.revature.presentation;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.MainDriver;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repo.AccountDAO;
import com.revature.repo.AccountDaoImpl;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDaoImpl;
import com.revature.service.BankManager;
import com.revature.service.BankManagerImpl;

public class BankTerminal {

	final static Logger loggy = Logger.getLogger(MainDriver.class);

	AccountDAO aDao = new AccountDaoImpl();
	UserDAO uDao = new UserDaoImpl();
	String username = null;

	BankManager bManager = new BankManagerImpl(aDao, uDao);
	//BankTerminal bTerminal = new BankTerminal();
	Scanner sc = new Scanner(System.in);

	public void displayWelcome() {

		System.out.println("Welcome to Revature Bank!");

		boolean validEntry = false;

		while (validEntry == false) {
			System.out.println("Are you a member? (yes/no)");

			String memberString = sc.nextLine();

			switch(memberString.toLowerCase()) {
			case "yes":
				System.out.println("\n");
				this.loginMenu();
				//System.out.println("login menu");
				validEntry = true;
				break;
			case "no":
				System.out.println("\n");
				this.registerMenu();
				//System.out.println("register menu");
				validEntry = true;
				break;
			default:
				System.out.println("Sorry that input is not valid. Please type yes or no.");
			}	
		}
	}

	private void registerMenu() {
		String newUser = null;
		String newPassword = null;
		String firstName = null;
		String lastName = null;
		int accountType = 0;

		System.out.println("Register Menu");

		boolean validUsername = false;

		System.out.println("Please Choose Your Username: ");

		while (validUsername == false) {

			newUser = sc.nextLine();

			if (bManager.authenticate(newUser) == true) {
				System.out.println("Username taken. Please choose a new username: ");
			}

			else validUsername = true;
		}

		boolean validPassword = false;
		System.out.println("Please Choose Your Password: ");

		while (validPassword == false) {		
			newPassword = sc.nextLine();
			validPassword = bManager.validPassword(newPassword);

			if (validPassword == false) {
				System.out.println("Password must be at least 6 characters. Please choose a new password: ");
			}
		}

		System.out.println("Please Enter Your First Name: ");
		firstName = sc.nextLine();

		System.out.println("Please Enter Your Last Name: ");
		lastName = sc.nextLine();

		System.out.println("Please Enter (1) to register as a CUSTOMER or (2) to register as an EMPLOYEE. ");

		boolean validEntry = false;
		while (validEntry == false) {

			String accountTypeString = sc.nextLine();

			switch(accountTypeString.toLowerCase()) {
			case "1":
				accountType = 1;
				validEntry = true;
				break;
			case "2":
				accountType = 2;
				validEntry = true;
				break;
			default:
				System.out.println("Sorry that input is not valid. Please Enter (1) to register as a CUSTOMER or (2) to register as an EMPLOYEE.");
			}
		}

		uDao.createNewUser(newUser, newPassword, firstName, lastName, accountType);

		loggy.info(newUser + " created a new account.");
		System.out.println("\n");

		System.out.println("Congratulations! You have successfully created an account!");
		System.out.println("\n");

		if (accountType == 1)
			this.customerMenu();
		else this.employeeMenu();
	}

	public void loginMenu() {

		System.out.println("Login Menu");
		System.out.println("Please Enter Your Username: ");

		username = sc.nextLine();
		System.out.println("\n");

		if (bManager.authenticate(username) == true) {

			System.out.println("Please Enter Your Password: ");
			String password = sc.nextLine();

			if (bManager.checkPassword(username, password)) {
				System.out.println("\n");
				System.out.println("Welcome " + username + "!");
				System.out.println("\n");

				if (bManager.checkAccountType(username) == 1)
					this.customerMenu();
				else this.employeeMenu();
			}

			else {
				System.out.println("Wrorng Password.");
				System.out.println("Enter 0 to try again.");
				System.out.println("Enter 1 to return to Welcome Menu.");

				boolean validEntry = false;
				while (validEntry == false) {
					String tryAgain = sc.nextLine();

					switch(tryAgain.toLowerCase()) {
					case "0":
						this.loginMenu();
						validEntry = true;
						break;
					case "1":
						this.displayWelcome();
						validEntry = true;
						break;
					default:
						System.out.println("Sorry that input is not valid. Please enter 0 or 1.");
					}
				}
			}
		}

		else {
			System.out.println("Invalid User.");
			System.out.println("Enter 0 to try again.");
			System.out.println("Enter 1 to return to Welcome Menu.");

			boolean validEntry = false;
			while (validEntry == false) {
				String tryAgain = sc.nextLine();
				System.out.println("\n");

				switch(tryAgain.toLowerCase()) {
				case "0":
					this.loginMenu();
					validEntry = true;
					break;
				case "1":
					this.displayWelcome();
					//System.out.println("register menu");
					validEntry = true;
					break;
				default:
					System.out.println("Sorry that input is not valid. Please enter 0 or 1.");
				}
			}
		}
	}

	public void customerMenu() {
		System.out.println("What would you like to do today?");

		boolean validEntry = false;

		while (validEntry == false) {

			System.out.println("Enter 1 to apply for a new bank account.");
			System.out.println("Enter 2 to view account balances.");
			System.out.println("Enter 3 to make a deposit.");
			System.out.println("Enter 4 to make a withdrawal.");
			System.out.println("Enter 5 to make a transfer.");
			System.out.println("Enter 0 to logout.");

			String userAction = sc.nextLine();
			System.out.println("\n");

			switch(userAction.toLowerCase()) {
			case "0":
				//this.logoutMenu();
				validEntry = true;
				break;

			case "1":
				boolean validBalance = false;

				while (validBalance == false) {

					System.out.println("Please enter your starting balance: ");

					String startingBalance = sc.nextLine();
					String checkedBalance = bManager.checkStartingBalance(startingBalance);

					if (checkedBalance == startingBalance) {
						bManager.applyBankAccount(username, Double.parseDouble(startingBalance));
						validBalance = true;
					}
					else System.out.println(checkedBalance);
				}

				loggy.info(username + " applied for a new bank account.");

				System.out.println("\n");
				System.out.println("Thank you! Your application is being reviewed.");

				validEntry = true;
				break;

			case "2":
				System.out.println("Account Information");
				User u = bManager.selectUser(username);

				boolean accountFound = false;

				while (accountFound == false) {
					int counter = 0;

					for (Account a : aDao.selectAccountsbyUser(u.getId())) {
						System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
						counter++;
					}
					System.out.println("\n");

					if (counter == 0) {
						System.out.println("No Active Accounts");
						System.out.println("\n");
						this.customerMenu();
					}

					System.out.println("Enter Account Number if you want to view that account specifically.");
					System.out.println("Enter 0 to return to the menu.");

					String accountInfo = sc.nextLine();
					System.out.println("\n");

					if (accountInfo.equals("0")) {
						this.customerMenu();
						accountFound = true;
					}

					for (Account a : aDao.selectAccountsbyUser(u.getId())) {

						if (Integer.toString(a.getAccountId()).equals(accountInfo)) {
							Account acc = aDao.selectAccountbyId(Integer.parseInt(accountInfo));
							System.out.println("Account Number: " + acc.getAccountId() + "   Balance: " + acc.getBalance());
							accountFound = true;
						}
					}

					if (accountFound == false) 
						System.out.println("Invalid account number.");
				}

				validEntry = true;
				break;

			case "3":

				boolean validDeposit = false;
				boolean validAccount = false;
				Account acc = new Account();
				User us = bManager.selectUser(username);

				while (validDeposit == false) {

					while (validAccount == false) {

						int counter = 0;
						for (Account a : aDao.selectAccountsbyUser(us.getId())) {
							counter++;
							System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
						}

						if (counter == 0) {
							System.out.println("No Active Accounts");
							System.out.println("\n");
							this.customerMenu();
						}

						System.out.println("\n");
						System.out.println("Enter the Account Number you would like to deposit to: ");

						String accountInfo = sc.nextLine();
						System.out.println("\n");

						//System.out.println(accountInfo);

						for (Account a : aDao.selectAccountsbyUser(us.getId())) {
							//System.out.println();
							if (Integer.toString(a.getAccountId()).equals(accountInfo)) {
								acc = aDao.selectAccountbyId(Integer.parseInt(accountInfo));
								validAccount = true;
							}
						}

						if (validAccount == false) 
							System.out.println("Invalid account number.");
					}


					System.out.println("How much would you like to deposit?");

					String depositAmount = sc.nextLine();
					String checkedBalance = bManager.checkStartingBalance(depositAmount);

					if (checkedBalance == depositAmount) {
						aDao.updateAccount(acc.getAccountId(), Double.parseDouble(depositAmount));
						validDeposit = true;
					}
					else System.out.println(checkedBalance);
				}

				loggy.info(username + " made a deposit.");
				System.out.println("\n");
				System.out.println("Thank you for your deposit!");

				validEntry = true;
				break;

			case "4":

				boolean validWithdraw = false;
				validAccount = false;
				Account account = new Account();
				us = bManager.selectUser(username);

				while (validWithdraw == false) {

					while (validAccount == false) {

						int counter = 0;

						for (Account a : aDao.selectAccountsbyUser(us.getId())) {
							System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
							counter++;
						}

						System.out.println("\n");

						if (counter == 0) {
							System.out.println("No Active Accounts");
							System.out.println("\n");
							this.customerMenu();
						}

						System.out.println("Enter the Account Number you would like to withdraw from:");

						String accountInfo = sc.nextLine();

						System.out.println("\n");

						for (Account a : aDao.selectAccountsbyUser(us.getId())) {
							//System.out.println();
							if (Integer.toString(a.getAccountId()).equals(accountInfo)) {
								account = aDao.selectAccountbyId(Integer.parseInt(accountInfo));
								validAccount = true;
							}
						}

						if (validAccount == false) 
							System.out.println("Invalid account number.");
					}


					System.out.println("How much would you like to withdraw?");

					String depositAmount = sc.nextLine();
					String checkedBalance = bManager.checkStartingBalance(depositAmount);

					if (checkedBalance == depositAmount) {
						//System.out.println(Double.parseDouble(depositAmount)+ account.getBalance());
						if (Double.parseDouble(depositAmount) <= account.getBalance()) {
							aDao.updateAccount(account.getAccountId(), 0 - Double.parseDouble(depositAmount));
							validWithdraw = true;
						}
						else System.out.println("Not enough money.");

					}
					else System.out.println(checkedBalance);
				}

				loggy.info(username + " made a withdrawal.");
				System.out.println("\n");
				System.out.println("Thank you for your withdrawal!");

				validEntry = true;
				break;

			case "5":

				boolean validTransfer = false;
				boolean validAccountFrom = false;
				boolean validAccountTo = false;
				Account transferFrom = new Account();
				Account transferTo = new Account();

				User userTransferring = bManager.selectUser(username);

				while (validTransfer == false) {

					while (validAccountFrom == false) {

						int counter = 0;

						for (Account a : aDao.selectAccountsbyUser(userTransferring.getId())) {
							System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
							counter++;
						}

						if (counter == 0) {
							System.out.println("No Active Accounts");
							System.out.println("\n");
							this.customerMenu();
						}

						System.out.println("\n");

						System.out.println("Enter the Account Number you would like to transfer from:");

						String transFrom = sc.nextLine();
						System.out.println("\n");

						for (Account a : aDao.selectAccountsbyUser(userTransferring.getId())) {
							//System.out.println();
							if (Integer.toString(a.getAccountId()).equals(transFrom)) {
								transferFrom = aDao.selectAccountbyId(Integer.parseInt(transFrom));
								validAccountFrom = true;
							}
						}

						if (validAccountFrom == false) 
							System.out.println("Invalid account number.");
					}

					while (validAccountTo == false) {

						//						for (Account a : aDao.selectAllAccounts())
						//							System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
						//						System.out.println("\n");

						System.out.println("Enter the Account Number you would like to transfer to:");

						String transTo = sc.nextLine();
						System.out.println("\n");

						for (Account a : aDao.selectAllAccounts()) {

							if (Integer.toString(a.getAccountId()).equals(transTo) && transTo.equals(Integer.toString(transferFrom.getAccountId())) == false) {
								transferTo = aDao.selectAccountbyId(Integer.parseInt(transTo));
								validAccountTo = true;
							}
						}

						if (validAccountTo == false) 
							System.out.println("Invalid account number.");
					}

					System.out.println("How much would you like to transfer?");

					String transferAmount = sc.nextLine();
					String checkedBalance = bManager.checkStartingBalance(transferAmount);

					if (checkedBalance == transferAmount) {
						if (Double.parseDouble(transferAmount) <= transferFrom.getBalance()) {
							aDao.updateAccount(transferFrom.getAccountId(), 0 - Double.parseDouble(transferAmount));
							aDao.updateAccount(transferTo.getAccountId(), Double.parseDouble(transferAmount));

							validTransfer = true;
						}
						else System.out.println("Not enough money.");
					}
					else System.out.println(checkedBalance);
				}

				loggy.info(username + " made a transfer.");
				System.out.println("\n");
				System.out.println("Transfer successful!");

				validEntry = true;
				break;

			default:
				System.out.println("Sorry that input is not valid.");
			}

			boolean exit = false;

			while (exit == false) {

				System.out.println("\n");
				System.out.println("Enter 1 to return to the menu.");
				System.out.println("Enter 0 to logout.");

				userAction = sc.nextLine();
				System.out.println("\n");

				switch(userAction.toLowerCase()) {
				case "0":
					exit = true;
					this.logoutMenu();
					break;

				case "1":
					this.customerMenu();
					exit = true;
					break;

				default:
					System.out.println("Sorry that input is not valid.");
				}
			}
		}
	}

	public void employeeMenu() {

		System.out.println("What would you like to do today?");

		boolean validEntry = false;

		while (validEntry == false) {

			System.out.println("Enter 1 to view account applications.");
			System.out.println("Enter 2 to approve or deny pending applications.");
			System.out.println("Enter 3 to view a customer's accounts.");
			System.out.println("Enter 4 to view transactions log.");
			System.out.println("Enter 0 to logout.");

			String userAction = sc.nextLine();
			System.out.println("\n");

			switch(userAction.toLowerCase()) {
			case "0":
				//this.logoutMenu();
				validEntry = true;
				break;
			case "1":
				System.out.println("Pending Applications");

				for (Account a : aDao.selectAllApplications())
					System.out.println("Application Number: " + a.getApplicationId() + "   Name: " + a.getFirstName() + " " + a.getLastName() + "   Starting Balance: " + Double.toString(a.getBalance()));
				System.out.println("\n");

				validEntry = true;
				break;

			case "2":
				System.out.println("Pending Applications");

				boolean validDecision = false;
				while (validDecision == false) {

					System.out.println("Enter 1 to approve a pending application.");
					System.out.println("Enter 2 to deny a pending application.");
					System.out.println("Enter 3 to approve all pending applications.");
					System.out.println("Enter 4 to deny all pending applications.");

					Account appStatus = new Account();

					String applicationDecision = sc.nextLine();
					System.out.println("\n");

					switch(applicationDecision.toLowerCase()) {

					case "1":
						boolean accountFound = false;

						while (accountFound == false) {
							for (Account a : aDao.selectAllApplications())
								System.out.println("Application Number: " + a.getApplicationId() + "   Name: " + a.getFirstName() + " " + a.getLastName() + "   Starting Balance: " + Double.toString(a.getBalance()));
							System.out.println("\n");
							System.out.println("Enter the application number you would like to approve: ");

							String applicationApprove = sc.nextLine();
							System.out.println("\n");

							for (Account a : aDao.selectAllApplications()) {
								if (Integer.toString(a.getApplicationId()).equals(applicationApprove)) {
									bManager.approveBankAccount(a.getUsername(), a.getBalance());
									appStatus = a;
									accountFound = true;
								}
							}

							if (accountFound == false) 
								System.out.println("Invalid application number.");
						}

						aDao.deleteApplication(appStatus);
						System.out.println("Application Approved!");
						
						validDecision = true;
						break;

					case "2":
						accountFound = false;

						while (accountFound == false) {
							for (Account a : aDao.selectAllApplications())
								System.out.println("Application Number: " + a.getApplicationId() + "   Name: " + a.getFirstName() + " " + a.getLastName() + "   Starting Balance: " + Double.toString(a.getBalance()));
							System.out.println("\n");
							System.out.println("Enter the application number you would like to deny: ");

							String applicationDeny = sc.nextLine();
							System.out.println("\n");

							for (Account a : aDao.selectAllApplications()) {
								if (Integer.toString(a.getApplicationId()).equals(applicationDeny)) {
									aDao.deleteApplication(a);
									accountFound = true;
								}
							}

							if (accountFound == false) 
								System.out.println("Invalid application number.");
						}
						
						System.out.println("Application Denied!");
						
						validDecision = true;
						break;

					case "3":
						for (Account a : aDao.selectAllApplications()) {
							bManager.approveBankAccount(a.getUsername(), a.getBalance());
							aDao.deleteApplication(a);
						}
						
						System.out.println("All Applications Approved!");
						validDecision = true;
						break;

					case "4":
						for (Account a : aDao.selectAllApplications())
							aDao.deleteApplication(a);
						
						System.out.println("All Application Denied!");
						validDecision = true;
						break;

					default:
						System.out.println("Sorry that input is not valid.");
					}
				}
				validEntry = true;
				break;

			case "3":
				boolean accountFound = false;

				while (accountFound == false) {
					for (User u : uDao.selectAllUsers())
						System.out.println("User ID: " + u.getId() + "   Name: " + u.getFirstName() + " " + u.getLastName());
					System.out.println("\n");
					System.out.println("Enter the User ID of the user account you would like to view: ");

					String userSelect = sc.nextLine();
					System.out.println("\n");

					for (User u : uDao.selectAllUsers()) {
						if (Integer.toString(u.getId()).equals(userSelect)) {
							System.out.println("Name: " + u.getFirstName() + " " + u.getLastName());
							for (Account a : aDao.selectAccountsbyUser(u.getId())) 
								System.out.println("Account Number: " + a.getAccountId() + "   Balance: " + a.getBalance());
							System.out.println("\n");
							accountFound = true;
						}
					}

					if (accountFound == false) 
						System.out.println("Invalid application number.");
				}

				validEntry = true;
				break;

			case "4":
				System.out.println("Transactions Log: ");
				System.out.println(bManager.readLog());

				validEntry = true;
				break;

			default:
				System.out.println("Sorry that input is not valid.");
			}

			validEntry = false;

			while (validEntry == false) {
				System.out.println("\n");
				System.out.println("Enter 1 to return to the menu.");
				System.out.println("Enter 0 to logout.");

				userAction = sc.nextLine();
				System.out.println("\n");

				switch(userAction.toLowerCase()) {
				case "0":
					this.logoutMenu();
					validEntry = true;
					break;

				case "1":
					this.employeeMenu();
					validEntry = true;
					break;

				default:
					System.out.println("Sorry that input is not valid.");
				}
			}
		}
	}

	public void logoutMenu() {
		System.out.println("Thank you for using Revature Bank!");
		sc.close();
		System.exit(0);
	}
}
