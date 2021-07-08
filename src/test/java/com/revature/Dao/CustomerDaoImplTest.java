package com.revature.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Account;

public class CustomerDaoImplTest {

	CustomerDao c = new CustomerDaoImpl();

	@Test
	public void applyTest()
	{
		Account t1 = new Account("james", "account1", false, 132456.6);
		Account t2 = new Account("bob", "account1", false, 789.5);
		assertEquals(c.apply_account(t1), false);
		assertEquals(c.apply_account(t2), true);
	}
	
	@Test
	public void viewTest() {
		Account t1 = new Account("james", "account1", false, 456.38);
		c.view_balance(t1);
		assertEquals(t1.getBalance(), 565.38, 0.0001);
		
	}

	@Test
	public void depositTest()
	{
		Account t1 = new Account("james", "account1", false, 132456.6);
		Account t2 = new Account("bob", "account1", false, 789.5);
		c.deposite(t1, 100);
		c.deposite(t2, 100);
		assertEquals(t1.getBalance(), 665.38, 0.0001);
		assertNotEquals(t2.getBalance(), 889.5, 0.0001);
	}
	
	@Test
	public void withdrawTest()
	{
		Account t1 = new Account("james", "account1", false, 132456.6);
		Account t2 = new Account("bob", "account1", false, 789.5);
		c.withdraw(t1, 100);
		c.withdraw(t2, 100);
		assertEquals(t1.getBalance(), 465.38, 0.0001);
		assertNotEquals(t2.getBalance(), 689.5, 0.0001);
	}

	@Test
	public void postTest() {
		Account t1 = new Account("james", "account1", false, 132456.6);
		Account t2 = new Account("bob", "account1", false, 789.5);
		Account t3 = new Account("david", "account1", false, 123.456);
		
		c.post(t1, t3, 100);
		assertEquals(t1.getBalance(), 565.38, 0.0001);
		assertEquals(t3.getBalance(), 465.38, 0.0001);
	}
	
	@Test
	public void acceptTest()
	{
		Account t1 = new Account("james", "account1", false, 132456.6);
		Account t2 = new Account("bob", "account1", false, 789.5);
		Account t3 = new Account("david", "account1", false, 123.456);

		c.accept(t1, t3, 100);
		assertEquals(t1.getBalance(), 565.38, 0.0001);
		assertEquals(t3.getBalance(), 365.38, 0.0001);
	}

	@After
	public void reset()
	{
		Account a = new Account("bob", "account1", false, 789.5);
		String sql = "DELETE FROM account WHERE username = ? AND account_name = ?;";
		try(Connection conn = ConnectionFactory.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getUsername());
			ps.setString(2, a.getAccount_name());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
