package com.revaturebank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revaturebank.model.User;
import com.revaturebank.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {

	@Override
	public void saveUser(User user) throws Exception{
	
		String sql = "INSERT INTO login (user_name,password,user_type) VALUES(?,?,?)";

		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,user.getUserName());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getUserType());
		
			ps.execute();
			
			
		}catch (SQLException e) {
			throw new Exception("Unable to save user info to DB:: " + e.getMessage());
		} 
			
	}

	@Override
	public User getUser(String userName) throws Exception {	
		String sql = "SELECT * FROM login WHERE user_name = ?";
		User user = new User();
		
		try(Connection conn = ConnectionFactory.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,userName);
			ps.execute();
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getString("user_type"));
			}

		}catch (SQLException e) {
			throw new Exception("Unable to retrive user info from DB:: " + e.getMessage());
		} 	
		return user;
	}

}
