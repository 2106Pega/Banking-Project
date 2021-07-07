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
		String sql = "SELECT COUNT(*) FROM bank WHERE username = ? AND passcode = ?; ";
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
		String sql = "INSERT INTO bank (username, passcode, validcustomer, account_name, balance) VALUES(?,?,?,?,?);";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPasscode());
			ps.setBoolean(3, u.isValid_c());
			ps.setString(4, null);
			ps.setDouble(5, 0.0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
