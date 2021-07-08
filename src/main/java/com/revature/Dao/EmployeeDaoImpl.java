package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Account;
import com.revature.models.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	
	final static Logger loggy = Logger.getLogger(EmployeeDaoImpl.class);
	@Override
	public boolean login(Employee employee)
	{
		boolean valid = false;
		String sql = "SELECT COUNT(*) FROM employee WHERE username = ? AND passcode = ?; ";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getUsername());
			ps.setString(2, employee.getPasscode());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getInt(1) != 0)
				{
					valid = true;
					String status_t = "User log in sucesses with username: " + employee.getUsername() + " and passcode: " + employee.getPasscode();
					loggy.info(status_t);
				}
				else
				{
					String status_f = "User fault to login with username: " + employee.getUsername() + " and passcode: " + employee.getPasscode();
					loggy.info(status_f);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;
	}
	
	
	@Override
	public void approve(Account a) {
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
					System.out.println("This account dose not exist!");
					String status_f = "Employee can not find the account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
					loggy.info(status_f);
					return;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "UPDATE account SET approval = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
			a.setApproval(true);
			String status_t = "The current account " + a.getAccount_name() + " approval status is " + a.isApproval();
			System.out.println(status_t);
			loggy.info(status_t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reject(Account a) {
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
					System.out.println("This account dose not exist!");
					String status_f = "Employee can not find the account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
					loggy.info(status_f);
					return;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "UPDATE account SET approval = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
			a.setApproval(false);
			String status_t = "The current account " + a.getAccount_name() + " approval status is " + a.isApproval();
			System.out.println(status_t);
			loggy.info(status_t);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void view_customer(Account a) {
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
					System.out.println("This account dose not exist!");
					String status_f = "Employee can not find the account with username: " + a.getUsername() + " and account name: " + a.getAccount_name();
					loggy.info(status_f);
					return;
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
			System.out.println(a);
			loggy.info(a.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
