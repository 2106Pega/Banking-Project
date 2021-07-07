package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.util.ConnectionDriver;


public class EmployeesDaoImpl implements EmployeesDao {

	@Override
	public boolean selectByUsernamePasswords(String userName, String Passwords) {

		String sql = "select * from employees where user_name = ? and passwords = ? ;";
		boolean exist= false;
		
		// try with resources syntax
		try(Connection conn = ConnectionDriver.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, Passwords);
			ResultSet rs = ps.executeQuery();
		
			if (rs.next()) return true;
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

}
