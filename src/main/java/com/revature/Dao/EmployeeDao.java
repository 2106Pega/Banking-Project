package com.revature.Dao;

import com.revature.models.Account;
import com.revature.models.Employee;

public interface EmployeeDao {
	public boolean login(Employee employee);
	public void approve(Account a);
	public void reject(Account a);
	public void view_customer(Account a);
}
