package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtil {
	private static final String CONNECTION_USERNAME = "postgres"; 
	private static final String CONNECTION_PASSWORD = "b1gb055"; 
	private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/bank" ; 
	private static Connection connection; 
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			System.out.println("Could not register driver!");
			e.printStackTrace();
		}
		if (connection == null || connection.isClosed())
		 connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		return connection; 
	}

}