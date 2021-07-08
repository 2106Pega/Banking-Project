package com.revature.database;

import java.util.List;

import com.revature.models.Customer;

public interface CustomerDAO extends GenericDAO<Customer>{
	List<Customer> getByName(String s);
}
