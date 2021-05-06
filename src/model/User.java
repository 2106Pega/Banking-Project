package model;

public abstract class User {
	int userId;
	int username;
	int password;
	
	public abstract void printOptions();
	public abstract int processChoice(int choice);
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
		this.username = username;
	}
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}
}
