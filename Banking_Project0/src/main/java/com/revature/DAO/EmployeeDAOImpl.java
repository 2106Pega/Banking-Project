/*
 * DAO implementation for any user stories regarding the Employee
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
import com.revature.Models.Log_Entry;
import com.revature.Models.Unregistered_Account;

public class EmployeeDAOImpl implements EmployeeDAO {

	public Unregistered_Account checkPendingAccount() {
		String nonAccQuery = "SELECT * FROM unregistered_account LIMIT 1";
		try
		{
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement accQ = database.prepareStatement(nonAccQuery);
			ResultSet output = accQ.executeQuery();
			if(output.next())
			{
				Unregistered_Account retVal = new Unregistered_Account(output.getInt("request_id"), output.getString("account_username"),
						output.getInt("balance"), output.getString("account_type"));
				return retVal;
			}
			return null;
			
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in EmployeeDAO checkPendingAccount");
			System.out.println(e);
		}
		return null;

	}

	public void approve(Unregistered_Account acc) {
		try
		{
			String AccInsert = "INSERT INTO account(account_username, balance, account_type) VALUES(?,?,?)";
			String nonAccDel = "DELETE FROM unregistered_account WHERE request_id = ?";
			
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement accountUpdate = database.prepareStatement(AccInsert);
			PreparedStatement nonAccDelete = database.prepareStatement(nonAccDel);
			
			accountUpdate.setString(1, acc.getAccount_username());
			accountUpdate.setInt(2, acc.getBalance());
			accountUpdate.setString(3, acc.getAccount_type());
			
			nonAccDelete.setInt(1, acc.getRequest_id());
			
			accountUpdate.executeUpdate();
			nonAccDelete.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in EmployeeDAO approve");
			System.out.println(e);
		}

	}

	public void reject(Unregistered_Account acc) {
		try
		{
			String nonAccDel = "DELETE FROM unregistered_account WHERE request_id = ?";
			
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement nonAccDelete = database.prepareStatement(nonAccDel);
			
			nonAccDelete.setInt(1, acc.getRequest_id());
			
			nonAccDelete.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in EmployeeDAO reject");
			System.out.println(e);
		}

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
			System.out.println("SQLException in EmployeeDAO viewAccounts");
			System.out.println(e);
		}
		return null;

	}
	//this might also be list
	public List<Log_Entry> viewLog() {
		try
		{
			List<Log_Entry> retVal = new ArrayList<Log_Entry>();
			String viewLogg = "SELECT * FROM log";
			
			ConnectionGetter connection = new ConnectionGetter();
			Connection database = connection.getConnection();
			PreparedStatement viewAllLog = database.prepareStatement(viewLogg);
			ResultSet output = viewAllLog.executeQuery();
			while(output.next())
			{
				retVal.add(new Log_Entry(output.getInt("trans_id"), output.getInt("source_id"), output.getInt("receiving_id"), output.getString("trans_type"), output.getInt("amount")));
			}
			return retVal;
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in EmployeeDAO viewLog");
			System.out.println(e);
		}
		return null;

	}

	public List<Account> viewAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

}
