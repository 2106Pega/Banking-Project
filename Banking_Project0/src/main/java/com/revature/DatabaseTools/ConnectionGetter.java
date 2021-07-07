/*
 * Helper class when working with SQL statements
 */
package com.revature.DatabaseTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionGetter
{
	private final String USERNAME = "postgres";
	private final String PASSWORD = "password1";
	private final String URL = "jdbc:postgresql://localhost:5432/template1";
	
	public Connection getConnection()
	{
		Connection x = null;
		try
		{
			x = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch(SQLException e)
		{
			System.out.println("Connection not found");
			System.out.println(e);
			System.exit(0);
		}
		return x;
	}
}
