package com.revature.pzero.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;

public class AccountTests {

	/*
	 * 
	 * 	private int id;
	 *	private Double balance;
	 *	private String nickName;
	 * boolean approved
	 */
	
	Account notApprovedAccount;
	Account noBalanceAccount;
	Account normalAccount;
	
	@Mock
	Bank mockBank;
	
	@BeforeClass
	public void beforeClass() {
		
	}
	
	@Before
	public void beforeTests() {
//		notApprovedAccount = new Account(1, 150.0, "accountNickname", false);
//		noBalanceAccount = new Account(2, 0.0, "accountNickname", true);
//		normalAccount = new Account(3, 234.56, "accountNickname", true);
		
		mockBank = mock(BankImpl.class);
		
//		when(mockBank.newUser(new User(), "")).thenReturn(false);
//		when(mockBank.newAccount(new Account())).thenReturn(false);
//		when(mockBank.viewAccountByAccountId(0)).thenReturn(new Account());
//		when(mockBank.viewAccountByUserID(0)).thenReturn(new ArrayList());
//		when(mockBank.viewAllAccounts()).thenReturn(new ArrayList());
//		when(mockBank.withdraw(new Account())).thenReturn(false);
//		when(mockBank.deposit(new Account())).thenReturn(false);
//		when(mockBank.transfer(new Account(),  new Account())).thenReturn(false);
//		when(mockBank.closeAccount(new Account())).thenReturn(false);
//		when(mockBank.deleteUser(new User())).thenReturn(false);
	}
	
	@Test
	public void IdTest() {}
	
	@Test
	public void balanceTest() {
		//can't be negative
		//can't be bigger/smaller than the Double value
		//can't be null
		//can't be smaller that 0.01
	}
	
	@Test
	public void nickNameTest() {}
	
	@Test
	public void approvedTest() {}
	
}
