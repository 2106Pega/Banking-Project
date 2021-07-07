package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Account;

public class EmployeeDaoImpl implements EmployeeDao {

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
				if(rs.getInt(1) != 0)
				{
					System.out.println("This account is already exist!");
					return;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "UPDATE bank SET approval = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
			a.setApproval(true);;
			System.out.println("The current balance of account " + a.getAccount_name() + "approval status is " + a.isApproval());
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
				if(rs.getInt(1) != 0)
				{
					System.out.println("This account is already exist!");
					return;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "UPDATE bank SET approval = ? WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getAccount_name());
			ps.executeUpdate();
			a.setApproval(false);;
			System.out.println("The current balance of account " + a.getAccount_name() + "approval status is " + a.isApproval());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void view_customer(Account a) {
		// TODO Auto-generated method stub

	}

}
