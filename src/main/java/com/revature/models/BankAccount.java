package com.revature.models;

public class BankAccount extends User{
	private int accountId, userId;
	private double balance;
	public BankAccount(int userId, String firstName, String lastName, String username, String password, int accountId,
			int userId2, double balance) {
		super(userId, firstName, lastName, username, password);
		this.accountId = accountId;
		this.userId = userId2;
		this.balance = balance;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return super.toString() 
				+ "Account #:" + accountId + "\n"
				+ "Balance:" + balance + "\n";
	}
}
