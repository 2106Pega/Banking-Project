package com.revature.dao;

import com.revature.models.Customer;

public interface CustomerDao {

	//CRUD
	
	//CREATE
	int createCustomer(Customer c);
	//READ
	Customer selectCustomerById(int id);
	Customer selectCustomerByName(String first_name, String last_name);
	//UPDATE
		//There is no need to update, no method to change customer values
		//Customer accounts are added elsewhere
	//DELETE
		//There is no deletion of customers
}
