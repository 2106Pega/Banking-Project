package com.revaturebank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revaturebank.model.Customer;
import com.revaturebank.model.User;
import com.revaturebank.util.ConnectionFactory;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public void saveCustomer(Customer customer) {
		String sql = "INSERT INTO customer_table ("
				+ "customer_id,"
				+ "first_name,"
				+ "last_name,"
				+ "email,"
				+ "account_name,"
				+ "account_type,"
				+ "balance,"
				+ "status,"
				+ "phone_number)"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		
		
		
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,customer.getCustomerId());
			ps.setString(2,customer.getFirstName());
			ps.setString(3,customer.getLastName());
			ps.setString(4, customer.getEmail());
			ps.setString(5, customer.getAccountName());
			ps.setString(6, customer.getAccountType());
			ps.setDouble(7, customer.getBalance());
			ps.setString(8, customer.getStatus());
			ps.setString(9, customer.getPhoneNumber());

			ps.execute();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public List<Customer> getCustomersFromCustomerId(Integer id) {
		String sql = "SELECT * FROM customer_table WHERE customer_id = ?";
		List<Customer> customerList = new ArrayList();
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				customerList.add(new Customer(
						rs.getInt("Customer_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getInt("account_number"),
						rs.getString("account_name"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getString("phone_number")));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		} 		
		return customerList;
	}

	@Override
	public void updateCustomerBalance(Integer accountNumber, Double updatedBalance) {
		String sql = "UPDATE customer_table SET balance = ? WHERE account_number = ?";

		try(Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, updatedBalance);
			ps.setInt(2, accountNumber);	
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 	
		
	}

	@Override
	public Customer getCustomerFromAccountNumber(Integer payToAccountNumber) {
		String sql = "SELECT * FROM customer_table WHERE account_number = ?";
		Customer customer = null;
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,payToAccountNumber);
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				customer = new Customer(
						rs.getInt("Customer_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getInt("account_number"),
						rs.getString("account_name"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getString("phone_number"));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		} 		
		return customer;
	}

	@Override
	public Customer getCustomerFromCustomerId(Integer id) {
		String sql = "SELECT * FROM customer_table WHERE customer_id = ?";
		Customer customer = null;
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				customer = new Customer(
						rs.getInt("Customer_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getInt("account_number"),
						rs.getString("account_name"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getString("phone_number"));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		} 		
		return customer;
	}

	@Override
	public List<Customer> getCustomersFromStatus(String status) {
		String sql = "SELECT * FROM customer_table WHERE status = ?";
		List<Customer> customerList = new ArrayList();
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,status);
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				customerList.add(new Customer(
						rs.getInt("Customer_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getInt("account_number"),
						rs.getString("account_name"),
						rs.getString("account_type"),
						rs.getDouble("balance"),
						rs.getString("status"),
						rs.getString("phone_number")));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		} 		
		return customerList;
	}

	@Override
	public void updateCustomerAccountStatus(String status, Integer accountNumber) {
		String sql = "UPDATE customer_table SET status = ? WHERE account_number = ?";

		try(Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, accountNumber);	
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 	
		
	}

}
