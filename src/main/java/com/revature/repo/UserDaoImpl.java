package com.revature.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.Bank.BankManager;
import com.revature.models.Fund;
import com.revature.util.ConnectionFactory;

public class UserDaoImpl implements UserDAO {
	private final Logger loggy = Logger.getLogger(UserDaoImpl.class);
	
	public UserDaoImpl() {
		super();
		loggy.setLevel(Level.ERROR);
	}

	@Override
	public boolean insertUser(User newUser) {
		loggy.info("Starting to insert new user into db");
		boolean result = false;
		//Create a SQL query
		String sql = "INSERT INTO users(user_name, user_pass, first_name,"
				+ " last_name, active, employee) VALUES ("
				+ "?, ?, ?, ?, FALSE, FALSE);";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			loggy.info("Created the db connection");
			//Prepare statement object to insert new user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newUser.getUser_name());
			ps.setString(2, newUser.getUser_pass());
			ps.setString(3, newUser.getFirst_name());
			ps.setString(4, newUser.getLast_name());
			
			//Attempt to execute query into db
			ps.execute();
			result = true;
			loggy.info("Successfully added ");
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully created new user that needs to be approved.
		loggy.info("Successfully created new user that needs to be approved now.");
		return result;
	}

	@Override
	public User selectUserByUsername(String username) {
		loggy.info("Starting to select user by username");
		User user = null;
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE user_name = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fruit into an object
			if(rs.next()) {
				user = new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully retrieved user based on username.
		loggy.info("Successfully retrieved user based on user_name:" + username);
		return user;
	}

	@Override
	public User selectUserById(int id) {
		loggy.info("Starting to select user based on user id");
		User user = null;
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE user_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fruit into an object
			if(rs.next()) {
			user = new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully retrieved user based on user_id.
		loggy.info("Successfully retrieved user based on user_id#" + id);
		return user;
	}

	@Override
	public List<User> selectUnapprovedUsers() {
		loggy.info("Starting to select all unapproved users from db");
		List<User> unapprovedList = new ArrayList<User>();
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE active = FALSE;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned users into an object
			while(rs.next()) {
				unapprovedList.add(new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully return list of unapproved users.
		loggy.info("Successfully returned List of all unapproved user accounts");
		return unapprovedList;
	}

	@Override
	public boolean updateUserInformation(User user) {
		loggy.info("Starting to update user information in db");
		boolean result = false;
		
		//Create a SQL query
		String sql = "UPDATE users SET user_name = ?, user_pass = ?, first_name = ?,"
				+ " last_name = ?, active = ?, employee = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_name());
			ps.setString(2, user.getUser_pass());
			ps.setString(3, user.getFirst_name());
			ps.setString(4, user.getLast_name());
			ps.setBoolean(5, user.isActive());
			ps.setBoolean(6, user.isEmployee());
			
			//Attempt to execute query into db
			ps.executeUpdate();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		loggy.info("Successfully updated the user account");
		return result;
	}

	@Override
	public boolean deleteUser(User user) {
		loggy.info("Starting to delete user from db");
		boolean result = false;
		// Make the sql query to delete the user
		String sql = "DELETE FROM users WHERE user_id = ?;";
		
		//Create a connection to the database
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());

			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		loggy.info("Successfully deleted the user");
		return result;
	}

	@Override
	public List<Fund> selectFundsById(int id) {
		loggy.info("Starting to select all the user's funds");
		List<Fund> funds = new ArrayList<Fund>();
		
		String sql = "SELECT * FROM funds WHERE fund_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			funds.add(new Fund(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully returned all funds related to one account.
		loggy.info("Successfully returned all funds related to the account");
		return funds;
	}

	@Override
	public List<Fund> selectFundsByUsername(String username) {
		loggy.info("Selecting all funds associated to the user through username");
		List<Fund> funds = new ArrayList<Fund>();
		User user = selectUserByUsername(username);
		
		String sql = "SELECT * FROM funds WHERE fund_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			funds.add(new Fund(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully returned all funds related to one account.
		loggy.info("Successfully returned all funds related to the account");
		return funds;
	}

	@Override
	public List<Fund> selectFundsByUser(User user) {
		loggy.info("Starting to select all funds related to user from db");
		List<Fund> funds = new ArrayList<Fund>();
		
		String sql = "SELECT * FROM funds WHERE fund_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			funds.add(new Fund(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			loggy.error(e);
		}
		//Successfully returned all funds related to one account.
		loggy.info("Successfully returned all funds related to the account");
		return funds;
	}
	
	
}
