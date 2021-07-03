package com.revature.pzero.tests;

import org.junit.Before;
import org.junit.Test;

import com.revature.pzero.models.Account;

public class AccountTests {

	/*
	 * 
	 * 	private int id;
	 *	private Double balance;
	 *	private String nickName;
	 * 
	 */
	
	@Before
	public void beforeTests() {
		Account a = new Account(1, 1.0, "accountNickname");
	}
	
	@Test
	public void balanceTest() {
		//can't be negative
		//can't be bigger/smaller than the Double value
		//can't be null
		//can't be smaller that 0.01
	}
	
}
