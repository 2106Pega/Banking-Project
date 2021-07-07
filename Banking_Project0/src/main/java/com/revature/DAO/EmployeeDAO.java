package com.revature.DAO;

import java.util.List;

import com.revature.Models.Account;
import com.revature.Models.Log_Entry;
import com.revature.Models.Unregistered_Account;

public interface EmployeeDAO 
{
	public Unregistered_Account checkPendingAccount();
	public void approve(Unregistered_Account acc);
	public void reject(Unregistered_Account acc);
	public List<Account> viewAccounts();
	public List<Log_Entry> viewLog();
}
