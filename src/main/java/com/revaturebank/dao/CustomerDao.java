package com.revaturebank.dao;

import java.util.List;

import com.revaturebank.model.Customer;

public interface CustomerDao {
	public void saveCustomer(Customer customer);

	public List<Customer> getCustomersFromCustomerId(Integer id);

	public void updateCustomerBalance(Integer accountNumber, Double amount);

	public Customer getCustomerFromAccountNumber(Integer payToAccountNumber);

	public Customer getCustomerFromCustomerId(Integer id);

	public List<Customer> getCustomersFromStatus(String string);

	public void updateCustomerAccountStatus(String status, Integer id);
	
}
