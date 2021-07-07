package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDAOImpl implements UserDAO{

	//Returns the user in the database via username and password.
	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			PreparedStatement s = conn.prepareStatement("select * from users where username = ? and password = ?");
			s.setString(1, username);
			s.setString(2, password);
			ResultSet rs = s.executeQuery();
			if (rs.next() ) {
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
			} 
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	//Inserts the user into the database
	public boolean insertUser(User user) {
		Connection conn;
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "insert into users (username, password) values ('" + user.getUsername() + "', '" + user.getPassword() + "')";
			Statement s = conn.createStatement();
			s.execute(sql);
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}
