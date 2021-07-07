package com.revature.test.jUnitTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.Application.Customer;
import com.revature.Application.Employee;
import com.revature.DatabaseTools.UserLogin;
import com.revature.Models.User;

public class Tests 
{
	Customer testCust;
	Employee testEmp;
	UserLogin login = new UserLogin();
	
	@BeforeClass
	public static void beforeClass()
	{
		
	}
	
	@Before
	public void before()
	{
		testCust = new Customer(login.getLogin("debugCust", "password1"));
		testEmp = new Employee(login.getLogin("debugEmp", "password1"));
	}
	
	@After
	public void after()
	{
		
	}
	
	@AfterClass()
	public static void afterClass()
	{
		
	}
	
	@Test
	public void goodLogin()
	{
		testCust.login("debugCust", "password1");
	}
	
	@Test
	public void badRegister()
	{
		String existingUsername = "debugCust";
		boolean x = testCust.register(existingUsername, "password");
		assert(!x);
	}
	
	@Test
	public void badLogin()
	{
		boolean x = testCust.login("debugCust", "badPassword");
		assert(!x);
	}
	
	@Test
	public void badViewAccount()
	{
		int x = testCust.viewBalance(5);
		assert(x == -1);
	}
	
	@Test
	public void badDepositAmount()
	{
		boolean x = testCust.deposit(-500, 1);
		assert(!x);
	}
	
	@Test
	public void badDepositAccount()
	{
		boolean x = testCust.deposit(500, 5);
		assert(!x);
	}
	
	@Test
	public void badWithdrawAmount()
	{
		boolean x = testCust.withdraw(-500, 1);
		assert(!x);
	}
	
	@Test
	public void badWithdrawAccount()
	{
		boolean x = testCust.withdraw(500, 5);
		assert(!x);
	}
	
	@Test
	public void badTransferAccId()
	{
		boolean x = testCust.transfer(500, 3, 4);
		assert(!x);
	}
	
	@Test
	public void badTransferAmount()
	{
		boolean x = testCust.transfer(-500, 1, 2);
	}
}

















