/*
 * Customer handles any query requests by calling the extended DAO class provided. 
 * Customer also prints whatever is returned from the DAO queries
 */
package com.revature.Application;

import java.util.List;

import com.revature.DAO.CustomerDAOImpl;
import com.revature.Models.Account;
import com.revature.Models.User;

public class Customer extends CustomerDAOImpl
{
	private User cust;
	
	public Customer()
	{
		cust = null;
	}
	
	public Customer(User input)
	{
		cust = input;
	}
	
	public int getId()
	{
		return cust.getUser_id();
	}

	@Override
	public boolean register(String username, String password) {
		return super.register(username, password);
	}

	@Override
	public boolean login(String username, String password) {
		return super.login(username, password);
	}

	
	public boolean newAccount(String type, int balance) {
		boolean x = super.newAccount(cust.getUsername(), type, balance);
		if(x)
			System.out.println("Request for new account submitted");
		else
			System.out.println("Request for new account not completed");
		return x;
	}

	public int viewBalance(int acc_id) 
	{
		int balance = super.viewBalance(acc_id, cust.getUsername());
		if(balance == -1)
			System.out.println("You do not have access to that account");
		else
			System.out.println("The balance for that account is: " + balance);
		
		return balance;
	}
	
	@Override
	public int viewBalance(int id, String username)
	{
		return -1;
	}

	public boolean withdraw(int amount, int id) {
		return super.withdraw(amount, id, cust.getUsername());
	}

	
	public boolean deposit(int amount, int id) {
		return super.deposit(amount, id, cust.getUsername());
	}

	public boolean transfer(int amount, int source, int receive) {
		return super.transfer(amount, source, receive, cust.getUsername());
	}
	
	public List<Account> viewAccounts()
	{
		List<Account> retVal = super.viewAccounts(cust.getUsername());
		for(Account x : retVal)
			System.out.println("Account id: " + x.getAccount_id() + ", Account type: " + x.getType() + " Balance: " + x.getBalance());
		return retVal;
	}
	
	
	
	
}
