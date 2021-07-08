package com.revature.Bank;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.MainDriver;
import com.revature.models.Fund;
import com.revature.models.User;
import com.revature.repo.FundDAO;
import com.revature.repo.FundDaoImpl;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDaoImpl;

public class BankManager {
	private static BankManager instance;
	private UserDAO userDao;
	private FundDAO fundDao;
	private User currUser;
	private List<Fund> currFunds;
	private final Logger loggy = Logger.getLogger(BankManager.class);;
	
	private BankManager() {
		super();
		loggy.setLevel(Level.ERROR);
		currUser = null;
		currFunds = null;
		userDao = new UserDaoImpl();
		fundDao = new FundDaoImpl();
	}
	
	public static BankManager getInstance() {
		if(instance == null) {
			instance = new BankManager();
		}
		return instance;
	}
	
	public void run() {
		boolean quit1 = false;
		Scanner in = new Scanner(System.in);
		String input = "";
		
		while(!quit1) {
			//Allow the user to log in, or quit
			displayLogin();
			input = in.nextLine();
			if(input.equals("1")) {
				loggy.info("User chose to log in");
				boolean loggedIn = logIn(in);
				if(loggedIn && !currUser.isEmployee() && !currUser.isActive()) {
					displayNotActiveMessage();
				} else if(loggedIn && !currUser.isEmployee()) {
					customerLoop(in);
				} else if(loggedIn && currUser.isEmployee()){
					employeeLoop(in);
				}
				//Empty out currAccount and currFunds
				currUser = null;
				currFunds = null;
				
			} else if(input.equals("2")) {
				loggy.info("User chose to register a new account");
				System.out.println();
				boolean result = registerForAccount(in);
				if(result) {
					System.out.println("Account successfully registered. Please wait 2-3 business days for account approval.");
				} else {
					System.out.println("Could not complete account registration. Please try again.\n\n");
				}
				
			} else if(input.equals("3")) {
				//Exit out of the while loop
				loggy.info("User chose to quit the application");
				quit1 = true;
				displayExitMessage();
			} else {
				//Display message telling user to try again
				loggy.warn("User selected an invalid option");
				invalidOption();
			}
		}
	}
	
	public void displayLogin() {
		loggy.info("Display start options");
		printLine();
		System.out.println("Welcome to the 2106 Pega Bank!");
		System.out.println("Please choose an option:");
		System.out.println("1) Login");
		System.out.println("2) Register for an account");
		System.out.println("3) Quit");
	}
	
	public void displayExitMessage() {
		printLine();
		System.out.println("\nThank you for using Pega Bank. We hope to serve you again soon!");
	}
	
	public void invalidOption() {
		System.out.println("Please select a valid option from the list shown.\n\n");
	}
	
	public boolean logIn(Scanner in) {
		loggy.info("Starting login process");
		boolean valid = false;
		
		while (!valid) {
			System.out.println("\nPlease make sure that upper and lowercase are correct...");
			System.out.print("Username:\t");
			String user_n = in.nextLine();
			System.out.print("Password:\t");
			String user_p = in.nextLine();
			System.out.println();
			
			currUser = userDao.selectUserByUsername(user_n);
			
			if(currUser == null) {
				loggy.warn("Username could not be found");
				System.out.println("Could not find a user with that username. Please try again.");
			} else if(!currUser.getUser_pass().equals(user_p)) {
				loggy.warn("Incorrect password entered");
				System.out.println("The password is incorrect. Please try again.");
			} else {
				valid = true;
				currFunds = fundDao.selectFundsByOwnerId(currUser.getUser_id());
				loggy.info("Successfully logged in as user:" + currUser.getUser_name());
			}
		}
		return valid;
	}
	
	public boolean registerForAccount(Scanner in) {
		boolean result = false;
		//valid criteria, valid password criteria, valid first name, valid last name
		boolean[] checks = new boolean[4]; //Entire array is false
		boolean quit = false;
		
		//To build the User
		String username = "";
		String password = "";
		String firstName = "";
		String lastName = "";
		//Account needs to be activated by employee
		//Only an employee can upgrade another account to employee
		
		printLine();
		loggy.info("User started process to register a new account");
		System.out.println("Welcome to Customer Account Creation Menu. Please follow the intructions to create an account.");
		System.out.println("To exit out of this menu, enter 'q'.\n");
		
		//Valid Criteria for Username
		while(!checks[0] && !quit) {
			loggy.info("User trying to input a new username");
			System.out.println("Username must be between 8-30 characters long and alphanumeric only:");
			username = in.nextLine();
			
			//Check if user wants to quit
			if(username.equals("q")) {
				//exit out of all the loops
				loggy.warn("User quit \"New User Registration Process\"");
				quit = true;
				
			//Check for duplicate usernames
			} else if(userDao.selectUserByUsername(username) != null) {
				loggy.warn("Inputted username already in use!");
				System.out.println("This username is already in use! Please choose another one.\n");
				
			//Check for valid username criteria
			} else if((username.length() >= 8)  && (username.length() <= 30) &&  username.matches("^[a-zA-Z0-9]*$")) {
				loggy.info("Valid username entered");
				checks[0] = true;
				System.out.println();
			}
			else {
				loggy.warn("Username invalid");
				System.out.println("The username does not fit the criteria. Please enter another username.");
				System.out.println("To exit out of this menu, enter 'q'.\n");
			}
		}
		
		//Valid password
		while(!checks[1] && !quit) {
			loggy.info("Inputting new password now");
			System.out.println("Password must be between 8-30 characters long and alphanumeric only:");
			password = in.nextLine();
			
			//Check if user wants to quit
			if(password.equals("q")) {
				//exit out of all the loops
				loggy.warn("User quit \"New User Registration Process\"");
				quit = true;
				
			//Check for valid password criteria
			} else if((password.length() >= 8)  && (password.length() <= 30) &&  password.matches("^[a-zA-Z0-9]*$")) {
				loggy.info("Valid password entered");
				checks[1] = true;
				System.out.println();
			}
			else {
				loggy.warn("Password invalid");
				System.out.println("The password does not fit the criteria. Please enter another password.");
				System.out.println("To exit out of this menu, enter 'q'.\n");
			}
		}
		
		//Valid firstname
		while(!checks[2] && !quit) {
			loggy.info("Inputting first name now");
			System.out.println("First name must be at most 30 characters long, and contain letters only:");
			firstName = in.nextLine();
			
			//Check if user wants to quit
			if(firstName.equals("q")) {
				//exit out of all the loops
				loggy.warn("User quit \"New User Registration Process\"");
				quit = true;
				
			//Check for valid firstname criteria
			} else if((firstName.length() > 0)  && (firstName.length() <= 30) &&  firstName.matches("^[a-zA-Z\\-]*$")) {
				loggy.info("Valid first name entered");
				checks[2] = true;
				System.out.println();
			}
			else {
				loggy.warn("First name invalid");
				System.out.println("The first name does not fit the criteria. Please try again.");
				System.out.println("To exit out of this menu, enter 'q'.\n");
			}
		}
		
		//Valid lastname
		while(!checks[3] && !quit) {
			loggy.info("Inputting last name now");
			System.out.println("Last name must be at most 30 characters long, and contain letters only:");
			lastName = in.nextLine();
			
			//Check if user wants to quit
			if(lastName.equals("q")) {
				//exit out of all the loops
				loggy.warn("User quit \"New User Registration Process\"");
				quit = true;
				
			//Check for valid lastname criteria
			} else if((lastName.length() > 0)  && (lastName.length() <= 30) &&  lastName.matches("^[a-zA-Z\\-]*$")) {
				loggy.info("Valid last name entered");
				checks[3] = true;
				System.out.println();
			}
			else {
				loggy.warn("Last name invalid");
				System.out.println("The last name does not fit the criteria. Please try again.");
				System.out.println("To exit out of this menu, enter 'q'.\n");
			}
		}
		
		//Only run if the user did not quit
		if(!quit) {
			loggy.info("Registering new user with valid data");
			User newUser = new User(username, password, firstName, lastName);
			result = userDao.insertUser(newUser);
		}
		
		return result;
	}
	
	private boolean[] bypassBooleans(boolean[] checks, boolean value) {
		for(int i = 0; i < checks.length; i++) {
			checks[i] = value;
		}
		return checks;
	}
	
	public void customerLoop(Scanner in) {
		boolean quit = false;
		while(!quit) {
			displayCustomerMenu();
			String choice = in.nextLine();
			if(choice.equals("1")) {
				displayFunds();
			} else if(choice.equals("2")) {
				applyNewBankAccount(in);
			} else if(choice.equals("3")) {
				depositMoney(in);
			} else if(choice.equals("4")) {
				withdrawMoney(in);
				
			//} else if(choice.equals("5")) {
				
			} else if(choice.equals("5")) {
				quit = true;
				System.out.println("Logging out of your account now...\n");
			} else {
				loggy.warn("User customer selected invalid option");
				System.out.println("User selected an invalid option.");
			}
		}
		loggy.info("Logging out of customer account now");
	}
	
	public void employeeLoop(Scanner in) {
		//TODO implement this
		System.out.println("We are sorry, this has not been implemented yet.");
	}
	
	public void displayNotActiveMessage() {
		System.out.println("Oh no! Your customer account is not active yet.");
		System.out.println("Please wait for it to be approved.\n");
	}
	
	public void displayCustomerMenu() {
		loggy.info("Display customer menu");
		printLine();
		System.out.println("Welcome " + currUser.getFirst_name() + ",\n");
		System.out.println("Please select an option from below:");
		System.out.println("1) View all my bank accounts");
		System.out.println("2) Apply for a new bank account");
		System.out.println("3) Deposit money into an account");
		System.out.println("4) Withdraw money from an account");
		//System.out.println("5) Send money to someone else");
		System.out.println("5) Log out");
	}
	
	public void displayFunds() {
		if((currFunds == null) || (currFunds.size() <= 0)) {
			loggy.warn("Current funds is null");
			System.out.println("You currently do not have any active bank accounts.");
			System.out.println("Please apply for one.");
		} else {
			loggy.info("Viewing bank account funds");
			for(int i = 0; i < currFunds.size(); i++) {
				Fund fund = currFunds.get(i);
				System.out.println("Fund " + (i + 1) + ":");
				System.out.println("Account Type:\t" + fund.getFund_type());
				System.out.println("Amount:\t\t$" + fund.getFund_amount());
				System.out.println();
			}
		}
	}
	
	public boolean applyNewBankAccount(Scanner in) {
		int fundId = 0;
		int fundOwner = currUser.getUser_id();
		String fundType = "";
		double fundAmount = 0.0;
		
		boolean valid = false;
		while(!valid) {
			System.out.println("What kind of bank account would you like to open?");
			System.out.println("1) Checking");
			System.out.println("2) Savings");
			System.out.println("3) Custom");
			
			String choice = in.nextLine();
			if(choice.equals("1")) {
				fundType = "Checking";
				valid = true;
			} else if(choice.equals("2")) {
				fundType = "Savings";
				valid = true;
			} else if(choice.equals("3")) {
				System.out.println("Enter fund name:");
				fundType = in.nextLine();
				System.out.println();
				valid = true;
			} else {
				System.out.println("Could not determine bank account type.\n\n");
			}
		}
		
		valid = false;
		while(!valid) {
			System.out.println("How much money would you like to deposit into this account?");
			try {
				fundAmount = in.nextDouble();
				if(fundAmount < 0) {
					System.out.println("The deposit amount cannot be less than 0. Please try again.\n\n");
				}
				valid = true;
			} catch(InputMismatchException e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n\n");
			}
		}
		//int fund_id, int fund_owner, String fund_type, double fund_amount
		boolean result = fundDao.insertFund(new Fund(fundId, fundOwner, fundType, fundAmount));
		if(result) {
			System.out.println("Congratulations, you have opened a new bank account.");
			currFunds = fundDao.selectFundsByOwnerId(currUser.getUser_id());
		} else {
			System.out.println("Oh no. It seems we have encountered a system error and cout not complete your bank account application.");
		}
		return result;
	}
	
	private void printLine() {
		System.out.println("---------------------------------------------------"
											+ "----------------------------");
	}

	public boolean depositMoney(Scanner in) {
		if(currFunds.size() <= 0) {
			System.out.println("You do not have any open bank accounts. Please open one before depositing money into it.");
			System.out.println();
			return false;
		}
		Fund depositFund = null;
		double depositAmount = 0.0;
		
		boolean valid = false;
		while(!valid) {
			System.out.println("Which fund would you like to deposit money into? Please type in the number.");
			displayFunds();
			try {
				if(in.hasNextInt()) {
					int choice = in.nextInt();
					in.nextLine();
					if(choice <= 0 || choice > currFunds.size()) {
						System.out.println("Please enter in a valid number.\n");
					} else {
						depositFund = currFunds.get(choice - 1);
						valid = true;
					}
				} else {
					in.nextLine(); //clean the garbage input
					System.out.println("Could not read input. Please only enter numbers!\n");
				}
			} catch(InputMismatchException e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			} catch (Exception e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			}
		}
		
		valid = false;
		while(!valid) {
			System.out.println("How much money would you like to deposit into the fund? Please type only in numbers.");
			try {
				if(in.hasNextDouble()) {
					depositAmount = in.nextDouble();
					if(depositAmount <= 0) {
						System.out.println("Please enter in a positive number.\n");
					} else {
						valid = true;
					}
				} else {
					in.nextLine();
					System.out.println("Could not read input. Please only enter numbers!\n");
				}
			} catch(InputMismatchException e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			} catch (Exception e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			}
		}
		depositFund.setFund_amount(depositFund.getFund_amount() + depositAmount);
		
		boolean result = fundDao.updateFundInformation(depositFund);
		if(result) {
			System.out.println("Congratulations, money has been deposited into your account.");
			currFunds = fundDao.selectFundsByOwnerId(currUser.getUser_id());
		} else {
			System.out.println("Oh no. It seems we have encountered a system error and could not complete your deposit.");
			System.out.println("Please try again.");
		}
		return result;
	}

	public boolean withdrawMoney(Scanner in) {
		if(currFunds.size() <= 0) {
			System.out.println("You do not have any open bank accounts. Please open one before withdrawing money from it.");
			System.out.println();
			return false;
		}
		Fund withdrawFund = null;
		double withdrawAmount = 0.0;
		
		boolean valid = false;
		while(!valid) {
			System.out.println("Which fund would you like to withdraw money from? Please type in the number of the fund.");
			displayFunds();
			try {
				if(in.hasNextInt()) {
					int choice = in.nextInt();
					in.nextLine();
					if(choice <= 0 || choice > currFunds.size()) {
						System.out.println("Please enter in a valid number.\n");
					} else {
						withdrawFund = currFunds.get(choice - 1);
						valid = true;
					}
				} else {
					in.nextLine(); //clean the garbage input
					System.out.println("Could not read input. Please only enter numbers!\n");
				}
			} catch(InputMismatchException e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			} catch (Exception e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			}
		}
		
		valid = false;
		while(!valid) {
			System.out.println("How much money would you like to withdraw from the fund? Please type only in numbers.");
			try {
				if(in.hasNextDouble()) {
					withdrawAmount = in.nextDouble();
					if(withdrawAmount < 0 || withdrawAmount > withdrawFund.getFund_amount()) {
						System.out.println("Please enter in a valid number.\n");
					} else {
						valid = true;
					}
				} else {
					in.nextLine();
					System.out.println("Could not read input. Please only enter numbers!\n");
				}
			} catch(InputMismatchException e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			} catch (Exception e) {
				loggy.error(e);
				e.printStackTrace();
				System.out.println("Could not read input. Please only enter numbers!\n");
			}
		}
		withdrawFund.setFund_amount(withdrawFund.getFund_amount() - withdrawAmount);
		
		boolean result = fundDao.updateFundInformation(withdrawFund);
		if(result) {
			System.out.println("Congratulations, money has been withdrawn from your account.");
			currFunds = fundDao.selectFundsByOwnerId(currUser.getUser_id());
		} else {
			System.out.println("Oh no. It seems we have encountered a system error and could not complete your deposit.");
			System.out.println("Please try again.");
		}
		return result;
	}
}
