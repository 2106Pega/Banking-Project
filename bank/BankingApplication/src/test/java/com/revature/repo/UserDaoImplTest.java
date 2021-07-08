package com.revature.repo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.revature.exceptions.PasswordMismatchException;
import com.revature.exceptions.UserNotFoundExecption;

public class UserDaoImplTest {
	
	UserDAO uDao = new UserDaoImpl();
	
	@Test
	public void testSelectUserByUsername() throws UserNotFoundExecption {
		
		assertEquals(null, uDao.selectUserByUsername("adam1234"));
		assertEquals(1, uDao.selectUserByUsername("adam123").getId());
		assertEquals("Brooks", uDao.selectUserByUsername("miles_b").getLastName());
	}
	
	@Test
	public void testSelectUserByPassword() throws PasswordMismatchException {
		
		assertEquals("Adam", uDao.selectUserByPassword("adam123", "password").getFirstName());
		assertEquals(2, uDao.selectUserByPassword("miles_b", "employee").getId());
		assertEquals(null, uDao.selectUserByPassword("miles_b", "Employee"));
	}
}
