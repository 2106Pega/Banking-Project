package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String URL = "jdbc:postgresql://localhost:5432/RevatureBank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";
	
	private static Connection conn;
	
	public static Connection getConnection() {
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}

}
