package com.revature.pzero.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.util.ConnectionPoint;

public class BankImpl implements Bank{

	public BankImpl() {
		super();
	}

	@Override
	public boolean newUser(User u, String userType) {
		boolean success = false;
		
		String sqlQuery = "insert into user_table(first_name, last_name, user_type, account_username, account_password) values (?, ?, ?, ?, ?);";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setString(1, u.getFirstName());
			cStatement.setString(2, u.getLastName());
			cStatement.setString(3, userType);
			cStatement.setString(4, u.getUserName());
			cStatement.setString(5,  u.getUserPassword());
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean newAccount(Account a) {
		boolean success = false;
		
		String sqlQuery = "insert into account_table(account_balance, account_name, account_approved) values (?, ?, ?);";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setDouble(1, a.getBalance());
			cStatement.setString(2, a.getNickName());
			cStatement.setBoolean(3, a.isApproved());
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	@Override
	public User login(String username, String password) {
		User user = null;
		String sqlQuery = "select * from user_table where account_username = ? and account_password = ?;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setString(1, username);
			cStatement.setString(1, password);
			
			ResultSet result = cStatement.executeQuery();
			
			if(result.next()) {
				user = new User(result.getInt("user_id"),
						result.getString("first_Name"),
						result.getString("last_Name"),
						result.getString("user_type"),
						result.getString("account_username"),
						result.getString("account_password"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public Account viewAccountByAccountId(int accountId) {
		Account account = null;
		String sqlQuery = "select * from account_table where account_id = ?;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setInt(1, accountId);
			
			ResultSet result = cStatement.executeQuery();
			
			if(result.next()) {
				account = new Account(result.getInt("account_id"),
						result.getDouble("account_balance"),
						result.getString("account_name"),
						result.getBoolean("account_approved"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return account;
	}

	@Override
	public List<Account> viewAccountByUserID(int userID) {
		List<Account> listOfAccounts = null;
		String sqlQuery = "select * from account_table where account_id in (select account_id from account_to_user where user_id = ?);";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setInt(1, userID);
			
			ResultSet result = cStatement.executeQuery();
			
			while(result.next()) {
				Account a = new Account(); //need to populate account
				listOfAccounts.add(a);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return listOfAccounts;
	}

	@Override
	public List<Account> viewAllAccounts() {
		List<Account> listOfAccounts = null;
		String sqlQuery = "select * from account_table;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
//			cStatement.setString(1, u.getFirstName());
			
			ResultSet result = cStatement.executeQuery();
			
			while(result.next()) {
				Account a = new Account(); //need to populate account
				listOfAccounts.add(a);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return listOfAccounts;
	}

	@Override
	public boolean withdraw(Account a, double newAmount) {
		return moneyAdjustment(a, newAmount);
	}

	@Override
	public boolean deposit(Account a, double newAmount) {
		return moneyAdjustment(a, newAmount);
	}
	
	private boolean moneyAdjustment(Account a, double newAmount) {
		boolean success = false;
		
		String sqlQuery = "update account_table set account_balance = ? where account_id = ?;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setDouble(1, a.getBalance());
			cStatement.setInt(2,  a.getId());
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	///////////////////////////////////////////////////////////////////////
	@Override
	public boolean transfer(Account a, Account b, double transferAmount) {
		boolean success = false;
		
		String sqlQuery = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean closeAccount(Account a) {
		boolean success = false;
		
		String sqlQuery = "delete from account_to_user where account_id = ?;";
		String sqlQuery2 = "delete from account_table where account_id = ?;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setInt(1, a.getId());
			
			
			CallableStatement cStatementTwo = conn.prepareCall(sqlQuery2);
			cStatementTwo.setInt(1, a.getId());
			
			cStatement.execute();
			cStatementTwo.execute();
			
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean deleteUser(User u) {
		boolean success = false;
		
		String sqlQuery = "delete from account_to_user where user_id = ?;";
		String sqlQuery2 = "delete from user_table where user_id = ?;";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlQuery);
			cStatement.setInt(1, u.getId());
			
			CallableStatement cStatementTwo = conn.prepareCall(sqlQuery2);
			cStatementTwo.setInt(1,  u.getId());
			
			cStatement.execute();
			cStatementTwo.execute();
			
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public List<String> viewUsernames() {  //returns list of usernames
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserFromAccountId(int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> viewUnapprovedAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateApproval(Account a) {
		// TODO Auto-generated method stub
		return false;
	}


	
	
}