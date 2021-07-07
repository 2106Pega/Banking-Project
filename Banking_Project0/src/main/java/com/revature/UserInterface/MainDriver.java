package com.revature.UserInterface;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revature.Application.Customer;
import com.revature.Application.Employee;
import com.revature.DatabaseTools.ConnectionGetter;
import com.revature.DatabaseTools.UserLogin;
import com.revature.Models.User;

public class MainDriver 
{
	private static final Logger LOG = LogManager.getLogger(MainDriver.class);
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		//gathering user credentials here
		System.out.println("Welcome to the Revature Bank");
		System.out.println("Would you like to (1)login or (2)register?");
		int x = input.nextInt();
		input.nextLine();
		if(x == 2)
		{
			System.out.println("Please enter your username");
			String newUsername = input.nextLine();
			System.out.println("Please enter your password");
			String newPassword = input.nextLine();
			Customer newUser = new Customer();
			boolean regComplete = newUser.register(newUsername, newPassword);
			while(!regComplete)
			{
				System.out.println("Username is taken, please choose a new one: ");
				LOG.error("Duplicate username: " + newUsername);
				newUsername = input.nextLine();
				regComplete = newUser.register(newUsername, newPassword);
			}
			System.out.println("Registration complete, please login with your new credentials: ");
			LOG.info("New user registered: " + newUsername);
		}
		System.out.println("Please enter your username");
		String username = input.nextLine();
		System.out.println("Please enter your password");
		String password = input.nextLine();
		
		UserLogin login = new UserLogin();
		User credentials = login.getLogin(username, password);
		while(credentials == null)
		{
			System.out.println("Invalid username/password please try again");
			LOG.error("Bad login from: " + username);
			System.out.println("Please enter your username");
			username = input.nextLine();
			System.out.println("Please enter your password");
			password = input.nextLine();
			credentials = login.getLogin(username, password);
		}
		
		//checks if the user is a employee or customer
		if(credentials.isEmployee())
			employeeLogic(credentials);
		else
			customerLogic(credentials);
		
		System.out.println("End of program");
	}
	
	//method for customer user stories
	public static void customerLogic(User user)
	{
		LOG.info("Customer login successful: " + user.getUsername());
		Scanner custInput = new Scanner(System.in);
		Customer cust = new Customer(user);
		int choice = -1;
		while(choice != 6)
		{
			displayCustomerOptions();
			choice = custInput.nextInt();
			switch(choice)
			{
			case 1:
				//view specific account details
				System.out.println("Please enter the account id you would like to view: ");
				cust.viewAccounts();
				int acc_id = custInput.nextInt();
				while(acc_id < 1)
				{
					System.out.println("Invalid input: Please try again ");
					acc_id = custInput.nextInt();
				}
				cust.viewBalance(acc_id);
				LOG.info(user.getUsername() + " viewed account " + acc_id + " details");
				break;
			case 2:
				//withdraw from account
				System.out.println("Please enter the account id you would like to withdraw: ");
				cust.viewAccounts();
				int withdraw_id = custInput.nextInt();
				System.out.println("Enter amount to withdraw: ");
				int withdrawAmount = custInput.nextInt();
				while(withdrawAmount <= 0)
				{
					System.out.println("Invalid amount, please try again: ");
					withdrawAmount = custInput.nextInt();
				}
				boolean withCheck = cust.withdraw(withdrawAmount, withdraw_id);
				if(withCheck)
				{
					System.out.println("Withdraw successful");
					LOG.info(user.getUsername() + " withdrew from account " + withdraw_id + " " +withdrawAmount + "$");
				}
				else
				{
					System.out.println("Withdraw not completed");
					LOG.error(user.getUsername() + " could not complete withdraw");
				}
					
				break;
			case 3:
				//deposit to account
				System.out.println("Please enter the account id you would like to deposit: ");
				cust.viewAccounts();
				int deposit_id = custInput.nextInt();
				System.out.println("Enter amount to deposit: ");
				int depositAmount = custInput.nextInt();
				while(depositAmount <= 0)
				{
					System.out.println("Invalid amount, please try again: ");
					depositAmount = custInput.nextInt();
				}
				boolean depCheck = cust.deposit(depositAmount, deposit_id);
				if(depCheck)
				{
					System.out.println("Deposit successful");
					LOG.info(user.getUsername() + " deposited to account " + deposit_id + " " + depositAmount + "$");
				}
				else
				{
					System.out.println("Deposit not completed");
					LOG.error(user.getUsername() + " could not complete deposit");
				}
				break;
			case 4:
				//transfer funds
				System.out.println("Please enter the account id you would like to transfer out of: ");
				cust.viewAccounts();
				int source_id = custInput.nextInt();
				System.out.println("Enter amount to transfer: ");
				int transferAmount = custInput.nextInt();
				if(transferAmount <= 0)
				{
					System.out.println("Invalid amount, please try again: ");
					transferAmount = custInput.nextInt();
				}
				System.out.println("Enter id of account to transfer to");
				int receiving_id = custInput.nextInt();
				boolean custCheck = cust.transfer(transferAmount, source_id, receiving_id);
				if(custCheck)
				{
					System.out.println("Transfer completed");
					LOG.info(user.getUsername() + " transfered from account " + source_id + " to " + receiving_id + " " + transferAmount + "$");
				}
				else
				{
					System.out.println("Transfer not completed");
					LOG.error(user.getUsername() + " could not complete transfer");
				}
				break;
			case 5:
				//submit new account request
				System.out.println("What kind of account would you like to open?");
				custInput.nextLine();
				String accType = custInput.nextLine();
				while(accType.length() > 15)
				{
					System.out.println("Description is too long, please shorten (Limit 15 characters)");
					LOG.error("Account description too long; rejected");
					accType = custInput.nextLine();
				}
				System.out.println("What would you like your starting balance to be?");
				int startBal = custInput.nextInt();
				while(startBal < 0)
				{
					System.out.println("Invalid balance, please try again");
					LOG.error("Invalid starting balance; rejected");
					startBal = custInput.nextInt();
				}
				cust.newAccount(accType, startBal);
				LOG.info(user.getUsername() + " requested new account: " + accType + " with starting balance:  " + startBal + "$");
				break;
			case 6:
				//logout
				custInput.close();
				LOG.info(user.getUsername() + " logged out");
				break;
			default:
				System.out.println("Invalid input, please try again");
				break;
			}
		}
	}
	
	//customer user stories
	public static void displayCustomerOptions()
	{
		System.out.println("Please select a option");
		System.out.println("(1) View Balance");
		System.out.println("(2) Withdraw");
		System.out.println("(3) Deposit");
		System.out.println("(4) Transfer money");
		System.out.println("(5) Open Account");
		System.out.println("(6) Log out");
	}
	
	//method for employee user stories
	public static void employeeLogic(User user)
	{
		LOG.info("Employee login successful: " + user.getUsername());
		Scanner empInput = new Scanner(System.in);
		Employee emp = new Employee(user);
		int choice = -1;
		while(choice != 4)
		{
			displayEmployeeOptions();
			choice = empInput.nextInt();
			empInput.nextLine();
			switch(choice)
			{
			case 1:
				//check and approve/reject pending account
				emp.checkPendingAccount();
				LOG.info(user.getUsername() + " viewed pending accounts");
				break;
			case 2:
				//check accounts owned by username
				System.out.println("Please input username to view accounts for: ");
				String username = empInput.nextLine();
				emp.viewAccounts(username);
				LOG.info(user.getUsername() + " viewed accounts from user: " + username);
				break;
			case 3:
				//view log of all transactions
				emp.viewLog();
				LOG.info(user.getUsername() + " viewed log of transactions");
				break;
			case 4:
				//logout
				empInput.close();
				LOG.info(user.getUsername() + " logged out");
				break;
			default:
				System.out.println("Invalid input, please try again");
				break;
			}
		}
	}
	
	//employee user stories
	private static void displayEmployeeOptions() 
	{
		System.out.println("Please select a option");
		System.out.println("(1) Approve/Reject account");
		System.out.println("(2) View customer bank accounts");
		System.out.println("(3) View log of all transactions");
		System.out.println("(4) Log out");
	}
}
