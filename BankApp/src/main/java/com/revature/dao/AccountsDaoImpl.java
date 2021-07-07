package com.revature.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Accounts;
import com.revature.models.Users;
import com.revature.util.ConnectionDriver;



public class AccountsDaoImpl implements AccountsDao {

	@Override
	public boolean createAccount(Accounts s) {
		

		String sql = "INSERT INTO accounts (account_name, account_banlance, account_approved, user_id) VALUES(?,?,?,?)";
		
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getAccountName());
			ps.setInt(2, (int) s.getBanlance());
			ps.setBoolean(3, false);
			ps.setInt(4, s.getUserID());
	
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			
		} 
		
		
		return true;
	}

	@Override
	public List<Accounts> selectAccountsByUserId(int userId) {
		
		List<Accounts> databaseAccounts = new ArrayList();
		
		String sql = "select * from accounts where user_id = ? ;";
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			
			// we expect rows and columns back from our db
			ResultSet rs = ps.executeQuery();
			
			// we want to convert those columns and rows into objects.
			while(rs.next()) {
				databaseAccounts.add(new Accounts(
						rs.getInt(1),
						rs.getString(2), 
						rs.getInt(3), 
						rs.getBoolean(4),
						rs.getInt(5),
						null));
			}
			
		} catch(SQLException e) {
			
		}
		
		return databaseAccounts;
	}


	@Override
	public boolean approveAccounts(int id) {


		String sql = "update accounts set account_approved = true where accounts_id = ? ;";
		
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//execute the query
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return true;
	}


	@Override
	public boolean updateAccounts(Accounts account, double amount) {


		String sql = "update accounts set account_banlance = ? where accounts_id = ? ;";
		
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, amount + account.getBanlance());
			ps.setInt(2, account.getAccountID());
			
			//execute the query
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return true;
	}

	@Override
	public Accounts selectAccountByAccountId(int AccountId) {
		
		Accounts databaseAccount = null;
		
		String sql = "select * from accounts where accounts_id = ? ;";
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, AccountId);
			
			// we expect rows and columns back from our db
			ResultSet rs = ps.executeQuery();
			
			// we want to convert those columns and rows into objects.
			while(rs.next()) {
				databaseAccount = new Accounts(
						rs.getInt(1),
						rs.getString(2), 
						rs.getInt(3), 
						rs.getBoolean(4),
						rs.getInt(5),
						null);
			}
			
		} catch(SQLException e) {
			
		}
		
		return databaseAccount;
	}

}
