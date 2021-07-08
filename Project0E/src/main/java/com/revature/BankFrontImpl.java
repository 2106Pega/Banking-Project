package com.revature;

import java.util.List;
import java.util.Scanner;

public class BankFrontImpl implements BankFront{

	Scanner sc = new Scanner(System.in);
	BankService bs = new BankServiceImpl();


	public void displayDirections() {
		System.out.println("Please use the number pad to make your selection");
	}

	public void displayGreeting() {						
		System.out.println("Hello and welcome to Eberle Bank!");
		
	}

	public int getUserChoice(int numberOfChoices, int defaultInt) {// do unit test on this
		String userInput = sc.nextLine();
		if(isNumeric(userInput)) {
			int userInt = Integer.parseInt(userInput);
			if(userInt > 0 && userInt <= numberOfChoices) {
				return userInt;				
			}
		}
		displayDirections();
		return defaultInt;
	}

	public void displayMainMenu() {		
		System.out.println("1: Log in");
		System.out.println("2: Create an Account");
		System.out.println("3: Exit Eberle Bank");
	}

	public void displayFairwell() {
		System.out.println("Thank you for using Eberle Bank!");
		
	}

	public User logIn() {
		System.out.println("Please enter your username: "); //get username
		String username = getUserText();
		System.out.println("Please enter your password: "); //get password
		String password = getUserText();
		return bs.logIn(username, password); 
	}

	public void createAccount() {
		System.out.println("Thank you for choising to make an account with us!");
		System.out.println("We need a little information to get you started");
		boolean exit = false;
		while(!exit) {
			System.out.println("First name: ");
			String fn = getUserText();
			System.out.println("Last Name: ");
			String ln = getUserText();
			System.out.println("Desired username: ");
			String un = getUserText();
			System.out.println("Password: ");
			String pw = getUserText();
			System.out.println("Thank you. Is the following information correct?");
			System.out.println("First name: " + fn);
			System.out.println("Last name: " + ln);
			System.out.println("Username: " + un);
			System.out.println("Password: " + pw);
			System.out.println("1) Yes");
			System.out.println("2) No");
			System.out.println("3) Exit");
			int choice = getUserChoice(3, 0);
			if(choice == 1) {
				if(bs.testNewUser(un)) {
					bs.createNewUser(fn, ln, un, pw);
					exit = true;
				}
				else {
					System.out.println("I'm sorry, someone already has that username please choose another");
				}
				
			}else if(choice == 3){
				exit = true;
			}
		}

	}

	public void displayEmployeeChoices() {
		System.out.println("~~~Eberle Bank Employee Terminal~~~");
		System.out.println("Select operation:");
		System.out.println("1) View customer account information");
		System.out.println("2) View customer account request");
		System.out.println("3) Log out");		
	}

	public void displayCustomerChoices() {
		System.out.println("~~~Eberle Bank Accounts~~~");
		System.out.println("Please make a selection:");
		System.out.println("1) View Balance");
		System.out.println("2) Withdraw");
		System.out.println("3) Deposit");
		System.out.println("4) Transfer to new account");
		System.out.println("5) Log-out");
		
	}

	public void displayBalance(User u) {
		System.out.println("Your balance is: $" + u.getBalance());
		
	}

	public void displayWithdrawMenu(User u) {
		displayBalance(u);
		System.out.println("Withdraw Ammount: $");
		int ammount = getUserMoneyInput();
		bs.withdraw(u, ammount);
		displayBalance(u);
	}

	public void displayDepositMenu(User u) {
		displayBalance(u);
		System.out.println("Deposit Ammount: $");
		int ammount = getUserMoneyInput();
		bs.deposit(u, ammount);
		displayBalance(u);
		
	}

	public void displayTransferMenu(User u) {
		displayBalance(u);
		System.out.println("Account number to transfer to: ");
		int toAccount = getUserMoneyInput();
		System.out.println("Transfer Ammount: $");
		int ammount = getUserMoneyInput();
		bs.transfer(u, toAccount, ammount);
		displayBalance(u);
		
	}

	public void viewAccountMenu() {
		System.out.println("User Number: ");
		int userNumber = this.getUserMoneyInput();
		User n = new User();
		n.setUser_Id(userNumber);
		n = bs.logInbyId(n);
		System.out.println("Username: " + n.getUser_name());
		System.out.println("First Name: " + n.getFirst_name());
		System.out.println("Last Name: " + n.getLast_name());
		
	}

	public void pendingAccountsMenu() {
		System.out.println("The following is all unapproved accounts:");
		System.out.println("_________________________________________");
		List<User> userList = bs.getUnapproved();
		for(User u : userList) {
			System.out.println("Account name: " + u.getAccount_name());
		}
		System.out.println("_________________________________________");
		System.out.println("Would you like to approve all accounts?");
		System.out.println("1) Yes");
		System.out.println("2) No");
		int userChoice = this.getUserChoice(2, 2);
		switch (userChoice) {
		case 1:
			bs.approveAllAccounts();
			System.out.println("Accounts approved");
			break;
		case 2:
			System.out.println("No accounts approved");
			break;
		default:
			break;
		}
	}
	public String getUserText() {
		String userInput = sc.nextLine();
		userInput = userInput.replace("/", "");
		userInput = userInput.replace(",", "");
		userInput = userInput.replace("'", "");
		return userInput;
	}
	
	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public int getUserMoneyInput() {
		String userInput = getUserText();
		int i = 0;
		 try {
		        i = Integer.parseInt(userInput);
		    } catch (NumberFormatException nfe) {
		        return 0;
		    }
		 if(i >= 0) {
			 return i; 
		 }
		return 0;
	}
	

}
