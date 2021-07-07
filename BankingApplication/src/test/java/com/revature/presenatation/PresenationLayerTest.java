package com.revature.presenatation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.AdminAccount;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.models.User;
import com.revature.presentation.BankingUI;

class PresenationLayerTest {
	
	BankingUI testBankingUI;
	BankAccount testBankAccount;
	AdminAccount testAdminAccount;
	CustomerAccount testCustomerAccount;
	EmployeeAccount testEmployeeAccount;
	User testUser;

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Before
	public void before()
	{
		testUser = new User(0, "username", "password");

	}
	
	public void employeeLoginTest() {
		testCustomerAccount.loginValidation(testUser);
	}
	

}
