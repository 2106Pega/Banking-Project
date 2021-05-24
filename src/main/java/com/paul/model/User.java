package com.paul.model;

public abstract class User {
	int userId;
	String username;
	String password;
	
	public static final int CLIENT_TYPE = 1;
	public static final int EMPLOYEE_TYPE = 2;
	
	public abstract void printOptions();
	public abstract int processChoice(int choice);
	
	public int getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	
	public boolean equals(Object o) {
		if (o == this)
	        return true;
	    if (!(o instanceof User))
	        return false;
	    User other = (User) o;
	    return (this.userId == other.userId) && (this.username.equals(other.username)) && (this.password.equals(password));
	}
}
