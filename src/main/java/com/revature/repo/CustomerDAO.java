package com.revature.repo;

import java.util.List;

import com.revature.models.Customer;
import com.revature.models.User;

public interface CustomerDAO {

	public Customer getCustomerByUser(User user);
	public List<Customer> GetAllCustomers();
	public boolean insertCustomer(Customer customer);
	public boolean removeCustomer(Customer customer);
	public boolean updateCustomer(Customer customer);
	public Customer GetCustomerByAccountId(int account_id);
}
