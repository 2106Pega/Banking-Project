package com.revature.models;

import java.util.Objects;

public class Account {
	private int accountID;
	private String type;
	private double balance;
	private int customerID;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(int accountID, String type, double balance, int customerID) {
		super();
		this.accountID = accountID;
		this.type = type;
		this.balance = balance;
		this.customerID = customerID;
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void modifyBalance(double modifier) {
		setBalance(getBalance() - modifier);
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	@Override
	public String toString() {
		return "Accounts [accountID=" + accountID + ", type=" + type + ", balance=" + balance + ", customerID="
				+ customerID + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(accountID, balance, customerID, type);
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
		return accountID == other.accountID
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& customerID == other.customerID && Objects.equals(type, other.type);
	}
}
