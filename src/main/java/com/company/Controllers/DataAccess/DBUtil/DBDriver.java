package com.company.Controllers.DataAccess.DBUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public interface DBDriver {


    default Connection getConnect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties properties = new Properties();
        properties.setProperty("user","postgres");
        properties.setProperty("password","password");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/bank_project",properties);
    }

}
