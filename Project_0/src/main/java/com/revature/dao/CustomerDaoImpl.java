package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Customer;
import com.revature.util.ConnectionFactory;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public int createCustomer(Customer c) {
		String sql1 = "INSERT INTO customers "
				+ "(customer_first_name, customer_last_name, customer_ssn) "
				+ "VALUES (?, ?, ?)";

		String sql2 = "SELECT customer_id FROM customers WHERE customer_ssn = ?;";
		int customerId = -1;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setString(1, c.getFirst_name());
			ps.setString(2, c.getLast_name());
			ps.setInt(3, c.getSsn());
			
			ResultSet rs = ps.executeQuery();
			
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, c.getSsn());
			
			rs = ps.executeQuery();
			customerId = rs.getInt("customer_id");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return customerId;
	}

	@Override
	public Customer selectCustomerById(int id) {
		String sql = "SELECT * FROM customers WHERE customer_id = ?;";
		
		Customer getCustomer = null;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			getCustomer = new Customer(rs.getString("customer_first_name"), 
										rs.getString("customer_last_name"),
										rs.getInt("customer_id"),
										rs.getInt("customer_ssn"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return getCustomer;
	}

	@Override
	public Customer selectCustomerByName(String first_name, String last_name) {
		String sql = "SELECT * FROM customers WHERE customer_first_name = ? AND customer_last_name = ?;";
		
		Customer getCustomer = null;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, first_name);
			ps.setString(2, last_name);
			
			ResultSet rs = ps.executeQuery();
			getCustomer = new Customer(rs.getString("customer_first_name"), 
										rs.getString("customer_last_name"),
										rs.getInt("customer_id"),
										rs.getInt("customer_ssn"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return getCustomer;
	}
}
