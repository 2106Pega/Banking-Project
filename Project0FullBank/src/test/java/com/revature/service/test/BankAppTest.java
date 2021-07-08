package com.revature.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.repository.BankDao;
import com.revature.service.BankApp;
import com.revature.service.BankAppImpl;

public class BankAppTest {
	
	BankApp app;
	
	@Mock
	BankDao fakeDao;
	
	@Mock
	CustomerAccount customer;
	
	@Mock
	EmployeeAccount employee;
	
	@Mock
	BankAccount bank1;
	
	@Mock
	BankAccount bank2;
	
	@Mock
	BankAccount bank3;
	
	@Before
	public void setUp() {
		
		fakeDao = mock(BankDao.class);
		customer = mock(CustomerAccount.class);
		employee = mock(EmployeeAccount.class);
		bank1 = mock(BankAccount.class);
		bank2 = mock(BankAccount.class);
		bank3 = mock(BankAccount.class);
		
		when(fakeDao.selectEmployeeAccountByUsername("imanemployee")).thenReturn(employee);
		when(employee.getPassword()).thenReturn("iworkhard247");
		
		when(fakeDao.selectCustomerAccountByUsername("johnsmith")).thenReturn(customer);
		when(customer.getPassword()).thenReturn("imgeneric7");
		when(customer.getId()).thenReturn(1);
		when(fakeDao.selectBankAccountsByCustomer(1)).thenReturn(new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		
		when(fakeDao.selectBankAccountById(1)).thenReturn(bank1);
		when(fakeDao.selectBankAccountById(2)).thenReturn(bank2);
		when(fakeDao.selectBankAccountById(3)).thenReturn(bank3);
		when(fakeDao.selectAllBankAccounts()).thenReturn(new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		when(bank1.getId()).thenReturn(1);
		when(bank2.getId()).thenReturn(2);
		when(bank3.getId()).thenReturn(3);
		when(bank1.getBalance()).thenReturn(50.0);
		when(bank2.getBalance()).thenReturn(30.0);
		when(bank3.getBalance()).thenReturn(0.0);
		when(bank1.isApproved()).thenReturn(true);
		when(bank2.isApproved()).thenReturn(true);
		when(bank3.isApproved()).thenReturn(false);
		
		app = new BankAppImpl(fakeDao);
		
	}
	
	@Test
	public void testValidateCustomerLogin() throws NoSuchAccountException {
		
		assertTrue(app.validateCustomerLogin("johnsmith", "imgeneric7"));
		assertFalse(app.validateCustomerLogin("johnsmith", "imgeneric"));
		assertThrows(NoSuchAccountException.class, () -> app.validateCustomerLogin("jonsmith", "imgeneric7"));
		assertThrows(NoSuchAccountException.class, () -> app.validateCustomerLogin("will", "bigbadbi11"));
		
	}
	
	@Test
	public void testValidateEmployeeLogin() throws NoSuchAccountException {
		
		assertTrue(app.validateEmployeeLogin("imanemployee", "iworkhard247"));
		assertFalse(app.validateEmployeeLogin("imanemployee", "imgeneric7"));
		assertThrows(NoSuchAccountException.class, () -> app.validateEmployeeLogin("employee", "iworkhard247"));
		assertThrows(NoSuchAccountException.class, () -> app.validateEmployeeLogin("imanelephant", "1h4v3n01d34!"));
		
	}
	
	@Test
	public void testCreateNewCustomerAccount() throws UsernameAlreadyExistsException {
		
		assertThrows(UsernameAlreadyExistsException.class, () -> app.createNewCustomerAccount("johnsmith", "differen7"));
		assertThrows(UsernameAlreadyExistsException.class, () -> app.createNewCustomerAccount("johnsmith", "imgeneric7"));
	
	}
	
	@Test
	public void testCreateNewEmployeeAccount() {
		
		
		assertThrows(UsernameAlreadyExistsException.class, () -> app.createNewEmployeeAccount("imanemployee", "differen7"));
		assertThrows(UsernameAlreadyExistsException.class, () -> app.createNewEmployeeAccount("imanemployee", "iworkhard247"));
		
	}
	
	@Test
	public void testGetCustomerIdFromUsername() throws NoSuchAccountException {
		
		assertEquals(app.getCustomerIdFromUsername("johnsmith"), 1);
		assertThrows(NoSuchAccountException.class, () -> app.getCustomerIdFromUsername("idontexist"));
		assertThrows(NoSuchAccountException.class, () -> app.getCustomerIdFromUsername(""));
		assertThrows(NoSuchAccountException.class, () -> app.getCustomerIdFromUsername("?????"));
		
	}
	
	@Test
	public void testGetBankAccountIds() throws NoSuchAccountException {
		
		assertEquals(app.getBankAccountIds(), new ArrayList<Integer>(Arrays.asList(1, 2)));
		assertEquals(app.getBankAccountIds("johnsmith"), new ArrayList<Integer>(Arrays.asList(1, 2)));
		assertThrows(NoSuchAccountException.class, () -> app.getBankAccountIds("whoami?61"));
		assertThrows(NoSuchAccountException.class, () -> app.getBankAccountIds("imanemployee"));
		
	}
	
	@Test
	public void testGetUnvalidatedAccounts() {
		
		assertEquals(app.getUnvalidatedAccountIds(), new ArrayList<Integer>(Arrays.asList(3)));
		
	}
	
	@Test
	public void testGetBalance() throws NoSuchAccountException {
		
		assertEquals(app.getBalance(1), 50.0, 0.001);
		assertEquals(app.getBalance(2), 30.0, 0.001);
		assertThrows(NoSuchAccountException.class, () -> app.getBalance(4));
		assertThrows(NoSuchAccountException.class, () -> app.getBalance(0));
		assertThrows(NoSuchAccountException.class, () -> app.getBalance(-10));
		
	}
	
	@Test
	public void testMakeDeposit() throws BadInputException, NoSuchAccountException {
		
		app.makeDeposit(1, 50);
		verify(fakeDao, times(1)).updateBankAccount(bank1);
		assertThrows(BadInputException.class, () -> app.makeDeposit(1, -100));
		assertThrows(BadInputException.class, () -> app.makeDeposit(1, -100));
		assertThrows(NoSuchAccountException.class, () -> app.makeDeposit(-2, 20));
		assertThrows(NoSuchAccountException.class, () -> app.makeDeposit(5, 20));
		
	}
	
	@Test
	public void testMakeWithdrawal() throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		app.makeWithdrawal(2, 5);
		verify(fakeDao, times(1)).updateBankAccount(bank2);
		assertThrows(BadInputException.class, () -> app.makeWithdrawal(1, -50));
		assertThrows(BadInputException.class, () -> app.makeWithdrawal(1, 0));
		assertThrows(NoSuchAccountException.class, () -> app.makeWithdrawal(-2, 20));
		assertThrows(NoSuchAccountException.class, () -> app.makeWithdrawal(5, 20));
		assertThrows(OverdrawnException.class, () -> app.makeWithdrawal(1, 80));
		
	}
	
	@Test
	public void testMakeTransfer() throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		app.makeTransfer(1, 2, 15);
		verify(fakeDao, times(1)).transfer(bank1, bank2, 15);
		assertThrows(BadInputException.class, () -> app.makeTransfer(1, 2, -90));
		assertThrows(BadInputException.class, () -> app.makeTransfer(2, 1, 0));
		assertThrows(NoSuchAccountException.class, () -> app.makeTransfer(-1, 2, 15));
		assertThrows(NoSuchAccountException.class, () -> app.makeTransfer(1, 6, 10));
		assertThrows(OverdrawnException.class, () -> app.makeTransfer(1, 2, 70));
		
	}
	
	@Test
	public void testApproveAccount() throws NoSuchAccountException {
		
		app.approveAccount(3);
		verify(fakeDao, times(1)).updateBankAccount(bank3);
		assertThrows(NoSuchAccountException.class, () -> app.approveAccount(7));
		assertThrows(NoSuchAccountException.class, () -> app.approveAccount(-9));
		
	}
	
	@Test
	public void testRejectAccount() throws NoSuchAccountException {
		
		app.rejectAccount(3);
		verify(fakeDao, times(1)).deleteBankAccount(bank3);
		assertThrows(NoSuchAccountException.class, () -> app.rejectAccount(10));
		assertThrows(NoSuchAccountException.class, () -> app.rejectAccount(-8));
		
	}
	

}
