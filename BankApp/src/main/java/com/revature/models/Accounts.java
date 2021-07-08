package com.revature.models;

import java.util.List;

public class Accounts {
	
	private int accountID;
	private String accountName;
	private double  banlance;
	private boolean isApprove;
	private int userID;
	public Accounts(int iD, String accountName, double banlance, boolean isApprove, int userID) {
		super();
		accountID = iD;
		this.accountName = accountName;
		this.banlance = banlance;
		this.isApprove = isApprove;
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "accountName: " + accountName + ", banlance: " + banlance ;
	}

	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getBanlance() {
		return banlance;
	}
	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}
	public boolean isApprove() {
		return isApprove;
	}
	public void setApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	
	
	
	

	


	

}
