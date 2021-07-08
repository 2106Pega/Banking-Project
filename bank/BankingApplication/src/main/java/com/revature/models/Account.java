package com.revature.models;

public class Account {
	
	private int accountId;
	private int applicationId;
	private int userId;
	private double balance;
	
	private String firstName;
	private String lastName;
	private String username;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(int accountId, double balance) {
		super();
		this.accountId = accountId;
		//this.userId = userId;
		this.balance = balance;
	}
	
	public Account(int userId, int accountId, double balance) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}
	
	public Account(int applicationId, int userId, String firstName, String lastName, String username, double balance) {
		super();
		this.applicationId = applicationId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.balance = balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirst_name(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLast_name(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Account [id=" + accountId + ", userId=" + userId + ", balance=" + balance + "]";
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	

}
