package com.revature.models;

import java.util.Objects;

public class Account {
	private String username;
	private String account_name;
	private boolean approval;
	private double balance;
	
	public Account(String username, String account_name, boolean approval, double balance) {
		super();
		this.username = username;
		this.account_name = account_name;
		this.setApproval(approval);
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_name, balance, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(account_name, other.account_name)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Username: " + username + " Account name: " + account_name + " Approval status: " + approval + " Balance: " + balance;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	
	

}
