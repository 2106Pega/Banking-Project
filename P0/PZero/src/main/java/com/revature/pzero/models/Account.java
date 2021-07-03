package com.revature.pzero.models;

public class Account {
	
	private int id;
	private Double balance;
	private String nickName;
	
	public Account() {
		super();
		this.id = -1;
		this.balance = 0.0;
		this.nickName = "";
	}

	public Account(int id, Double balance, String nickName) {
		super();
		this.id = id;
		this.balance = balance;
		this.nickName = nickName;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + id;
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		return result;
	}
	
	public void displayBalance() {
		if(!nickName.isEmpty()) {
			System.out.printf("ACCOUNT: " + nickName + "\nBALANCE: $%.2f%n", balance);
		}else {
			System.out.printf("ACCOUNT: " + id + "\nBALANCE: $%.2f%n", balance);
//			System.out.printf("'%5.2f'%n", 5.1473);
		}
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
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (id != other.id)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Account [ id= " + id + ", balance= $" + balance + ", nickName= " + nickName + " ]";
	}
	
	
}
