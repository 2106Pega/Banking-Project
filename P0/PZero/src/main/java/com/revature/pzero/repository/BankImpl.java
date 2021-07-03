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
	public boolean newUser(User u) {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, u.getFirstName());
//			cStatement.setDouble(2, 0.0);
			
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
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public Account viewAccountByAccountId(int accountId) {
		Account account = null;
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, u.getFirstName());
			
			ResultSet result = cStatement.executeQuery();
			
			if(result.next()) {
				account = new Account();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return account;
	}

	@Override
	public List<Account> viewAccountByUserID(int userID) {
		List<Account> listOfAccounts = null;
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
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
	public List<Account> viewAllAccounts() {
		List<Account> listOfAccounts = null;
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
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
	public boolean withdraw(Account a) {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean deposit(Account a) {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean transfer(Account a, Account b) {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean closeAccount() {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean deleteUser() {
		boolean success = false;
		
		String sqlString = "";
		
		try(Connection conn = ConnectionPoint.getConnection()){
			
			CallableStatement cStatement = conn.prepareCall(sqlString);
//			cStatement.setString(1, "");
			
			cStatement.execute();
			success = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	
	
}