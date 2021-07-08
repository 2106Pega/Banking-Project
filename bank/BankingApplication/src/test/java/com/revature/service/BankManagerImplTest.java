package com.revature.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.revature.exceptions.UserNotFoundExecption;
import com.revature.repo.AccountDAO;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDaoImpl;

public class BankManagerImplTest {
	AccountDAO aDao;
	UserDAO uDao = new UserDaoImpl();
	BankManager bM = new BankManagerImpl(aDao, uDao);


	@Test
	public void testAuthenticate() throws UserNotFoundExecption{
		
		assertEquals(true, bM.authenticate("adam123"));
		assertEquals(false, bM.authenticate("adam1234"));
		assertEquals(true, bM.authenticate("raymond"));
		assertEquals(false, bM.authenticate("Raymond"));
	}
	
	@Test
	public void testCheckPassword() {
		assertEquals(true, bM.checkPassword("adam123", "password"));
		assertEquals(false, bM.checkPassword("adam123", "PASSWORD"));
	}
	
	@Test
	public void testValidPassword() {
		assertEquals(true, bM.validPassword("password"));
		assertEquals(false, bM.validPassword("pass"));
		assertEquals(true, bM.validPassword("Good paSSworD"));
		assertEquals(false, bM.validPassword("Pdfae"));
	}
	
	@Test
	public void testCheckAccountType() {
		assertEquals(1, bM.checkAccountType("adam123"));
		assertEquals(2, bM.checkAccountType("miles_b"));
	}
	
	@Test
	public void testCheckStartingBalance() {
		assertEquals("Please input a positive number.", bM.checkStartingBalance("-20"));
		assertEquals("Please input a valid number.", bM.checkStartingBalance("apple"));
	}
}
