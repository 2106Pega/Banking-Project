package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.PasswordMismatchException;
import com.revature.exceptions.UserNotFoundExecption;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDaoImpl implements UserDAO {

	@Override
	public User selectUserByUsername(String username) throws UserNotFoundExecption {

		//SQL query
		String sql = "SELECT * FROM user_table WHERE username = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User userByUsername = new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("user_password"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getInt("account_type"));
				return userByUsername;
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User selectUserByPassword(String username, String password) throws PasswordMismatchException {

		//SQL query
		String sql = "SELECT * FROM user_table WHERE username = ? AND user_password = ?;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2,  password);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User userByPassword = new User(
						rs.getInt("user_id"),
						rs.getString("username"),
						rs.getString("user_password"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getInt("account_type"));
				return userByPassword;
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void createNewUser(String username, String password, String firstName, String lastName, int accountType) {


		//SQL query
		String sql = "INSERT INTO user_table (username, user_password, first_name, last_name, account_type) VALUES(?, ?, ?, ?, ?)";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.setInt(5, accountType);

			//execute the query
			ps.execute();

		}

		catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public List<User> selectAllUsers() {

		List<User> userList = new ArrayList<>();

		//SQL query
		String sql = "SELECT user_id, first_name, last_name FROM user_table WHERE account_type = 1;";

		//Creating connection
		try(Connection conn = ConnectionFactory.getConnection();) {

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();


			while (rs.next()) {
				userList.add(new User(
						rs.getInt("user_id"),
						rs.getString("first_name"),
						rs.getString("last_name")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 

		return userList;
	}

}

