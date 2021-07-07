package com.revature.Models;

public class Unregistered_Account 
{
	private int request_id;
	private String account_username;
	private int balance;
	private String account_type;
	public Unregistered_Account(int request_id, String account_username, int balance, String account_type) {
		super();
		this.request_id = request_id;
		this.account_username = account_username;
		this.balance = balance;
		this.account_type = account_type;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public String getAccount_username() {
		return account_username;
	}
	public void setAccount_username(String account_username) {
		this.account_username = account_username;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	@Override
	public String toString() {
		return "Account Username: " + account_username + ", Balance: " + balance + ", Account Type: " + account_type;
	}
	
}
