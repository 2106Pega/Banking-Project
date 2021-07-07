/*
 * Extra helper DAO class for when the user logs in for the first time 
 */

package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.DatabaseTools.ConnectionGetter;
import com.revature.Models.User;

public class LoginDAOImpl implements LoginDAO {

	public User login(String username, String password) 
	{
		String x = "SELECT * FROM users WHERE username = ? AND password = ?";
		try
		{
			ConnectionGetter connect = new ConnectionGetter();
			Connection database = connect.getConnection();
			PreparedStatement query = database.prepareStatement(x);
			query.setString(1, username);
			query.setString(2, password);
			ResultSet output = query.executeQuery();
			if(output.next())
			{
				User retVal = new User(output.getInt("user_id"), output.getString("username"), output.getString("password"), output.getBoolean("isemployee"));
				return retVal;
			}
			return null;
		}
		catch(SQLException e)
		{
			System.out.println("Exception in LoginDAO");
			System.out.println(e);
		}
		return null;
	}

}
