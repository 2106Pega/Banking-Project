package com.revature;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.Bank.BankManager;
import com.revature.models.User;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDaoImpl;

public class MainDriver {
	/* *
	 * ## User Stories
		Total Points: 23 points
		​
		* As a user, I can login.
			* 2 points
		* As a customer, I can apply for a new bank account with a starting balance.
			* 3 points
		* As a customer, I can view the balance of a specific account.
			* 1 point
		* As a customer, I can make a withdrawal or deposit to a specific account.
			* 2 points
		* As the system, I reject invalid transactions.
			* Ex:
				* A withdrawal that would result in a negative balance.
				* A deposit or withdrawal of negative money.
			* 2 points
		* As an employee, I can approve or reject an account.
			* 2 points
		* As an employee, I can view a customer's bank accounts.
			* 1 point
		* As a user, I can register for a customer account.
			* 3 points
		* As a customer, I can post a money transfer to another account.
			* 3 points
		* As a customer, I can accept a money transfer from another account.
			* 2 points
		* An employee, I can view a log of all transactions.
			* 2 points
	 */
	private static final Logger loggy = Logger.getLogger(MainDriver.class);
	
	public static void main(String[] args) {
		//Account ac = new Account(0, "first", "last", 100);
		//System.out.println(ac);
		
//		UserDAO user = new UserDaoImpl();
//		User newUser = user.selectUserByUsername("newUser");
//		
//		System.out.println(newUser);
		loggy.setLevel(Level.ERROR);
		loggy.info("Get BankManager instance");
		BankManager bank = BankManager.getInstance();
		loggy.info("Start BankManager.run()");
		bank.run();
	}

}
