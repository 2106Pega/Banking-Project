package com.revature.Models;

public class Account 
{
	private int account_id;
	private String username;
	private int balance;
	private String type;
	
	public Account(int account_id, String username, int balance, String type) {
		super();
		this.account_id = account_id;
		this.username = username;
		this.balance = balance;
		this.type = type;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Account ID: " + account_id + ", Username: " + username + ", Balance: " + balance + ", Type: "
				+ type;
	}
	
	
}
