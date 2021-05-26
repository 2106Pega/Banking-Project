package com.paul.model;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paul.dao.*;

public class Client extends User {
	public ArrayList<Account> accounts;
	private static final Logger LOG = LogManager.getLogger(Client.class);
	
	public Client(int uId, String uname, String passwd) { 
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
		this.accounts = null;
	}
	public Client(String uname, String passwd) { 
		this.username = uname;
		this.password = passwd;
	}
	
	public Client(int uId, String uname, String passwd, ArrayList<Account> accounts) {
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
		this.accounts = accounts;
	}
	
	@Override
	public void printOptions() {
		System.out.println("What would you like to do? (1. View bank accounts. 2. Make a transaction. 3. Create a bank account. 4. Money transfer. 5. Exit.)");
	}

	@Override
	public int processChoice(int choice) {
		Scanner in = new Scanner(System.in);
		int acctChoice = -1;
		int secondAcctChoice = -1;
		boolean withdrawl = false;
		double amount = 0.0;
		switch(choice) {
		case 1:
			getMyAccounts();
			for (Account acct : this.accounts) {
				System.out.println(String.format("Account %d: %f", acct.accountID, acct.balance));
			}
			if (this.accounts.size() == 0) {
				System.out.println("You don't seem to have any approved accounts.");
			}
			break;
		case 2:
			getMyAccounts();
			for (int i = 0; i < this.accounts.size(); i++) {
				System.out.println(String.format("Account index %d: %f", i, this.accounts.get(i).balance));
			}
			if (this.accounts.size() == 0) {
				System.out.println("You don't seem to have any approved accounts.");
				break;
			}
			System.out.println("Which account do you want to deposit/withdraw to/from?");
			acctChoice = in.nextInt();
			in.nextLine();
			
			if (acctChoice < 0 || acctChoice >= this.accounts.size()) {
				System.out.println("Sorry, that account number is not a valid choice!");
				break;
			}
			System.out.println("Do you want to make a deposit (1) or a withdrawl (2)?");
			int tmp = in.nextInt();
			in.nextLine();
			withdrawl = (tmp == 1) ? false : true;
			if (!withdrawl) {
				System.out.println("How much do you want to deposit?");
				amount = in.nextDouble();
				in.nextLine();
				double res = deposit(amount, acctChoice);
				if (res == -1.0) {
					System.out.println("Something went wrong with your transaction.");
				} else {
					System.out.println("Your new balance is: $" + Double.toString(res));
				}
			} else {
				System.out.println("How much do you want to withdraw?");
				amount = in.nextDouble();
				in.nextLine();
				double res = withdraw(amount, acctChoice);
				if (res == -1.0) {
					System.out.println("Something went wrong with your transaction.");
				} else {
					System.out.println("Your new balance is: $" + Double.toString(res));
				}
			}
			break;
		case 3:
			System.out.println("With how much do you want to apply for an account with?: ");
			double bal = in.nextDouble();
			in.nextLine();
			applyForBankAccount(bal);
			break;
		case 4:
			getMyAccounts();
			for (int i = 0; i < this.accounts.size(); i++) {
				System.out.println(String.format("Account index %d: %f", i, this.accounts.get(i).balance));
			}
			if (this.accounts.size() < 2) {
				System.out.println("You don't seem to have enough accounts to perform a money transfer.");
				break;
			}
			System.out.println("Which account do you want to transfer from?");
			acctChoice = in.nextInt();
			in.nextLine();
			
			System.out.println("Which account do you want to transfer to?");
			secondAcctChoice = in.nextInt();
			in.nextLine();
			
			if (acctChoice == secondAcctChoice) { 
				System.out.println("You can't do a money transfer to the same account!");
				break;
			}
			
			if ((acctChoice < 0 || acctChoice >= this.accounts.size()) && (secondAcctChoice < 0 || secondAcctChoice >= this.accounts.size())) {
				System.out.println("Sorry, that account number is not a valid choice!");
				break;
			}
			
			System.out.println("How much do you want to transfer?");
			amount = in.nextDouble();
			in.nextLine();
			double wdRes = withdraw(amount, acctChoice);
			if (wdRes >= 0.0) {
				deposit(amount, secondAcctChoice);
				System.out.println("Money transfer conpleted!");
			} else {
				System.out.println("Money transfer failed! Is one of your accounts low?");
			}
			
			break;
		case 5: default:
			System.out.println("Goodbye!");
			in.close();
			System.exit(0);
			break;
		}
		
		return 0;
	}
	
	void applyForBankAccount(double startingAmount) {	
		Message app = new Message(this.userId, Message.EMPLOYEE_RID, startingAmount);
		BankDAO dao = new BankDAOImpl();
		dao.sendMessage(app);
		System.out.println("You have applied for a bank account!");
		LOG.trace("User " + this.username + " applied for a bank account");
	}
	
	public void getMyAccounts() {
		BankDAO dao = new BankDAOImpl();
		ArrayList<Account> accts = dao.getAccounts(this.userId);
		this.accounts = accts;
	}
	
	public double deposit(double amount, int idx) {
		BankDAO dao = new BankDAOImpl();
		if (amount < 0.0) {
			System.out.println("Cannot deposit a negative balance!");
			LOG.info("User " + this.username + " tried to deposit a negative balance");
			return -1.0;
		}
		Account acct = this.accounts.get(idx);
		acct.balance += amount;
		dao.commitAccount(acct);
		getMyAccounts();
		LOG.info("User " + this.username + " deposited " + Double.toString(amount));
		return this.accounts.get(idx).balance;
	}
	
	public double withdraw(double amount, int idx) {
		BankDAO dao = new BankDAOImpl();
		if (amount < 0.0) {
			System.out.println("Cannot deposit a negative balance!");
			LOG.info("User " + this.username + " tried to withdraw a negative balance");
			return -1.0;
		}
		Account acct = this.accounts.get(idx);
		if (acct.balance - amount < 0.0) {
			System.out.println("Cannot withdraw more money than you have!");
			LOG.info("User " + this.username + " tried to withdraw too much money from an account");
			return -1.0;
		}
		acct.balance -= amount;
		dao.commitAccount(acct);
		getMyAccounts();
		LOG.info("User " + this.username + " withdrew " + Double.toString(amount));
		return this.accounts.get(idx).balance;
	}
}
