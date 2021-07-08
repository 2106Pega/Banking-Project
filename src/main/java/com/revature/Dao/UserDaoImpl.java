package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.models.User;

public class UserDaoImpl implements UserDao {

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
			if(rs.getInt(1) != 0)
			{
				valid = true;
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
			if(rs.getInt(1) == 0)
			{
				result = true;
			}
			else
			{
				System.out.println("This account is already exist.");
				return result;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
