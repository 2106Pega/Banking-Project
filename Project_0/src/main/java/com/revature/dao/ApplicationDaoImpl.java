package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.util.ConnectionFactory;

public class ApplicationDaoImpl implements ApplicationDao {

	@Override
	public boolean createAccount(int customerId, String accountType, double amount) {
		String sql1 = "INSERT INTO account_applications "
				+ "(account_type, account_balance, c_id) "
				+ "VALUES (?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setString(1, accountType);
			ps.setDouble(2, amount);
			ps.setInt(3, customerId);
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Account> selectAllApplications() {

		List<Account> databaseApplications = new ArrayList<Account>();
		String sql = "SELECT * FROM account_applications;";
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				databaseApplications.add(new Account(
						rs.getInt("application_id"),
						rs.getString("account_type"),
						rs.getDouble("account_balance"),
						rs.getInt("c_id")
						));				
			}			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return databaseApplications;
	}

	@Override
	public boolean removeApplication(Account account) {

		String sql = "DELETE FROM account_applications "
				+ "where application_id = ? AND "
				+ "account_type = ? AND "
				+ "account_balance = ? AND "	
				+ "c_id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, account.getAccountID());
			ps.setString(2, account.getType());
			ps.setDouble(3, account.getBalance());
			ps.setInt(4, account.getCustomerID());
			
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean removeApplication(int id) {

		String sql = "DELETE FROM account_applications "
				+ "where application_id = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
