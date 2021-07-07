package com.revature.Models;

public class User 
{
	private int user_id;
	private String username;
	private String password;
	private boolean isEmployee;
	
	public User(int id, String name, String pass, boolean emp)
	{
		user_id = id;
		username = name;
		password = pass;
		isEmployee = emp;
	}
	
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", isEmployee="
				+ isEmployee + "]";
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
}
