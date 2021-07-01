package com.revature.models;

public class User {
	
	private int userId;
	private String firstName, lastName, username, password;
	public User(int userId, String firstName, String lastName, String username, String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "User #: " + userId + "\n"
				+ "Name: " + firstName + lastName + "\n"
				+ "Username: " + username + "\n"
				+ "Password: " + password + "\n";
	}
	
	
}
