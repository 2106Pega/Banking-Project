package com.revature.dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Accounts;
import com.revature.models.Users;
import com.revature.util.ConnectionDriver;



public class UsersDaoImpl implements UsersDao {

	@Override
	public boolean createUser(Users s) {
		

		String sql = "INSERT INTO users (user_name, first_name, last_name) VALUES(?,?,?)";
		
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getUser_name());
			ps.setString(2, s.getFirstName());
			ps.setString(3, s.getLastName());
	
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			
		} 
		
		
		return true;
	}

	@Override
	public List<Users> selectAllUsers() {
		List<Users> databaseUsers = new ArrayList();
		String sql = "select * from users;";
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				databaseUsers.add(new Users(
						rs.getInt(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4))
						);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return databaseUsers;
	}

	@Override
	public Users selectUserByUsername(String userName) {
		Users dbUser = null;
		
		String sql = "select * from users where user_name = ? ;";
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			
			// we expect rows and columns back from our db
			ResultSet rs = ps.executeQuery();
			
			// we want to convert those columns and rows into objects.
			while(rs.next()) {
				dbUser = new Users(rs.getInt(1), rs.getString(2),
						rs.getString(3),rs.getString(4));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return dbUser;
	}


	@Override
	public Users selectAccountsById(int id) {
		Users dbUser = null;
		
		String sql = "select * from users where user_id = ? ;";
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			// we expect rows and columns back from our db
			ResultSet rs = ps.executeQuery();
			
			// we want to convert those columns and rows into objects.
			while(rs.next()) {
				dbUser = new Users(rs.getInt(1), rs.getString(2),
						rs.getString(3),rs.getString(4));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return dbUser;
	}

}
