package com.revature;

public class User {
	
	
	public User(){
		user_name = "Hello";
		password = "pw";
		first_name = "fn";
		last_name = "ln";
	}
	
	public User(String firstName, String lastName, String username,String password) {
		this.setFirst_name(firstName);
		this.setLast_name(lastName);
		this.setPassword(password);
		this.setUser_name(username);
		this.setApproved(false);
	}
	private int User_Id;
	private String user_name;
	private String password;
	private String first_name;
	private String last_name;
	private boolean isEmployee;
	private boolean approved;
	private int balance;
	private int accountId;
	private String account_name;

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUser_Id() {
		return User_Id;
	}
	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public boolean isEmployee() {
		return isEmployee;
	}
	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
}
