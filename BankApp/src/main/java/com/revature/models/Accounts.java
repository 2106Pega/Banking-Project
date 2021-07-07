package com.revature.models;

import java.util.List;

public class Accounts {
	
	private int accountID;
	private String accountName;
	private double  banlance;
	private boolean isApprove;
	private int userID;
	private List<Transactions> transList;
	public Accounts(int iD, String accountName, double banlance, boolean isApprove, int userID,
			List<Transactions> transList) {
		super();
		accountID = iD;
		this.accountName = accountName;
		this.banlance = banlance;
		this.isApprove = isApprove;
		this.userID = userID;
		this.transList = transList;
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
	public List<Transactions> getTransList() {
		return transList;
	}
	public void setTransList(List<Transactions> transList) {
		this.transList = transList;
	}
	
	
	
	

	


	

}
