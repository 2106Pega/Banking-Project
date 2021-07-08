package com.revature.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Test;

import com.revature.models.Account;
import com.revature.models.Employee;

public class EmployeeDaoImplTest {
	EmployeeDao employee = new EmployeeDaoImpl();
	
	@Test
	public void loginTest()
	{
		Employee t1 = new Employee("james", "123456");
		Employee t2 = new Employee("james", "123789");
		Employee t3 = new Employee("allen", "123465");
		assertEquals(employee.login(t1), true);
		assertEquals(employee.login(t2), false);
		assertEquals(employee.login(t3), false);
	}
	
	@Test
	public void approveTest()
	{
		Account t1 = new Account("james", "account1", false, 0);
		Account t2 = new Account("smith", "account1", false, 0);
		employee.approve(t1);
		employee.approve(t2);
		assertEquals(t1.isApproval(), true);
		assertNotEquals(t2.isApproval(), false);
	}
	
	@Test
	public void rejectTest()
	{
		Account t1 = new Account("james", "account1", false, 0);
		Account t2 = new Account("smith", "account1", false, 0);
		employee.reject(t1);
		employee.reject(t2);
		assertEquals(t1.isApproval(), false);
		assertNotEquals(t2.isApproval(), true);
	}
	
	
	@After
	public void reset_r()
	{
		Account t1 = new Account("james", "account1", false, 0);
		employee.approve(t1);
		Account t2 = new Account("smith", "account1", false, 0);
		employee.reject(t2);
	}
}
