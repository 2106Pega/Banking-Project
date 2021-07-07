package com.revature.models;

import java.util.List;

public class Users {

	private int ID;
	private String user_name;
	private String firstName;
	private String lastName; 
	private List<Accounts> accountList;
	
	public Users(int iD, String user_name, String firstName, String lastName, List<Accounts> accountList) {
		super();
		ID = iD;
		this.user_name = user_name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountList = accountList;
	}
	@Override
	public String toString() {
		return  ID + ") user_name: " + user_name + ", firstName: " + firstName + ", lastName: " + lastName;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public List<Accounts> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<Accounts> accountList) {
		this.accountList = accountList;
	}

	
	
	
	



	
	
	
	
	
}
