package com.revature.presentation;

import com.revature.repository.BankDaoImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.service.BankApp;
import com.revature.service.BankAppImpl;

public class BankUiImpl implements BankUi {
	
	private static BankApp app;
	private static BankUi ui = new BankUiImpl();
	private static Scanner scan;
	
	private static final String EMPLOYEE_CODE = "Th1515th33mpl0y33C0D3!";
	private static final Logger loggy = Logger.getLogger(BankUiImpl.class);
	
	public BankUiImpl() {
		this.app = new BankAppImpl(new BankDaoImpl());
		this.scan = new Scanner(System.in);
	}
	
	/**
	 * Displays a menu greeting the user and asking them whether they are an employee
	 * 	or customer. Depending on the user's response, it will either take them to the
	 * 	customer or employee login.
	 */
	@Override
	public void displayWelcomeMenu() {
		
		System.out.println("Welcome to the bank app!");
		System.out.println("Are you a customer (1) or an employee (2)?");
		
		while (true) {
		
			String userChoice = scan.nextLine();
		
			switch(userChoice) {
		
			case "1":
				customerBranch();
				break;
			case "2":
				employeeBranch();
				break;
			default:
				System.out.println("Invalid input. Please enter 1 or 2.");
		
			}
		
		}
		
	}

	/**
	 * Asks the user if they are a new or old customer. This determines whether the
	 * 	user will be asked to create a new account or simply to log in.
	 * @return	true when user exits the branch
	 */
	private static boolean customerBranch() {
		
		while (true) {
			
			System.out.println("Do you want to create a new account (1) or sign in with an existing one (2)?");
			System.out.println("(Press x to go back)");
			String customerChoice = scan.nextLine();
			
			switch (customerChoice) {
			
			case "1":
				ui.registerForCustomerAccount();
				break;
			case "2":
				customerLogin();
				break;
			case "x":
				System.out.println("Welcome to the bank app!");
				System.out.println("Are you a customer (1) or an employee (2)?");
				return true;
			default:
				System.out.println("Error: Please enter 1 or 2.");
			
			}
		}
		
	}
	
	/**
	 * Asks the user if they are a new or current employee. This determines whether the
	 * 	user will be asked to create a new account or simply to log in.
	 * @return	true when user exits the branch
	 */
	private static boolean employeeBranch() {
		
		while (true) {
			
			System.out.println("Are you a new employee (1) or a current one (2)?");
			System.out.println("(Press x to go back)");
			String employeeChoice = scan.nextLine();
			
			switch (employeeChoice) {
			
			case "1":
				ui.registerForEmployeeAccount();
				break;
			case "2":
				employeeLogin();
				break;
			case "x":
				System.out.println("Welcome to the bank app!");
				System.out.println("Are you a customer (1) or an employee (2)?");
				return true;
			default:
				System.out.println("Error: Please enter 1 or 2.");
			
			}
		}
		
	}
	
	private static boolean customerLogin() {
		
		System.out.println("Please enter your login information (enter x to exit).\n");
		
		while (true) {
		
		System.out.print("Username: ");
		String username = scan.nextLine();
		
		if (username.equals("x")) {
			return false;
		}
			
		System.out.print("Password: ");
		String password = scan.nextLine();
			
		System.out.println("");
		// Search for unique username and validate password
			
		try {
			if (app.validateCustomerLogin(username, password)) {
				System.out.println("Welcome back, " + username + "!");
				customerMenu(username);
			} else {
				System.out.println("Invalid login. Please try again.\n");
				continue;
			}
		} catch (NoSuchAccountException e) {
			System.out.println("Account not found!\n");
			continue;
		}
		
		return false;
		
		}
		
	}
	
	/**
	 * 
	 */
	private static boolean employeeLogin() {

		System.out.println("Please enter your login information (enter x to exit).\n");
		
		while (true) {
		
		System.out.print("Username: ");
		String username = scan.nextLine();
		
		if (username.equals("x")) {
			return false;
		}
			
		System.out.print("Password: ");
		String password = scan.nextLine();
			
		System.out.println("");
		// Search for unique username and validate password
			
		try {
			if (app.validateEmployeeLogin(username, password)) {
				System.out.println("Welcome back, " + username + "!");
				employeeMenu();
			} else {
				System.out.println("Invalid login. Please try again.");
				continue;
			}
		} catch (NoSuchAccountException e) {
			System.out.println("Account not found!");
			continue;
		}
		
		return false;
		
		}

	}

	/**
	 * Presents a menu to the customer with six options (apply for bank account, view balance,
	 * 	deposit, withdraw, transfer, and logout) and asks them to input a number corresponding
	 * 	to one of the options.
	 * @param	username - the account username
	 */
	private static void customerMenu(String username) {
		
		boolean stillGoing = true;
		
		while (stillGoing) {
			
			System.out.println("\nPlease pick an option:");
			System.out.println("\t1. Apply for a new bank account");
			System.out.println("\t2. View balance in existing account");
			System.out.println("\t3. Make a deposit");
			System.out.println("\t4. Make a withdrawal");
			System.out.println("\t5. Transfer money from one account to another");
			System.out.println("\t6. Log out");
			
			String customerChoice = scan.nextLine();
		
			switch (customerChoice) {
		
			case "1":
				ui.applyForNewBankAccount(username);
				break;
			case "2":
				ui.viewBalance(username);
				break;
			case "3":
				ui.deposit(username);
				break;
			case "4":
				ui.withdraw(username);
				break;
			case "5":
				ui.transferMoney(username);
				break;
			case "6":
				System.out.println("\nHave a nice day!\n");
				ui.displayWelcomeMenu();
				break;
			default:
				System.out.println("Error: Please enter an integer 1 through 6");
		
			}
		}
		
	}
	
	/**
	 * Presents a menu to the employee with four options (approve/reject bank account, view
	 * 	a customer's bank accounts, view transaction log, and logout) and asks them to input
	 * 	a number corresponding to one of the options.
	 */
	private static void employeeMenu() {
		
		boolean stillGoing = true;
		
		while (stillGoing) {
			
			System.out.println("Please pick an option:");
			System.out.println("\t1. Approve or reject a customer's account application");
			System.out.println("\t2. View a customer's bank accounts");
			System.out.println("\t3. View transaction log");
			System.out.println("\t4. Log out");
			
			String employeeChoice = scan.nextLine();
		
			switch (employeeChoice) {
		
			case "1":
				ui.validateNewAccount();
				break;
			case "2":
				ui.viewCustomerAccounts();
				break;
			case "3":
				ui.viewTransactionLog();
				break;
			case "4":
				System.out.println("\nHave a nice day!\n");
				ui.displayWelcomeMenu();
				break;
			default:
				System.out.println("Error: Please enter an integer 1 through 4");
		
			}
		}
		
	}
	
	/**
	 * Allows the user to register for a customer account. The user needs to do this before
	 * 	they can apply for any bank accounts or access any of the bank's functions in general.
	 * @return	true if user successfully creates account, false if user exits
	 */
	@Override
	public boolean registerForCustomerAccount() {
		
		System.out.println("\nGreat! Please enter the following information for us (enter x to exit).");
		
		while (true) {
			
			System.out.print("\nUsername: ");
			String username = scan.nextLine();
		
			if (username.equals("x")) {
				System.out.println("");
				return false;
			}
		
			if (username.length() < 6) {
				System.out.println("Username must be 6 or more characters.");
				continue;
			}
			
			System.out.print("Password: ");
			String password = scan.nextLine();
		
			if (password.length() < 8) {
				System.out.println("Password must be 8 or more characters.");
				continue;
			}
			
			try {
				app.createNewCustomerAccount(username, password);
			} catch (UsernameAlreadyExistsException e) {
				System.out.println("\nUsername already exists; please try again.");
				continue;
			}
			
			System.out.println("\nYour account has been created!");
			return true;
		
		}
		
	}
	
	/**
	 * Allows the user to register for an employee account. Unlike when registering for customer
	 * 	accounts, the prospective employee will need to know and enter the employee code to make
	 * 	their own account.
	 * @return 	true if user creates account, false if user exits or doesn't know the code
	 */
	@Override
	public boolean registerForEmployeeAccount() {
		
		System.out.println("What's the passcode?");
		String employeePasscode = scan.nextLine();
		
		if (!employeePasscode.equals(EMPLOYEE_CODE)) {
			System.out.println("Nice try, but you're not an employee.");
			return false;
		}
		
		System.out.println("\nPlease enter the following information for us (enter x to exit).");
		
		while (true) {
			
			System.out.print("\nUsername: ");
			String username = scan.nextLine();
		
			if (username.equals("x")) {
				System.out.println("");
				return false;
			}
		
			if (username.length() < 6) {
				System.out.println("Username must be 6 or more characters.");
				continue;
			}
			
			System.out.print("Password: ");
			String password = scan.nextLine();
		
			if (password.length() < 8) {
				System.out.println("Password must be 8 or more characters.");
				continue;
			}
			
			try {
				app.createNewEmployeeAccount(username, password);
			} catch (UsernameAlreadyExistsException e) {
				System.out.println("\nUsername already exists; please try again.");
				continue;
			}
			
			System.out.println("\nYour account has been created!");
			return true;
		
		}
		
	}

	/**
	 * Allows a customer to apply for a new bank account. The new bank account will be
	 * 	stored in an "unvalidated" state until it is validated by an employee; until
	 * 	then, the customer still will not be able to access its functionalities.
	 * @param 	username - the customer's username
	 */
	@Override
	public void applyForNewBankAccount(String username) {
		
		System.out.print("Input starting balance: $");
		double startingBalance = Double.parseDouble(scan.nextLine());
		int id = -1;
		
		try {
			id = app.getCustomerIdFromUsername(username);
			app.createNewBankAccount(startingBalance, id);
			System.out.println("Thank you! Your application will be reviewed shortly.");
		} catch (NoSuchAccountException e) {
			System.out.println("Account does not exist!");
		}
		
	}

	/**
	 * Allows the user to view the current balance in one of their accounts
	 * @param 	username - the customer's username
	 * @return	true if user successfully views balance, false if user exits or has no accounts
	 */
	@Override
	public boolean viewBalance(String username) {
		
		List<Integer> idList;
		
		try {
			idList = app.getBankAccountIds(username);
			
			if (idList.isEmpty()) {
				System.out.println("You don't have any accounts set up yet!");
				return false;
			}
		} catch (NoSuchAccountException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Here are the accounts associated with your login:");
		for (int id: idList) {
			System.out.println("\tAccount" + id);
		}
		
		int accountNumber = -1;
		
		while (!idList.contains(accountNumber)) {
			
			System.out.println("Which account number would you like to view?");
			System.out.print("Enter the ID (or x to exit): ");
			
			try {
				String line = scan.nextLine();
				
				if (line.equals("x")) {
					System.out.println("");
					return false;
				}
				
				accountNumber = Integer.parseInt(line);
				
				if (idList.contains(accountNumber)) {
					double balance = app.getBalance(accountNumber);
					System.out.println("Your balance is $" + balance);
					return true;
				} else {
					System.out.println("Invalid account ID. Please try again.\n");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer!\n");
			} catch (NoSuchAccountException e) {
				System.out.println("Account does not exist!\n");
			}
		
		}
		
		return false;
		
	}

	/**
	 * Allows a customer to make a deposit of money into an account of their choice.
	 * @param 	username - the customer's username
	 * @return	true if deposit is completed, false if user exits or has no accounts
	 */
	@Override
	public boolean deposit(String username) {
		
		List<Integer> idList;
		
		try {
			idList = app.getBankAccountIds(username);
			
			if (idList.isEmpty()) {
				System.out.println("You don't have any accounts set up yet!");
				return false;
			}
		} catch (NoSuchAccountException e) {
			e.printStackTrace();
			return false;
		}
		
		
		System.out.println("Here are the accounts associated with your login:");
		for (int id: idList) {
			System.out.println("\tAccount #" + id);
		}
		
		int accountNumber = -1;
		
		while (!idList.contains(accountNumber)) {
			
			try {
				
				System.out.print("Enter the account ID (or x to exit): ");
				String line = scan.nextLine();
				
				if (line.equals("x")) {
					System.out.println("");
					return false;
				}
				
				accountNumber = Integer.parseInt(line);
				
				System.out.println("How much would you like to deposit?");
				double amount = Double.parseDouble(scan.nextLine());
				
				if (idList.contains(accountNumber)) {
					app.makeDeposit(accountNumber, amount);
					System.out.println("Your deposit of " + amount + " into account " + accountNumber + " was successful.\n");
					loggy.info("User '" + username + "' deposited $" + amount + " to account " + accountNumber +".");
					return true;
				} else {
					System.out.println("Invalid account ID. Please try again.\n");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer!\n");
			} catch (BadInputException e) {
				System.out.println("Amount cannot be negative or zero.\n");
			} catch (NoSuchAccountException e) {
				System.out.println("Account not found!\n");
			}
		
		}
		
		return false;
		
	}

	/**
	 * Allows a customer to withdraw money from an account of their choice.
	 * @param 	username - the customer's username
	 * @return 	true if withdrawal is completed, false if user exits or has no accounts
	 */
	@Override
	public boolean withdraw(String username) {
		
		List<Integer> idList;
		
		try {
			idList = app.getBankAccountIds(username);
			
			if (idList.isEmpty()) {
				System.out.println("You don't have any accounts set up yet!");
				return false;
			}
		} catch (NoSuchAccountException e) {
			e.printStackTrace();
			return false;
		}
		
		
		System.out.println("Here are the accounts associated with your login:");
		for (int id: idList) {
			System.out.println("\tAccount #" + id);
		}
		
		int accountNumber = -1;
		
		while (true) {
			
			try {
				
				System.out.print("Enter the account ID (or x to exit): ");
				String line = scan.nextLine();
				
				if (line.equals("x")) {
					System.out.println("");
					return false;
				}
				
				accountNumber = Integer.parseInt(line);
				
				System.out.print("How much would you like to withdraw? $");
				double amount = Double.parseDouble(scan.nextLine());
				
				if (idList.contains(accountNumber)) {
					app.makeWithdrawal(accountNumber, amount);
					System.out.println("Your withdrawal of " + amount + " from account " + accountNumber + " was successful\n");
					loggy.info("User '" + username + "' withdrew $" + amount + " from account " + accountNumber +".");
					return true;
				} else {
					System.out.println("You don't have access to that account. Please try again.\n");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer!\n");
			} catch (BadInputException e) {
				System.out.println("Amount cannot be negative or zero.\n");
			} catch (NoSuchAccountException e) {
				System.out.println("Account not found!\n");
			} catch (OverdrawnException e) {
				System.out.println("Error: You don't have enough money in that account!\n");
			}
		
		}
		
	}
	
	/**
	 * Allows a customer to transfer money from one account to another (including to accounts
	 * 	belonging to other users).
	 * @param 	username - the customer's username
	 * @return 	true if transfer is completed, false if user exits or has no accounts
	 */
	@Override
	public boolean transferMoney(String username) {

		List<Integer> idList;
		List<Integer> allIds = app.getBankAccountIds();
		
		try {
			idList = app.getBankAccountIds(username);
			
			if (idList.isEmpty()) {
				System.out.println("You don't have any accounts set up yet!");
				return false;
			}
		} catch (NoSuchAccountException e) {
			e.printStackTrace();
			return false;
		}
		

		System.out.println("Here are the accounts associated with your login:");
		for (int id: idList) {
			System.out.println("Account #" + id);
		}
		
		int accountNumber1 = -1;
		int accountNumber2 = -1;
		
		while (true) {
			
			try {
				
				System.out.print("Enter the ID for the account you want to transfer from (or x to exit): ");
				String line = scan.nextLine();
				
				if (line.equals("x")) {
					System.out.println("");
					return false;
				}
				
				accountNumber1 = Integer.parseInt(line);
				
				System.out.print("Enter the ID for the account you want to transfer to: ");
				accountNumber2 = Integer.parseInt(scan.nextLine());
				
				System.out.print("How much would you like to transfer? $");
				double amount = Double.parseDouble(scan.nextLine());
				
				if (idList.contains(accountNumber1)) {
					app.makeTransfer(accountNumber1, accountNumber2, amount);
					System.out.println("Your transfer of " + amount + " from account " + accountNumber1
							+ " to " + accountNumber2 + " was successful.\n");
					loggy.info("User '" + username + "' transferred $" + amount + " from account " 
							+ accountNumber1 + " to account " + accountNumber2 + ".");
					return true;
				} else {
					System.out.println("You don't have access to the first account. Please try again.\n");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer!\n");
			} catch (BadInputException e) {
				System.out.println("Amount cannot be negative or zero.\n");
			} catch (NoSuchAccountException e) {
				System.out.println("One of the accounts could not be found.\n");
			} catch (OverdrawnException e) {
				System.out.println("Error: You don't have enough money in that account!\n");
			}
		
		}
		
	}

	/**
	 * Allows an employee to approve or reject proposed customer bank accounts. If there are accounts
	 * 	awaiting validation, the employee can choose whether to approve it, in which the account will
	 * 	be ready for the customer to use, or reject it, in which case the account will be deleted.
	 * @return	true if unvalidated accounts are found, false if user exits or accounts are not found
	 */
	@Override
	public boolean validateNewAccount() {
		
		List<Integer> idList = app.getUnvalidatedAccountIds();
		
		if (idList.isEmpty()) {
			System.out.println("There are currently no accounts awaiting validation.\n");
			return false;
		}
		
		System.out.println("The following accounts are awaiting your approval: ");

		for (int id: idList) {
			System.out.println("\tAccount #" + id);
		}
		
		int accountNumber = -1;
		
		while (!idList.contains(accountNumber)) {
			
			try {
				
				System.out.println("Select an ID to make a decision (or x to exit).\n");
				String line = scan.nextLine();
				
				if (line.equals("x")) {
					System.out.println("");
					return false;
				}
				
				accountNumber = Integer.parseInt(line);
				
				if (idList.contains(accountNumber)) {
					
					System.out.println("Would you like to approve (1) or reject (2) this account?\n");
					String userChoice = scan.nextLine();
					
					switch (userChoice) {
					
					case "1":
						app.approveAccount(accountNumber);
						System.out.println("Account " + accountNumber + " has been approved.\n");
						break;
					case "2":
						app.rejectAccount(accountNumber);
						System.out.println("Account " + accountNumber + " has been rejected.\n");
						break;
					default:
						System.out.println("Error: Please enter 1 or 2.\n");
					
					}
				} else {
					System.out.println("Invalid account ID. Please try again.\n");
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Enter an integer!\n");
			} catch (NoSuchAccountException e) {
				System.out.println("Account does not exist!\n");
			}
		
		}
		
		return true;
		
	}

	/**
	 * Prompts the employee to enter a customer username before printing out a list of
	 * 	accounts associated with that username for them to view. The list includes the
	 * 	ID number for each account as well as the balance currently in the account.
	 * @return	true if accounts are found, false if not or if the user exits
	 */
	@Override
	public boolean viewCustomerAccounts() {
		
		System.out.println("Enter a customer username (or x to exit):");
		String username = scan.nextLine();
		
		if (username.equals("x")) {
			System.out.println("");
			return false;
		}
		
		try {
			List<Integer> idList = app.getBankAccountIds(username);
			
			if (idList.isEmpty()) {
				System.out.println("This customer has no bank accounts set up.\n");
				return false;
			}
			
			System.out.println("\nHere are the bank accounts associated with that username:");
			
			for (int i: idList) {
				System.out.println("\tAccount: " + i + " Balance: $" + app.getBalance(i));
			}
			System.out.println("");
			
		} catch (NoSuchAccountException e) {
			System.out.println("Account not found!\n");
			return false;
		}
		
		return true;
		
	}

	/**
	 * Allows an employee to view a log of all transactions since the bank opened. The log specifies
	 * 	who made the transaction, what kind of transaction it was (deposit, withdrawal, or transfer),
	 * 	which account or accounts were involved, and when it was made (date and time).
	 */
	@Override
	public void viewTransactionLog() {
		
		try {
			File logFile = new File("C:\\Users\\kmraz\\Documents\\Revature\\Projects\\Transactions.log");
			Scanner read = new Scanner(logFile);
			
			if (!read.hasNextLine()) {
				System.out.println("Log file is empty.\n");
			}
			
			System.out.println("Here are the contents of the log file:");
			while (read.hasNextLine()) {
				String data = read.nextLine();
				System.out.println(data);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
		}
		
	}

}
