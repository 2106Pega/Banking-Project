package com.revature.pzero.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPoint {

	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String URL = "";
	private static Connection conn;
	
	public static Connection getConnection() {
		
		try {
			conn = DriverManager.getConnection(URL);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
}
