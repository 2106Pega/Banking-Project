package com.paul.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAOUtil {
	private static final Logger LOG = LogManager.getLogger(DAOUtil.class);
	
	private static String CONNECTION_USERNAME; 
	private static String CONNECTION_PASSWORD; 
	private static String CONNECTION_URL; 
	private static Connection connection; 
	
	static {
		try {
			FileInputStream fs = new FileInputStream("C:\\Users\\paulk\\eclipse-workspace\\bank-maven\\src\\main\\resources\\application.properties");
			Properties props = new Properties();
			props.load(fs);
			CONNECTION_URL = props.getProperty("CONNECTION_URL");
			CONNECTION_USERNAME = props.getProperty("CONNECTION_USERNAME");
			CONNECTION_PASSWORD = props.getProperty("CONNECTION_PASSWORD");
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			LOG.fatal("Could not register driver!");
			e.printStackTrace();
		}
		if (connection == null || connection.isClosed())
		 connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		return connection; 
	}

}