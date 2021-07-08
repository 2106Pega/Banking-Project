package com.revature.utl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.BankDAOImpl;

public class ConnectionFactory{
	final static Logger loggy = Logger.getLogger(ConnectionFactory.class);
	private static final String URL = "jdbc:postgresql://localhost/postgres";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "javaPassword";
	
	private static Connection conn;
	public static Connection getConnection(){
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			loggy.warn(e);
		}

		return conn;
	}
}

