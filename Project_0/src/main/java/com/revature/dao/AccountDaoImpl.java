package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.util.ConnectionFactory;

public class AccountDaoImpl implements AccountDao {

	@Override
	public boolean createAccount(int customerId, String accountType, double amount) {
		String sql = "INSERT INTO accounts "
				+ "(account_type, account_balance, c_id) "
				+ "VALUES (?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  accountType);
			ps.setDouble(2, amount);
			ps.setInt(3, customerId);
			
			//execute the query
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Account> getAllCustomerAccounts(int customerId) {

		List<Account> databaseAccounts = new ArrayList<Account>();
				
		String sql = "SELECT * FROM accounts\r\n"
				+ "	WHERE c_id = ? ;";
		
		//try with resources syntax
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerId);
			
			//We expect rows and columns back from our db
			ResultSet rs = ps.executeQuery();
			
			//We want to convert those columns and rows into objects.
			while(rs.next()) {
				databaseAccounts.add(new Account(
						rs.getInt("account_number"), 
						rs.getString("account_type"), 
						rs.getDouble("account_balance"), 
						rs.getInt("c_id")
						));				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return databaseAccounts;
	}

	@Override
	public Account getCustomerBankAccount(int accountId) {
		Account customerAccount = null;
		
		String sql = "SELECT * FROM accounts\r\n"
				+ "	WHERE account_number = ? ;";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, accountId);
			
			ResultSet rs = ps.executeQuery();
			
			customerAccount = new Account(
						rs.getInt("account_number"), 
						rs.getString("account_type"), 
						rs.getDouble("account_balance"), 
						rs.getInt("c_id"));	
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return customerAccount;
	}

	@Override
	public double getAccountBalance(int accountId) {
		double accountBalance = -1;
		
		String sql = "SELECT account_balance FROM accounts\r\n"
				+ "	WHERE account_number = ? ;";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			
			accountBalance = rs.getDouble("account_balance");	
		}catch (SQLException e) {
			e.printStackTrace();
			return accountBalance;
		}
		return accountBalance;
	}

	@Override
	public boolean updateBalance(int accountId, double newBalance) {
		String sql = "update accounts set account_balance = ?, "
				+ "where account_number = ?;";
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, newBalance);
			ps.setInt(2, accountId);
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
