package com.paul.model;

public class Account {
	int accountID;
	int ownerID;
	double balance;
	
	public Account(int aId, int oId, double bal) {
		this.accountID = aId;
		this.ownerID = oId;
		this.balance = bal;
	}
	public double getBalance()   { return balance;           }
	public void setBalance(double b)   { this.balance = b;   } //only used for testing
	public int getAccountID() { return accountID;            }
}
