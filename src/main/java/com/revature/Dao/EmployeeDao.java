package com.revature.Dao;

import com.revature.models.Account;

public interface EmployeeDao {
	public void approve(Account a);
	public void reject(Account a);
	public void view_customer(Account a);
}
