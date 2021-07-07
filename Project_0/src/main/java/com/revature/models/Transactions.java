package com.revature.models;

import java.util.Objects;

public class Transactions {
	int transactionId;
	int accountId;
	String accountType;
	String transactionType;
	double transactionAmount;
	int transferIntoID;
	
	public Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transactions(int transactionId, int accountId, String accountType, String transactionType,
			double transactionAmount) {
		this(transactionId, accountId, accountType, transactionType, transactionAmount, -1);
	}

	public Transactions(int transactionId, int accountId, String accountType, String transactionType,
			double transactionAmount, int transferIntoID) {
		super();
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.accountType = accountType;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transferIntoID = transferIntoID;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public int getTransferIntoID() {
		return transferIntoID;
	}

	public void setTransferIntoID(int transferIntoID) {
		this.transferIntoID = transferIntoID;
	}

	@Override
	public String toString() {
		return "Transactions [transactionId=" + transactionId + ", accountId=" + accountId + ", accountType="
				+ accountType + ", transactionType=" + transactionType + ", transactionAmount=" + transactionAmount
				+ ", transferIntoID=" + transferIntoID + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, accountType, transactionAmount, transactionId, transactionType, transferIntoID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transactions other = (Transactions) obj;
		return accountId == other.accountId && Objects.equals(accountType, other.accountType)
				&& Double.doubleToLongBits(transactionAmount) == Double.doubleToLongBits(other.transactionAmount)
				&& transactionId == other.transactionId && Objects.equals(transactionType, other.transactionType)
				&& transferIntoID == other.transferIntoID;
	}	
}
