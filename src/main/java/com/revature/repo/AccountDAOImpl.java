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
import com.revature.util.ConnectionFactory;

public class AccountDAOImpl implements AccountDAO{

	public boolean DepositIntoAccount(int accountID, double depositAmount) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "CREATE OR REPLACE FUNCTION deposit_money(id1 integer, b1 double precision) \r\n"
				+ "RETURNS boolean AS $$\r\n"
				+ "        BEGIN\r\n"
				+ "				update accounts set balance = (b1 + balance) where account_id = id1;\r\n"
				+ "				return 'TRUE';\r\n"
				+ "        END;\r\n"
				+ "$$ LANGUAGE plpgsql;\r\n"
				+ "\r\n"
				+ "select deposit_money(?, ?)";
		
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, accountID);
			preparedStatement.setDouble(2, depositAmount);
			preparedStatement.execute();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean TransferMoneyBetweenAccounts(double transferAmount, int targetAccountID, int sourceAccountID, Customer customer) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "CREATE OR REPLACE FUNCTION transfer_money(b double precision, id1 integer, id2 integer) \r\n"
				+ "RETURNS boolean AS $$\r\n"
				+ "        BEGIN\r\n"
				+ "			update accounts set balance = (balance - b) where account_id = id1 and (balance-b >= 0);\r\n"
				+ "			update accounts set balance = (balance + b) where account_id = id2;\r\n"
				+ "			return 'TRUE';\r\n"
				+ "        END;\r\n"
				+ "$$ LANGUAGE plpgsql;\r\n"
				+ "\r\n"
				+ "select transfer_money(?, ?, ?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDouble(1, transferAmount);
			preparedStatement.setInt(2, sourceAccountID);
			preparedStatement.setInt(3, targetAccountID);
			preparedStatement.execute();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Account GetAccountByAccountID(int accountID) {
		Connection conn = ConnectionFactory.getConnection();
		Account account = null;
		String sql = "select * from accounts where account_id = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, accountID);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
				account = new Account(rs.getInt("account_id"), rs.getInt("customer_id"), rs.getDouble("balance"), rs.getBoolean("approval"));
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public boolean WithdrawFromAccount(int accountID, double withdrawAmount) {
		// TODO Auto-generated method stub
		if(GetAccountByAccountID(accountID).getBalance() < withdrawAmount)
			return false;
		
		Connection conn = ConnectionFactory.getConnection();
		String sql = "CREATE OR REPLACE FUNCTION withdraw_money(b1 double precision, id1 integer) \r\n"
				+ "RETURNS boolean AS $$\r\n"
				+ "        BEGIN\r\n"
				+ "				update accounts set balance = (balance - b1) where account_id = id1 and (balance-b1>=0);\r\n"
				+ "				return 'TRUE';\r\n"
				+ "        END;\r\n"
				+ "$$ LANGUAGE plpgsql;\r\n"
				+ "\r\n"
				+ "select withdraw_money(?, ?)";
		try {
			//create sql statement
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDouble(1, withdrawAmount);
			preparedStatement.setInt(2, accountID);
			preparedStatement.execute();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Account> GetAccountsByCustomerID(int customerID) {
		Connection conn = ConnectionFactory.getConnection();
		Account account;
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "select * from accounts where customer_id = '" + customerID + "';";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
                account = new Account(rs.getInt("account_id"), rs.getInt("customer_id"), rs.getDouble("balance"), rs.getBoolean("approval"));
                accounts.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public boolean InsertAccount(Account account) {
		Connection conn = ConnectionFactory.getConnection();;
		try {
			String sql = "insert into accounts (customer_id, balance, approval) values ('" + account.getCustomer_id() + "', '" + account.getBalance() + "', '" + account.isApproved() + "')";
			Statement s = conn.createStatement();
			s.execute(sql);
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
