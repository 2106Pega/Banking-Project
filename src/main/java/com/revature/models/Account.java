/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.models;

public class Account {
	
	private int account_id;
	private int customer_id;
	private double balance;
	private boolean isApproved;
	
	public Account(int account_id, int customer_id, double balance, boolean isApproved) {
		this.account_id = account_id;
		this.customer_id = customer_id;
		this.balance = balance;
		this.setApproved(isApproved);
	}
	
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}


	public boolean isApproved() {
		return isApproved;
	}


	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	

}
