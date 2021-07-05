package com.revature.pzero.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystem;
import com.revature.pzero.service.BankSystemImpl;

public class BankSystemTests {
	
	
	@Mock
	Bank mockBank;
	
	BankSystem testBankSystem;

	@BeforeClass
	private void beforeSetup() {		
		mockBank = mock(BankImpl.class);
		testBankSystem = new BankSystemImpl(mockBank);
	}
	
	@Before
	private void beforeTests() {}
	
	@After
	private void afterTests() {}
	
	@Test
	public void loginTest() {
		String validUsername = "username";
		String validPassword = "password";
		String invalidUsername = "badUsername";
		String invalidPassword = "badPassword";
	
		User u = new User(0, "Tom", "Nook", "Customer", validUsername, validPassword);
		
		when(mockBank.login(null, null)).thenReturn(null);
		when(mockBank.login(null, validPassword)).thenReturn(null);
		when(mockBank.login(validUsername, null)).thenReturn(null);
		when(mockBank.login(invalidUsername, invalidPassword)).thenReturn(null);
		when(mockBank.login(validUsername, invalidPassword)).thenReturn(null);
		when(mockBank.login(invalidUsername, validPassword)).thenReturn(null);
		when(mockBank.login(validUsername, validPassword)).thenReturn(u);
		
//		assertEquals(double expected, double actual, double delta)
		assertEquals(null, testBankSystem.login(null, null));
		assertEquals(null, testBankSystem.login(null, validPassword));
		assertEquals(null, testBankSystem.login(validUsername, null));
		assertEquals(null, testBankSystem.login(invalidUsername, invalidPassword));
		assertEquals(null, testBankSystem.login(validUsername, invalidPassword));
		assertEquals(null, testBankSystem.login(invalidUsername, validPassword));
		assertEquals(u, testBankSystem.login(validUsername, validPassword));
	}
	
	@Test
	public void registerTest() {
		String username = "username";
		String password = "password";
		String userType = "Customer";
		
		assertFalse(testBankSystem.register(null));
		assertFalse(testBankSystem.register(new User(0, null, null, userType, username, password)));
		assertFalse(testBankSystem.register(new User(0, null, "lastName", userType, username, password)));
		assertFalse(testBankSystem.register(new User(0, "firstName", null, userType, username, password)));
		assertFalse(testBankSystem.register(new User(0, "", "lastName", userType, username, password)));
		assertFalse(testBankSystem.register(new User(0, "firstName", "", userType, username, password)));
		assertFalse(testBankSystem.register(new User(0, "firstName", "lastName",  null, username, password)));
		assertFalse(testBankSystem.register(new User(0, "firstName", "lastName", userType, null, password)));
		assertFalse(testBankSystem.register(new User(0, "firstName", "lastName", userType, username, null)));
		assertTrue(testBankSystem.register(new User(0, "firstName", "lastName", userType, username, password)));
	}
	
//	@Test
//	public void authenticateTest() {
//		testBankSystem.authenticate();
//		
////		assertFalse(boolean condition)
////		assertFalse();
////		assertTrue(boolean condition)
////		assertTrue();
////		assertEquals(double expected, double actual, double delta)
//	}
	
	@Test
	public void authenticateUsernameTest() {
		String username = "username";
		testBankSystem.authenticateLoginUsername(null);
		
		assertFalse(testBankSystem.authenticateLoginUsername(null));
		assertFalse(testBankSystem.authenticateLoginUsername(""));
		assertTrue(testBankSystem.authenticateLoginUsername("username"));
	}
	
	@Test
	public void authenticatePasswordTest() {
		testBankSystem.authenticateLoginPassword(null);
		
		assertFalse(testBankSystem.authenticateLoginPassword(null));
		assertFalse(testBankSystem.authenticateLoginPassword(""));
		assertTrue(testBankSystem.authenticateLoginPassword("password"));
	}
	
	@Test
	public void withdrawTest() {		
		Account a = new Account(0, 120.30, "Nickname", true);
		Account badA = new Account(0, 0.0, "", true); //balance is 0. Can't withdraw anything.
		Account badATwo = new Account(0, 100.0, "", false); //not approved, no operations can be done
		
		when(mockBank.withdraw(badA, 5.0)).thenReturn(false);
		when(mockBank.withdraw(badA, 0.0)).thenReturn(false);
		when(mockBank.withdraw(badATwo, 10.0)).thenReturn(false); 
		when(mockBank.withdraw(badATwo, 0.0)).thenReturn((false));
		when(mockBank.withdraw(a, -5.0)).thenReturn(false); //can't withdraw negative values
		when(mockBank.withdraw(a,  200.10)).thenReturn(false); // can't be more than the balance
		when(mockBank.withdraw(a, 120.30)).thenReturn(true);		
		when(mockBank.withdraw(a, 20.30)).thenReturn(true);
		
		assertFalse(testBankSystem.withdraw(badA, 5.0));
		assertFalse(testBankSystem.withdraw(badA, 0.0));
		assertFalse(testBankSystem.withdraw(badATwo, 10.0));
		assertFalse(testBankSystem.withdraw(badATwo, 0.0));
		assertFalse(testBankSystem.withdraw(a, -5.0));
		assertFalse(testBankSystem.withdraw(a,  200.10));
		assertTrue(testBankSystem.withdraw(a, 120.30));
		assertTrue(testBankSystem.withdraw(a, 20.30));
		
	}
	
	@Test
	public void depositTest() {
		Account a = new Account(0, 120.30, "Nickname", true);
		Account badA = new Account(0, Double.MAX_VALUE, "", true); //balance is 0. Can't withdraw anything.
		Account badATwo = new Account(0, 100.0, "", false); //not approved, no operations can be done
		
		when(mockBank.deposit(a, -5.0)).thenReturn(false);
		when(mockBank.deposit(a, Double.MAX_VALUE+1.0)).thenReturn(false); //can't deposit values more than double max
		when(mockBank.deposit(a, 0.001)).thenReturn(false); //can't deposit smaller than a penny
		when(mockBank.deposit(badATwo, 10.0)).thenReturn(false); //not approved account
		when(mockBank.deposit(badA, 0.01)).thenReturn(false);
		when(mockBank.deposit(a, 10.0)).thenReturn(true);
		double x = Double.MAX_VALUE - a.getBalance() + 1;
		when(mockBank.deposit(a, x)).thenReturn(false);		
		
		assertFalse(testBankSystem.deposit(a, -5.0));
		assertFalse(testBankSystem.deposit(a, Double.MAX_VALUE+1.0));
		assertFalse(testBankSystem.deposit(a, 0.001));
		assertFalse(testBankSystem.deposit(badATwo, 10.0));
		assertFalse(testBankSystem.deposit(badA, 0.01));
		assertTrue(testBankSystem.deposit(a, 10.0));
		assertFalse(testBankSystem.deposit(a, x));
	}
	
	@Test
	public void transferTest() {
		Account okayAccount = new Account(0, 100.0, "", true);
		Account badAccount = new Account(0, 10.0, "", false);
		Account lowAccount = new Account(0, 10.0, "", true);
		
		when(mockBank.transfer(null, null, 100.0)).thenReturn(false);
		when(mockBank.transfer(null, okayAccount, 50.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, null, 10.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, badAccount, 10.0)).thenReturn(false);
		when(mockBank.transfer(badAccount, okayAccount, 10.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, lowAccount, 10.0)).thenReturn(true);
		when(mockBank.transfer(lowAccount, okayAccount, 20.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, lowAccount, -10.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, lowAccount, Double.MAX_VALUE-okayAccount.getBalance()+1.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, new Account(), 10.0)).thenReturn(false);
		when(mockBank.transfer(okayAccount, lowAccount, 55.09)).thenReturn(true);
		
		assertFalse(testBankSystem.transfer(null, null, 100.0));
		assertFalse(testBankSystem.transfer(null, okayAccount, 50.0));
		assertFalse(testBankSystem.transfer(okayAccount, null, 10.0));
		assertFalse(testBankSystem.transfer(okayAccount, badAccount, 10.0));
		assertFalse(testBankSystem.transfer(badAccount, okayAccount, 10.0));
		assertTrue(testBankSystem.transfer(okayAccount, lowAccount, 10.0));
		assertFalse(testBankSystem.transfer(lowAccount, okayAccount, 20.0));
		assertFalse(testBankSystem.transfer(okayAccount, lowAccount, -10.0));
		assertFalse(testBankSystem.transfer(okayAccount, lowAccount, Double.MAX_VALUE-okayAccount.getBalance()+1.0));
		assertFalse(testBankSystem.transfer(okayAccount, new Account(), 10.0));
		assertTrue(testBankSystem.transfer(okayAccount, lowAccount, 55.09));
		
	}
	
}
