package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.connection.ConnectionFactory;
import com.revature.models.User;

public class UserDaoImpl implements UserDao {

	final static Logger loggy = Logger.getLogger(UserDaoImpl.class);
	
	@Override
	public boolean login(User u) {
		boolean valid = false;
		String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND passcode = ?; ";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPasscode());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getInt(1) != 0)
				{
					valid = true;
					String status_t = "User log in sucesses with username: " + u.getUsername() + " and passcode: " + u.getPasscode();
					loggy.info(status_t);
				}
				else
				{
					String status_f = "User fault to login with username: " + u.getUsername() + " and passcode: " + u.getPasscode();
					loggy.info(status_f);
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;
	}

	@Override
	public boolean apply(User u) {
		boolean result = false;
		
		String sql_v = "SELECT COUNT(*) FROM users WHERE username = ?; ";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql_v);
			ps.setString(1, u.getUsername());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) == 0)
				{
					result = true;
				}
				else
				{
					System.out.println("This account is already exist.");
					String status_f = "User fault to apply account with username: " + u.getUsername() + " and passcode: " + u.getPasscode();
					loggy.info(status_f);
					return result;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO users (username, passcode) VALUES(?,?);";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPasscode());
			ps.executeUpdate();
			System.out.println("The customer account creation successes");
			String status_t = "User successfuly applied account with username: " + u.getUsername() + " and passcode: " + u.getPasscode();
			loggy.info(status_t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
