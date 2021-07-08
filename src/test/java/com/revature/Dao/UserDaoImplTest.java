package com.revature.Dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.models.User;

public class UserDaoImplTest {

	UserDao u = new UserDaoImpl();
	@Test
	public void testLogin()
	{
		User t1 = new User("smith","123456");
		User t2 = new User("james","123456");
		User t3 = new User("allen","123456");
		User t4 = new User("smith","456789");
		assertEquals(u.login(t1), true);
		assertEquals(u.login(t2), true);
		assertEquals(u.login(t3), false);
		assertEquals(u.login(t4), false);
	}
	
	@Test
	public void testApply()
	{
		User t1 = new User("smith","123456");
		User t2 = new User("james","123456");
		User t3 = new User("allen","123456");
		assertEquals(u.apply(t1), false);
		assertEquals(u.apply(t2), false);
		assertEquals(u.apply(t3), true);
		
	}
	
	@After
	public void reset()
	{
		User u = new User("allen","123456");
		String sql = "DELETE FROM users WHERE username = ? AND passcode = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPasscode());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
