/*
 * DAO implementation for any user stories regarding the customer
 */
package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.DatabaseTools.ConnectionGetter;
import com.revature.Models.Account;

public class CustomerDAOImpl implements CustomerDAO {

	public boolean register(String username, String password) {
		String checkUsers = "SELECT * FROM users WHERE username = ?";
		//String checkNonUsers = "SELECT * FROM users WHERE username = ?";
		try
		{
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement userQuery = database.prepareStatement(checkUsers);
			//PreparedStatement nonUserQuery = database.prepareStatement(checkNonUsers);
			
			userQuery.setString(1, username);
			//nonUserQuery.setString(1, username);
			
			ResultSet userOutput = userQuery.executeQuery();
			//ResultSet nonUserOutput = nonUserQuery.executeQuery();
			
			if(userOutput.next())
				return false;
			String registerStatement = "INSERT INTO users(username, password) VALUES(?, ?)";
			PreparedStatement userInsert = database.prepareStatement(registerStatement);
			userInsert.setString(1, username);
			userInsert.setString(2, password);
			userInsert.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO register");
			System.out.println(e);
		}
		return false;
	}

	public boolean login(String username, String password) {
		try
		{
			String checkUsers = "SELECT * FROM users WHERE username = ? AND password = ?";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			
			PreparedStatement userQuery = database.prepareStatement(checkUsers);
			userQuery.setString(1, username);
			userQuery.setString(2, password);
			
			ResultSet output = userQuery.executeQuery();
			if(output.next())
				return true;
			return false;
			
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO login");
			System.out.println(e);
		}
		return false;
	}

	public boolean newAccount(String username, String type, int balance) {
		try
		{
			//might be handled in whatever uses this method
			if(balance < 0)
				return false;
			String insertAcc = "INSERT INTO unregistered_account(account_username, balance, account_type) VALUES(?, ?, ?)";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			
			PreparedStatement accUpdate = database.prepareStatement(insertAcc);
			accUpdate.setString(1, username);
			accUpdate.setInt(2, balance);
			accUpdate.setString(3, type);
			
			accUpdate.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO newAccount");
			System.out.println(e);
			return false;
		}		
	}

	public int viewBalance(int id, String username) {
		try
		{
			String viewBal = "SELECT balance FROM account WHERE account_id = ? AND account_username = ?";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement accQuery = database.prepareStatement(viewBal);
			accQuery.setInt(1, id);
			accQuery.setString(2, username);
			ResultSet output = accQuery.executeQuery();
			if(output.next())
				return output.getInt("balance");
			return -1;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO viewBalance");
			System.out.println(e);
		}		
		return -1;
	}

	public boolean withdraw(int amount, int id, String username) {
		try
		{
			if(amount < 0)
				return false;
			int finalBalance = 0;
			String findAcc = "SELECT balance FROM account WHERE account_id = ? AND account_username = ?";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement accQuery = database.prepareStatement(findAcc);
			accQuery.setInt(1, id);
			accQuery.setString(2, username);
			ResultSet output = accQuery.executeQuery();
			if(output.next())
				finalBalance = output.getInt("balance");
			finalBalance = finalBalance - amount;
			if(finalBalance < 0)
				return false;
			String updateAcc = "UPDATE account SET balance = ? WHERE account_id = ? AND account_username = ?";
			PreparedStatement accUpdate = database.prepareStatement(updateAcc);
			accUpdate.setInt(1, finalBalance);
			accUpdate.setInt(2, id);
			accUpdate.setString(3, username);
			accUpdate.executeUpdate();
			insertLog(id, id, "Withdraw", amount);
			return true;
			
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO withdraw");
			System.out.println(e);
		}		return false;
	}



	public boolean deposit(int amount, int id, String username) {
		try
		{
			if(amount < 0)
				return false;
			int finalBalance = 0;
			String findAcc = "SELECT balance FROM account WHERE account_id = ? AND account_username = ?";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement accQuery = database.prepareStatement(findAcc);
			accQuery.setInt(1, id);
			accQuery.setString(2, username);
			ResultSet output = accQuery.executeQuery();
			if(output.next())
				finalBalance = output.getInt("balance");
			else
				return false;
			finalBalance = finalBalance + amount;
			if(finalBalance < 0)
				return false;
			String updateAcc = "UPDATE account SET balance = ? WHERE account_id = ? AND account_username = ?";
			PreparedStatement accUpdate = database.prepareStatement(updateAcc);
			accUpdate.setInt(1, finalBalance);
			accUpdate.setInt(2, id);
			accUpdate.setString(3, username);
			accUpdate.executeUpdate();
			insertLog(id, id, "Deposit", amount);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO deposit");
			System.out.println(e);
		}		return false;
	}
	public boolean transfer(int amount, int source, int receiving, String username) 
	{
		try
		{
			if(amount < 0)
				return false;
			//withdraw block
			int finalSourceBalance = 0;
			String findSource = "SELECT balance FROM account WHERE account_id = ? AND account_username = ?";
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement sourceQuery = database.prepareStatement(findSource);
			sourceQuery.setInt(1, source);
			sourceQuery.setString(2, username);
			ResultSet sourceOutput = sourceQuery.executeQuery();
			if(sourceOutput.next())
				finalSourceBalance = sourceOutput.getInt("balance");
			finalSourceBalance = finalSourceBalance - amount;
			if(finalSourceBalance < 0)
				return false;
			String updateSource = "UPDATE account SET balance = ? WHERE account_id = ? AND account_username = ?";
			PreparedStatement sourceUpdate = database.prepareStatement(updateSource);
			sourceUpdate.setInt(1, finalSourceBalance);
			sourceUpdate.setInt(2, source);
			sourceUpdate.setString(3, username);
			
			//deposit block
			int finalReceiveBalance = 0;
			String findReceive = "SELECT balance FROM account WHERE account_id = ?";
			PreparedStatement accQuery = database.prepareStatement(findReceive);
			accQuery.setInt(1, receiving);
			ResultSet receiveOutput = accQuery.executeQuery();
			if(receiveOutput.next())
				finalReceiveBalance = receiveOutput.getInt("balance");
			else
				return false;
			finalReceiveBalance = finalReceiveBalance + amount;
			if(finalReceiveBalance < 0)
				return false;
			String updateAcc = "UPDATE account SET balance = ? WHERE account_id = ?";
			PreparedStatement receiveUpdate = database.prepareStatement(updateAcc);
			receiveUpdate.setInt(1, finalReceiveBalance);
			receiveUpdate.setInt(2, receiving);
			
			sourceUpdate.executeUpdate();
			receiveUpdate.executeUpdate();
			insertLog(source, receiving, "Transfer", amount);
			return true;
			
			
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO transfer");
			System.out.println(e);
		}
		return false;
	}
	
	public List<Account> viewAccounts(String username) {
		try
		{
			List<Account> retVal = new ArrayList<Account>();
			String viewAcc = "SELECT * FROM account WHERE account_username = ?";
			
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement viewAllAcc = database.prepareStatement(viewAcc);
			viewAllAcc.setString(1, username);
			ResultSet output = viewAllAcc.executeQuery();
			while(output.next())
			{
				retVal.add(new Account(output.getInt("account_id"), output.getString("account_username"), output.getInt("balance"), output.getString("account_type")));
			}
			return retVal;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO viewAccounts");
			System.out.println(e);
		}
		return null;

	}
	
	private void insertLog(int source, int receiving, String type, int amount)
	{
		try
		{
			String insert = "INSERT INTO log(source_id, receiving_id, trans_type, amount) VALUES(?,?,?,?)";
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement logInsert = database.prepareStatement(insert);
			logInsert.setInt(1, source);
			logInsert.setInt(2, receiving);
			logInsert.setString(3, type);
			logInsert.setInt(4, amount);
			
			logInsert.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in CustomerDAO insertLog");
			System.out.println(e);
		}
	}

}
