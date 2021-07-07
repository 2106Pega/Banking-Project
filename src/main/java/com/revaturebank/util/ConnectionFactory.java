package com.revaturebank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static final String URL =  System.getenv("project_db_url");
	private static final String USERNAME = System.getenv("project_db_username");
	private static final String PASSWORD = System.getenv("project_db_password");
	
	
	private static Connection conn;
	
	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
		return conn;
	}
}
