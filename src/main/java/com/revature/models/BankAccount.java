package com.revature.models;

public class BankAccount {
	private int accountId;
	private String usernameRef, name;
	private double balance;
	private boolean isApproved;
	
	public BankAccount(int accountId, String usernameRef, String name, double balance, boolean isApproved) {
		this.accountId = accountId;
		this.usernameRef = usernameRef;
		this.name = name;
		this.balance = balance;
		this.isApproved = isApproved;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getUsernameRef() {
		return usernameRef;
	}
	public void setUsernameRef(String usernameRef) {
		this.usernameRef = usernameRef;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	@Override
	public String toString() {
		if(isApproved) {
			return "Account Name: " + name + "\n"
					+ "Balance: " + balance + "\n";
		}
		return "Account Name: " + name + " [AWAITING APPROVAL]\n"
		+ "Balance: " + balance + "\n";
	}
	
	public String toStringWithUsername() {
		if(isApproved) {
			return "Username: " + usernameRef + "\n"
					+ "Account Name: " + name + "\n"
					+ "Balance: " + balance + "\n";
		}
		return "Username: " + usernameRef + "\n"
				+ "Account Name: " + name + " [AWAITING APPROVAL]\n"
				+ "Balance: " + balance + "\n";
	}

	public String toTabbedString() {
		if(isApproved) {
			return "\tAccount Name: " + name + "\n"
					+ "\tBalance: " + balance + "\n";
		}
		return "\tAccount Name: " + name + " [AWAITING APPROVAL]\n"
		+ "\tBalance: " + balance + "\n";
	}
	
	public String toTabbedStringWithUsername() {
		if(isApproved) {
			return "\tUsername: " + usernameRef + "\n"
					+ "\tAccount Name: " + name + "\n"
					+ "\tBalance: " + balance + "\n";
		}
		return "\tUsername: " + usernameRef + "\n"
				+ "\tAccount Name: " + name + " [AWAITING APPROVAL]\n"
				+ "\tBalance: " + balance + "\n";
	}

	public boolean equals(BankAccount acc) {
		return this.getAccountId() == acc.getAccountId();
	}
}
