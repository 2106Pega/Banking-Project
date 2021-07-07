package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Account;

public class CustomerDaoImpl implements CustomerDao {
	
	@Override
	public void init(Account a)
	{
		String sql = "SELECT * FROM bank WHERE username = ? AND account_name = ?;";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				a.setApproval(rs.getBoolean("approval"));
				a.setBalance(rs.getDouble("balance"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean apply_account(Account a) {
		boolean valid = false;
		String sql = "INSERT INTO account(username, account_name, approval, balance) values (?, ?, ?, ?)";
		String sql_v = "SELECT COUNT(*) FROM account WHERE username = ? AND account_name = ?; ";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_v);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getInt(1) != 0)
				{
					System.out.println("This account is already exist!");
					return valid;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ps.setBoolean(3, a.isApproval());
			ps.setDouble(4, a.getBalance());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	
	public void view_balance(Account a) {
		double balance = 0.0;
		boolean approval = false;
		String sql = "SELECT balance, approval FROM bank WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				balance = rs.getDouble("balance");
				approval = rs.getBoolean("approval");
			}
			
			if(!approval)
			{
				a.setBalance(balance);
			}
			else
			{
				System.out.println("This account is not approved yet, please wait employee to approve it.");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deposite(Account a, double amount) {
		double balance;
		if(amount < 0)
		{
			System.out.println("Invalid input!");
			return;		
		}
		
		view_balance(a);
		
		balance = a.getBalance() + amount;
		String sql = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, balance);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void withdraw(Account a, double amount) {
		double balance;
		if(amount < 0 || amount > a.getBalance())
		{
			System.out.println("Invalid input!");
			return;		
		}
		
		view_balance(a);
		balance = a.getBalance() - amount;
		String sql = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, balance);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void post(Account a, Account b, double amount) {
		double balance_a;
		double balance_b;
		if(amount < 0 || amount > a.getBalance())
		{
			System.out.println("Invalid input!");
			return;		
		}
		
		view_balance(a);
		balance_a = a.getBalance() - amount;
		String sql_a = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_a);
			ps.setDouble(1, balance_a);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		view_balance(b);
		balance_b = b.getBalance() + amount;
		String sql_b = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_b);
			ps.setDouble(1, balance_b);
			ps.setString(2, b.getUsername());
			ps.setString(3, b.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void accept(Account a, Account b, double amount) {
		double balance_a;
		double balance_b;
		if(amount < 0 || amount > b.getBalance())
		{
			System.out.println("Invalid input!");
			return;		
		}

		view_balance(a);
		balance_a = a.getBalance() + amount;
		String sql_a = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_a);
			ps.setDouble(1, balance_a);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		view_balance(b);
		balance_b = b.getBalance() - amount;
		String sql_b = "UPDATE bank SET balance = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_b);
			ps.setDouble(1, balance_b);
			ps.setString(2, b.getUsername());
			ps.setString(3, b.getAccount_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		


	}

}
