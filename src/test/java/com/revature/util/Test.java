package com.revature.util; 
import org.junit.*;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.presentation.PresentationService;
import com.revature.repo.AccountDAO;
import com.revature.repo.AccountDAOImpl;
import com.revature.repo.CustomerDAO;
import com.revature.repo.CustomerDAOImpl;
import com.revature.repo.UserDAO;
import com.revature.repo.UserDAOImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


public class Test {
	PresentationService ps = new PresentationService();
	AccountDAO a = new AccountDAOImpl();
	UserDAO u = new UserDAOImpl();
	CustomerDAO c = new CustomerDAOImpl();
	
	public void main(String[] args) {
		this.TestEmail();
	}
	
	
	@org.junit.Test
	public void TestEmail() {
		assertTrue(ps.VerifyEmail("testh@d.ds"));
		assertTrue(ps.VerifyEmail("test@h.ds"));
		assertFalse(ps.VerifyEmail("test@h.s"));
		assertFalse(ps.VerifyEmail("testh.ss"));
		assertFalse(ps.VerifyEmail("@h.ss"));
	}
	
	@org.junit.Test
	public void TestPhoneNumber() {
		assertTrue(ps.VerifyPhoneNumber("1111111111"));
		assertTrue(ps.VerifyPhoneNumber("1234567890"));
		assertFalse(ps.VerifyPhoneNumber("111"));
		assertFalse(ps.VerifyPhoneNumber("@@@@@@@@@@"));
		assertFalse(ps.VerifyPhoneNumber("%11"));
	}
	
	@org.junit.Test
	public void TestUsername() {
		assertTrue(ps.VerifyUsername("12345@78"));
		assertTrue(ps.VerifyUsername("12345abc"));
		assertFalse(ps.VerifyUsername(""));
		assertFalse(ps.VerifyUsername("1234"));
		assertFalse(ps.VerifyUsername("123ab"));
	}
	
	@org.junit.Test
	public void TestPassword() {
		assertTrue(ps.VerifyPassword("123456@8"));
		assertTrue(ps.VerifyPassword("12345abc"));
		assertFalse(ps.VerifyPassword(""));
		assertFalse(ps.VerifyPassword("1234"));
		assertFalse(ps.VerifyPassword("123ab"));
	}
	
	@org.junit.Test
	public void TestInsertAccount() {
		Account account1 = new Account(1, 8, 100, false);
		Account account2 = new Account(1, 8, 100, false);
		Account account3 = new Account(1, 8, 100, false);
		Account account4 = new Account(1, 8, 100, false);
		assertTrue(a.insertAccount(account1));
		assertTrue(a.insertAccount(account2));
		assertTrue(a.insertAccount(account3));
		assertTrue(a.insertAccount(account4));
	}
	
	@org.junit.Test
	public void TestAccountWithdrawal() {
		assertTrue(a.WithdrawFromAccount(20, 1));
		assertTrue(a.WithdrawFromAccount(21, 1));
	}
	
	@org.junit.Test
	public void TestAccountDeposit() {
		assertTrue(a.DepositIntoAccount(134, 1));
		assertTrue(a.DepositIntoAccount(142, 1));
	}
	
	@org.junit.Test
	public void TestGetAccountByID() {
		Account account1 = new Account(20, 8, 100, true);
		Account account2 = new Account(21, 8, 100, true);
		assertEquals(account1.getAccount_id(), a.GetAccountByAccountID(20).getAccount_id());
		assertEquals(account2.getAccount_id(), a.GetAccountByAccountID(21).getAccount_id());
	}
	
	@org.junit.Test
	public void TestInsertUser() {
		User user1 = new User("username", "password");
		User user2 = new User("username", "password");
		User user3 = new User("username1", "password");
		assertFalse(u.insertUser(user1));
		assertFalse(u.insertUser(user2));
		assertTrue(u.insertUser(user3));
		//assertTrue(u.insertUser(null)));
	}

	
	@org.junit.Test
	public void TestApproveAccount() {
		assertTrue(ps.ApproveAccount("25"));
		assertTrue(ps.ApproveAccount("26"));
	}
	
	@org.junit.Test
	public void TestDenyAccount() {
		assertTrue(ps.ApproveAccount("8"));
		assertTrue(ps.ApproveAccount("9"));
	}


}
