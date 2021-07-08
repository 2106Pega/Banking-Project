/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.repo;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;

public interface EmployeeDAO {
	
	public User GetEmployeeByUsernameAndPassword(String username, String password);
	public List<Account> GetPendingAccounts();
	public List<Account> GetAllAccounts();
	public boolean ApproveAccount(String account_id);
	public boolean DenyAccount(String account_id);
	public Customer GetCustomerByAccountID(String account_id);

}
