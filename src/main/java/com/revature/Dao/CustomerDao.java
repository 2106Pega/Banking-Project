package com.revature.Dao;

import com.revature.models.Account;

public interface CustomerDao {
	public void init(Account a);
	public boolean apply_account(Account a);
	public void view_balance(Account a);
	public void deposite(Account a, double amount);
	public void withdraw(Account a, double amount);
	public void post(Account a, Account b, double amount);
	public void accept(Account a, Account b, double amount);
	

}
