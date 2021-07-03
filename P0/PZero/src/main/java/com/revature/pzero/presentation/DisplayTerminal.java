package com.revature.pzero.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.service.BankSystemImpl;

public class DisplayTerminal {
	
	private Scanner scanner = new Scanner(System.in);
	private BankSystemImpl bankSystem;
	
	private String bankName = "[insert bank name here]";
	private String divider = "----------------------------------------";
	
	//REMOVE
	String u = "username";
	String p = "password";

	public DisplayTerminal() {
		bankSystem = new BankSystemImpl();
	}
	
	public void welcome() {
		
		System.out.println("Welcome to " + bankName + ".\n[0] - Login?\n[1] - Sign Up?\n[2] - Quit?");
		String userInput = scanner.next();
		
		while(true) {
			userInput = userInput.toLowerCase();
			if(userInput.equals("0")) {
				System.out.println("Login!");
				displayLogin();
				break;
			}else if(userInput.equals("1")) {
				System.out.println("Signup!");
				displaySignUp();
				break;
			}else if(userInput.equals("2")) {
				System.out.println("QUIT!");
				break;
			}else {
				System.out.println("Invalid input. Please input one of the following:");
				System.out.println("[0] - Login?\n[1] - Sign Up?\n[2] - Quit?");
				userInput = scanner.next();
			}
		}
		
		System.out.println("Welcome method: END");
	}
	
	public void displayLogin() {
		System.out.println("Please login:");
		String userInput = "";
		boolean quit = false;
		User u = null;
		
		while(quit == false) {
			u = checkLogin();
			if(!u.equals(null)) { break; } //if login is valid -> display user accounts
			quit = quitDecision(); //if login wasn't valid, check if they want to quit
		}	
		
		if(quit != true) {
			displayAccounts(u);
		}	
		
		System.out.println("Display Login: END");
	}
	
	private User checkLogin() {
		System.out.println("USERNAME: ");
		String username = scanner.next();
		System.out.println("PASSWORD: ");
		String password = scanner.next();
		
//		boolean userCheck = bankSystem.authenticateLoginUsername(username);
//		boolean passCheck = bankSystem.authenticateLoginPassword(password);
		
		boolean userCheck = username.equals(u);
		boolean passCheck = password.equals(p);
		
		if(userCheck == true && passCheck == true) {
			//return bankSystem.getUserViaLogin(username, password);
			return new User(1, "John", "Smith", "Customer");
		}
		
		if(userCheck == false){
			System.out.println("USERNAME failed. Please check to make sure you entered the correct username.");
		}else if(passCheck == false){
			System.out.println("PASSWORD failed. Please check to make sure you entered the correct password.");
		}
		
		return null;//return false;
	}
	
	
	public void displaySignUp() {
//		System.out.println("DISPLAY SIGNUP");
		User user = new User();
		boolean quit = false;
		boolean successful = false;
		
		//int id, String firstName, String lastName, String userType
		while(quit == false) {
			System.out.print("Please enter your first name: ");
			String firstName = scanner.next();
			System.out.print("Please enter your last name: ");
			String lastName = scanner.next();
			
			boolean nameDone = areYouSure();
			
			if(nameDone == true) {
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setUserType("Customer");
			}else {
				quit = quitDecision();
			}
			
			System.out.println("Please input a username.");
			String username = scanner.next();
			System.out.println("Please input a password.");
			String password = scanner.next();
			
			boolean done = areYouSure();
			
			if(done == true) {
				successful = bankSystem.register(user, username, password);
			}else {
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			System.out.println("Customer account successfully made.");
		}
	}
	
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
	
	public void displayAccounts(User user) {
		List<Account> listOfCustomerAccounts = bankSystem.getCustomerAccounts(user.getId());
		
		///////////////////////////////////////////////////// 
		listOfCustomerAccounts = new ArrayList<Account>();
		listOfCustomerAccounts.add(new Account(5, 239.31, "main account"));
		listOfCustomerAccounts.add(new Account(11, 1932.90, "Tom's savings account"));
		listOfCustomerAccounts.add(new Account(54, 0.0, "dead account"));
		/////////////////////////////////////////////////////
		
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
		}
		
		//prompt = "Accounts for " + user.getFirstName();
		System.out.println("Accounts for " + user.getFirstName() + " " + user.getLastName());
		System.out.println(divider);
		
		int i = 0;
		for(Account a : listOfCustomerAccounts) {
			System.out.println("[" + i++ + "]: " + a.toString());
		}
		//select an account
		
		boolean quit = false;
		int index = -1;
		while(quit == false) {
			System.out.println("Please select an account by typing in the number beside it.");
			String userInput = scanner.next();
			try {
				index = Integer.parseInt(userInput);
			}catch(Exception e) {
				System.out.println("Please input an integer.");
			}
			
			if(index > listOfCustomerAccounts.size() || index < 0) {
				System.out.println("Please input a valid integer.");
			}else {
				break;
			}
		}
		
		displayUserAccount(user, listOfCustomerAccounts.get(index));		
	}
	
	public void displayUserAccount(User user, Account account) {
		boolean quit = false;
		while(quit == false) {
			System.out.println(divider);
			account.displayBalance();
			System.out.println(divider + "\n");
			
			System.out.println("What would you like to do?");
			
			System.out.println("[0] Withdraw");
			System.out.println("[1] Deposit");
			System.out.println("[2] Transfer");
			System.out.println("[3] Change Accounts");
			System.out.println("[4] Create a new account");
			System.out.println("[5] Logout");
			
			String userInput = scanner.next();
			boolean success = false;
			
			switch(userInput) {
				case "0": //withdraw
					success = displayWithdraw(account);
					displayOptionStatus("WITHDRAW", success);
					break;
				case "1": //deposit
					success = displayDeposit(account);
					displayOptionStatus("DEPOSIT", success);
					break;
				case "2": //transfer
					success = displayTransfer(account);
					displayOptionStatus("TRANSFER", success);
					break;
				case "3": //change accounts
//					success = displayWithdraw(account);
					displayAccounts(user);
//					displayOptionStatus("CHANGE ACCOUNTS", success);
					break;
				case "4": //create a new account	
					//success = displayWithdraw(account);
					displayNewAccountCreation(user);
//					displayOptionStatus("CREATE A NEW ACCOUNT", success);
					break;
				case "5": //logout
					//success = displayWithdraw(account);
//					displayOptionStatus("LOGOUT", success);
					System.out.println("Thank you for using " + bankName);
					quit = true;
					break;
				default:
					System.out.println("Please input a valid option.");
					break;
			}	
		}
		
		welcome();
	}
	
	private void displayOptionStatus(String option, boolean success) {
		if(success == true)
			System.out.println(option + " successful."); //make sure balance is updated
		else
			System.out.println(option + " failed.");
	}
	
	private boolean areYouSure() {
		boolean yesOrNo = false;
		String prompt = "Are you sure?";
		return yesOrNoDecision(prompt);
	}
	
	public boolean displayTransfer(Account account) { 
		boolean quit = false;
		boolean successful = false;
		double transferAmount = 0;
		String userInput = "";
		int accountToTransfer = -1;
		
		while(quit == false) {
			System.out.println("TRANSFER\n" + divider + "\n");
			account.displayBalance();
			
			System.out.println("Please input an account you wish to transfer to.");
			userInput = scanner.next();
			
			try {
				accountToTransfer = Integer.parseInt(userInput);
				break;
			}catch(Exception e) {
				System.out.println("Please input a valid id.");
			}
			
			quit = quitDecision();
		}
			
		if(accountToTransfer != -1) {
			quit = false;
			while(quit == false) {	
				System.out.print("How much would you like to transfer?\nPlease input a number:");
				userInput = scanner.next();
				
				try {
					transferAmount = Double.parseDouble(userInput);
					successful = true;
					break;
				}catch(Exception e) {
					System.out.println("Please input a valid number.");
				}
				
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			if(areYouSure())
				successful = bankSystem.transfer(account, transferAmount);
		}
		return successful; 
	}
	
	private boolean verifyAccountID(int id) { 
		return bankSystem.verifyAccount(id); 
	}
	
	//////////////////////////////////////////////////////////
	public void displayLog() {
		System.out.println("DISPLAY LOGOUT");
	}
	
	public boolean displayWithdraw(Account account) {
		boolean quit = false;
		boolean successful = false;
		double withdrawAmount = 0;
		
		while(quit == false) {
			System.out.println("WITHDRAW\n" + divider + "\n");
			account.displayBalance();
			System.out.print("How much would you like to withdraw?\nPlease input a number:");
			String userInput = scanner.next();
			
			try {
				withdrawAmount = Double.parseDouble(userInput);
				successful = true;
				break;
			}catch(Exception e) {
				System.out.println("Please input a valid number.");
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			/////////////////////////////////////////////////////////
//			successful = bankSystem.withdraw(account, withdrawAmount);
			successful = true;
		}
		
		return successful;
	}
	
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
		
		double depositAmount = 0;
		
		while(quit == false) {
			if(account.getId() != -1) { //not a new account
				System.out.println("DEPOSIT\n" + divider + "\n");
				account.displayBalance();
			}
			System.out.print("How much would you like to add?\nPlease input a number:");
			String userInput = scanner.next();
			
			try {
				depositAmount = Double.parseDouble(userInput);
			}catch(Exception e) {
				System.out.println("Please input a valid number.");
			}
			
			quit = quitDecision();
		}
		
		if(quit == true)
			return -1;
		
		return depositAmount;
		
	}
	
	
	public boolean quitDecision() {
		boolean willQuit = true;
		System.out.println("Quit? [yes/no]");
		
		willQuit = yesOrNoDecision("Quit?");
		
		if(willQuit == true) {
			System.out.println("Thanks for using " + bankName + ".");
			return willQuit;
		}
		
		return willQuit;	
	}
	
	//  yes returns true | no returns false
	private boolean yesOrNoDecision(String prompt) {
		boolean yesOrNo = false;
		
		while(yesOrNo == false) {
			String userInput = scanner.next().toLowerCase();
			
			switch(userInput) {
				case "yes":
					yesOrNo = true;
					break;
				case "no":
					yesOrNo = false;
					break;
				default:
					System.out.println("Invalid input. Please input either [yes] or [no].\n" + prompt);
			}
		}
		return yesOrNo;
	}
	
}
