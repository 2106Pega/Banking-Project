package com.revature.repo;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;

public interface EmployeeDAO {
	
	public User getEmployeeByUsernameAndPassword(String username, String password);
	public List<Account> getPendingAccounts();
	public List<Account> getAllAccounts();
	public boolean approveAccount(String account_id);
	public boolean denyAccount(String account_id);
	public Customer getCustomerByAccountID(String account_id);

}
