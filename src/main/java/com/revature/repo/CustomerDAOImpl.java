package com.revature.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class CustomerDAOImpl implements CustomerDAO{

	public Customer getCustomerByUser(User user) {
		Customer customer = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			String sql = "select * from customers where customer_id = '" + user.getUser_id() + "';";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);

			if(rs.next()) 
                customer = new Customer(user.getUser_id(), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"), rs.getString("email"));
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public List<Customer> GetAllCustomers() {
		Connection conn = ConnectionFactory.getConnection();
		List<Customer> customers = new ArrayList<Customer>();
		try {
			String sql = "select * from customers";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
                Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("phone_number"));
                customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean removeCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean insertCustomer(Customer customer) {
		Connection conn = ConnectionFactory.getConnection();
		try {
			String sql = "insert into customers (customer_id, first_name, last_name, phone_number, email) values "
					+ "('" + customer.getCustomerId() + "', '" + customer.getFirstName() + "', '" 
					+ customer.getLastName() + "', '" + customer.getPhoneNumber() + "', '" + customer.getEmail() + "');";
			Statement s = conn.createStatement();
			s.execute(sql);
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	public Customer GetCustomerByAccountId(int account_id) {
		// TODO Auto-generated method stub
		return null;
	}



}
