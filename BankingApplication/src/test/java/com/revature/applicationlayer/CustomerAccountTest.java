package com.revature.applicationlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.User;

public class CustomerAccountTest {

	@Mock
	BankAccount mockBankAccount;
	CustomerAccount testCustomerAccount;
	
	@Before
	public void setupBank() {
		 
		mockBankAccount = mock(BankAccount.class);
		testCustomerAccount = new CustomerAccount();
		
	}
	
	@Test
	public void testLoginValidation() {
		int userId = 0;
		String correctUsername = "afd233@F";
		String correctPassword = "afd233@F";
		//BankAccount testBank = null;
		User user = new User(userId, correctUsername, correctPassword);
		
		when(testCustomerAccount.loginValidation(user)).thenReturn(mockBankAccount);
		when(testCustomerAccount.loginValidation(null)).thenReturn(mockBankAccount);
		
		
		
		assertNull(testCustomerAccount.loginValidation(user));
	}
	
//	@Test
//	public void testLoadCsutomerInfoByID() {
//		BankAccount testBank = null;
//		when(testCustomerAccount.loadCustomerInfoByID(-1)).thenReturn(null);
//		when(testCustomerAccount.loadCustomerInfoByID(0)).thenReturn(testBank);
//		when(testCustomerAccount.loadCustomerInfoByID(-1)).thenReturn(testBank);
//		
//	
//
//	}
//	
//	@Test
//	public void testWithdrawMoney()
//	{
//		BankAccount testAccount1 = new BankAccount(0, null, null, null, -1, -100, true);
//		when(testCustomerAccount.withdrawMoney(testAccount1,-1)).thenReturn(null);
//		when(testCustomerAccount.withdrawMoney(testAccount1,-1)).thenReturn(testAccount1);
//	}

}
