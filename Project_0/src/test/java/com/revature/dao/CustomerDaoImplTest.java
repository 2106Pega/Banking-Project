package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Test;
import org.mockito.Mock;

import com.revature.models.Customer;
import com.revature.util.ConnectionFactory;

public class CustomerDaoImplTest {

	@Mock
	ConnectionFactory mockFactory;
	
	@Mock
	Connection mockConn;
	
	@Mock
	PreparedStatement mockPS;
	
	@Test
	public void testCreateCustomer(Customer c) {
		
	}
//
//	public Customer selectCustomerById(int id) {
//		
//	}
//
//	public Customer selectCustomerByName(String first_name, String last_name) {
//		
//	}	
}
