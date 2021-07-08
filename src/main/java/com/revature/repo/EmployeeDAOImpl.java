/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class EmployeeDAOImpl implements EmployeeDAO{

	public User GetEmployeeByUsernameAndPassword(String username, String password) {
		User employee = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			PreparedStatement s = conn.prepareStatement("select * from employees where username = ? and password = ?");
			s.setString(1, username);
			s.setString(2, password);
			ResultSet rs = s.executeQuery();
			if (rs.next() ) {
				employee = new User(rs.getInt("employee_id"), rs.getString("username"), rs.getString("password"));
			} 
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	public List<Account> GetPendingAccounts() {
		Connection conn = ConnectionFactory.getConnection();
		Account account;
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "select * from accounts where approval = 'FALSE';";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
                account = new Account(rs.getInt("account_id"), rs.getInt("customer_id"), rs.getDouble("balance"), rs.getBoolean("approval"));
                if( !account.isApproved() )
                	accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Account> GetAllAccounts() {
		Connection conn = ConnectionFactory.getConnection();
		List<Account> accounts = new ArrayList<Account>();
		Account account;
		try {
			String sql = "select * from accounts";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
                account = new Account(rs.getInt("account_id"), rs.getInt("customer_id"), rs.getDouble("balance"), rs.getBoolean("approval"));
                accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean ApproveAccount(String account_id) {
		Connection conn = ConnectionFactory.getConnection();
		try {
			String sql = "update accounts set approval = 'TRUE' where account_id = " + account_id;
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean DenyAccount(String account_id) {
		Connection conn = ConnectionFactory.getConnection();
		try {
			String sql = "delete from accounts where account_id = " + account_id;
			Statement s = conn.createStatement();
			s.executeUpdate(sql);
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Customer GetCustomerByAccountID(String account_id) {
		Customer customer = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			String sql = "select * from customers where customer_id";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next() ) {
				customer = new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"), rs.getString("email"));
			} 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

}
