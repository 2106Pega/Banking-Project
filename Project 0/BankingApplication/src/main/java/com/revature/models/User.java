package com.revature.models;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int accountType;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String username, String password, String firstName, String lastName, int accountType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = accountType;
	}
	
	public User(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAccountType() {
		return accountType;
	}
	
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", accountType=" + accountType + "]";
	}
	
	
}
