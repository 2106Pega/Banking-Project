package com.revature.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionFactory {
	//private static final String URL = "jdbc:postgres://scsdtpet:uVuRs5CEAwoSL6Kb9WhAE7c5YA1ujTUy@kashin.db.elephantsql.com/scsdtpet";
	//private static final String USERNAME = "scsdtpet";
	//private static final String PASSWORD = "uVuRs5CEAwoSL6Kb9WhAE7c5YA1ujTUy";
	private static final String URL = System.getenv("PROJECT_TRAINING_URL");
	private static final String USERNAME = System.getenv("PROJECT_TRAINING_USERNAME");
	private static final String PASSWORD = System.getenv("PROJECT_TRAINING_PASSWORD");
	
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