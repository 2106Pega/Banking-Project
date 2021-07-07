package com.revature.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Accounts;
import com.revature.models.Users;
import com.revature.service.LogProcess;

public class JunitTest {
	
	
	@Test
	public void testCreateUserDuplicateUserName() {
		
		Users newUser = new Users(1,"polloty", "Ben", "Hu");
		UsersDao userDao = new UsersDaoImpl();
			
		//userDao.createUser(newUser);  // create new customer in DB
		
		
		//LogProcess.userRegistration();

		assertEquals(newUser.getUser_name(), 
				(userDao.selectUserByUsername("polloty")).getUser_name());
		assertEquals(newUser.getFirstName(), 
				(userDao.selectUserByUsername("polloty")).getFirstName());
		assertEquals(newUser.getLastName(), 
				(userDao.selectUserByUsername("polloty")).getLastName());
		
		
	}
	
	
	/*
	 * @Test public void testCreateUser2() {
	 * 
	 * 
	 * Users newUser = new Users(1,"polloty", "Benjamin", "Hu"); Users newUser2 =
	 * new Users(1,"polloty110", "Benjamin", "Hun"); UsersDao userDao = new
	 * UsersDaoImpl();
	 * 
	 * userDao.createUser(newUser); //LogProcess.userRegistration();
	 * 
	 * assertEquals(newUser.getFirstName(),
	 * (userDao.selectUserByUsername("polloty110")).getFirstName());
	 * 
	 * 
	 * 
	 * assertEquals(newUser2.getUser_name(),
	 * (userDao.selectUserByUsername("polloty110")).getUser_name());
	 * assertEquals(newUser2.getFirstName(),
	 * (userDao.selectUserByUsername("polloty110")).getFirstName());
	 * assertEquals(newUser2.getLastName(),
	 * (userDao.selectUserByUsername("polloty110")).getLastName());
	 * 
	 * }
	 */
	
	
	
	
	
	
	
	
	
	
	

}
