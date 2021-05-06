package model;

public class Account {
	int accountID;
	int ownerID;
	double balance;
	
	public Account(int aId, int oId, double bal) {
		this.accountID = aId;
		this.ownerID = oId;
		this.balance = bal;
	}
}
