package com.revature.database;

import com.revature.models.Customer;
import com.revature.models.User;

public interface UserDAO extends GenericDAO<User>{
	User getByUsername(String username, String pass);
	User getByUsername(String username);
	User getByCustomer(Customer c);
}
