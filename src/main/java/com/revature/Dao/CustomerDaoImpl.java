package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Account;

public class CustomerDaoImpl implements CustomerDao {
	
	final static Logger loggy = Logger.getLogger(CustomerDaoImpl.class);
	
	@Override
	public boolean init(Account a)
	{
		String sql_v = "SELECT COUNT(*) FROM account WHERE username = ? AND account_name = ?; ";
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_v);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getInt(1) == 0)
				{
					System.out.println("This account does not exist!");
					String status_f = "Employee can not find the account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
					loggy.info(status_f);
					return false;
				}

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "SELECT * FROM account WHERE username = ? AND account_name = ?;";
		
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
		return true;
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
					String status_f = "User fault to apply account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
					loggy.info(status_f);
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
			ps.setBoolean(3, false);
			ps.setDouble(4, a.getBalance());
			ps.execute();
			System.out.println("Please wait the employee to approve your new account.");
			String status_t = "User successfuly applied account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
			loggy.info(status_t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	
	public void view_balance(Account a) {
		if(init(a))
		{
			if(a.isApproval() == false)
			{
				System.out.println("This account " + a.getUsername() + " is not approved yet, please wait employee to approve it.");
				return;
			}
			else
			{
				System.out.println("The current balance of account " + a.getAccount_name() + " is " + a.getBalance());
			}
		}
	}

	@Override
	public void deposite(Account a, double amount) {
		double balance;
		if(amount < 0)
		{
			System.out.println("Invalid input!");
			loggy.info("Customer tried to withdraw invalid amout of money from account.");
			return;		
		}
		
		if(init(a))
		{
			if(a.isApproval() == false)
			{
				String status_f = "This account " + a.getUsername() + " is not approved yet, please wait employee to approve it.";
				System.out.println(status_f);
				loggy.info(status_f);
				return;
			}
			
			balance = a.getBalance() + amount;
			String sql = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, balance);
				ps.setString(2, a.getUsername());
				ps.setString(3, a.getAccount_name());
				ps.executeUpdate();
				a.setBalance(balance);
				System.out.println("The current balance of account " + a.getAccount_name() + " is " + a.getBalance());
				String status_t = "The user " + a.getUsername() + " deposit " + amount + " to account name " + a.getAccount_name() + " and the current balance is " + a.getBalance();
				loggy.info(status_t);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void withdraw(Account a, double amount) {
		double balance;
		
		if(init(a)) {
			
			if(a.isApproval() == false)
			{
				String status_f = "This account " + a.getUsername() + " is not approved yet, please wait employee to approve it.";
				System.out.println(status_f);
				loggy.info(status_f);
				return;
			}
			
			if(amount < 0 || amount > a.getBalance())
			{
				System.out.println("Invalid input!");
				loggy.info("Customer tried to withdraw invalid amout of money from account.");
				return;		
			}
			
			
			balance = a.getBalance() - amount;
			String sql = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, balance);
				ps.setString(2, a.getUsername());
				ps.setString(3, a.getAccount_name());
				ps.executeUpdate();
				a.setBalance(balance);
				System.out.println("The current balance of account " + a.getAccount_name() + " is " + a.getBalance());
				String status_t = "The user " + a.getUsername() + " withdraw " + amount + " to account name " + a.getAccount_name() + " and the current balance is " + a.getBalance();
				loggy.info(status_t);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void post(Account a, Account b, double amount) {
		double balance_a;
		double balance_b;
		if(init(a) && init(b))
		{
			if(a.isApproval() == false)
			{
				System.out.println("This account " + a.getUsername() + " is not approved yet, please wait employee to approve it.");
				return;
			}
			
			if(b.isApproval() == false)
			{
				System.out.println("This account " + b.getUsername() + " is not approved yet, please wait employee to approve it.");
				return;
			}
			
			if(amount < 0 || amount > a.getBalance())
			{
				System.out.println("Invalid input!");
				return;		
			}
			
			balance_a = a.getBalance() - amount;
			String sql_a = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql_a);
				ps.setDouble(1, balance_a);
				ps.setString(2, a.getUsername());
				ps.setString(3, a.getAccount_name());
				ps.executeUpdate();
				a.setBalance(balance_a);
				System.out.println("The current balance of account " + a.getAccount_name() + " is " + a.getBalance());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			balance_b = b.getBalance() + amount;
			String sql_b = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql_b);
				ps.setDouble(1, balance_b);
				ps.setString(2, b.getUsername());
				ps.setString(3, b.getAccount_name());
				ps.executeUpdate();
				b.setBalance(balance_b);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String status_t = "Customer " + a.getUsername() + " from account name " + a.getAccount_name() + " transfer " + amount + " to customer " + b.getUsername() + " account name " + b.getAccount_name(); 
			loggy.info(status_t);
		}
	}

	@Override
	public void accept(Account a, Account b, double amount) {
		double balance_a;
		double balance_b;
		
		if(init(a) && init(b)){
		
			if(a.isApproval() == false)
			{
				System.out.println("This account " + a.getUsername() + " is not approved yet, please wait employee to approve it.");
				return;
			}
			
			if(b.isApproval() == false)
			{
				System.out.println("This account " + b.getUsername() + " is not approved yet, please wait employee to approve it.");
				return;
			}
			
			if(amount < 0 || amount > b.getBalance())
			{
				System.out.println("Invalid input!");
				return;		
			}
	
	
			balance_a = a.getBalance() + amount;
			String sql_a = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql_a);
				ps.setDouble(1, balance_a);
				ps.setString(2, a.getUsername());
				ps.setString(3, a.getAccount_name());
				ps.executeUpdate();
				a.setBalance(balance_a);
				System.out.println("The current balance of account " + a.getAccount_name() + "is " + a.getBalance());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	
			balance_b = b.getBalance() - amount;
			String sql_b = "UPDATE account SET balance = ? WHERE username = ? AND account_name = ?;";
			try(Connection conn = ConnectionFactory.getConnection())
			{
				PreparedStatement ps = conn.prepareStatement(sql_b);
				ps.setDouble(1, balance_b);
				ps.setString(2, b.getUsername());
				ps.setString(3, b.getAccount_name());
				ps.executeUpdate();
				b.setBalance(balance_b);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String status_t = "Customer " + a.getUsername() + " from account name " + a.getAccount_name() + " receive " + amount + " to customer " + b.getUsername() + " account name " + b.getAccount_name(); 
			loggy.info(status_t);
		}
	}

}
