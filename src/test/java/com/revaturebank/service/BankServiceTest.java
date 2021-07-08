package com.revaturebank.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import com.revaturebank.dao.CustomerDao;
import com.revaturebank.dao.CustomerDaoImpl;
import com.revaturebank.dao.UserDao;
import com.revaturebank.dao.UserDaoImpl;
import com.revaturebank.model.Customer;
import com.revaturebank.model.User;

public class BankServiceTest {
    
	@Mock
    UserDao userDao;
    
    @Mock
    CustomerDao customerDao;
    
    BankService bs;
    
    @Before
    public void prepareBankService() {
    	userDao = Mockito.mock(UserDaoImpl.class);
    	
    	customerDao = Mockito.mock(CustomerDaoImpl.class);
    	
    	bs = new BankService(userDao, customerDao);


    }


	@Test
	public void test_customerLogin_success() throws Exception {
		//Given	
		
		User mockedUser = new User();
		mockedUser.setPassword("123");
		mockedUser.setUserType("customer");
		Mockito.when(userDao.getUser(Mockito.anyString())).thenReturn(mockedUser);
		
		
		//When
		User user = bs.customerLogIn("test", "123");
	
		//Then
	    Assert.assertEquals(user.getPassword(), "123");
		Assert.assertEquals(user.getUserType(),"customer");
	}
	
	
	@Test
	public void test_customerLogin_failure() throws Exception {
		//Given	
		User mockedUser = new User();
		mockedUser.setPassword("123");
		mockedUser.setUserType("customer");
		Mockito.when(userDao.getUser(Mockito.anyString())).thenReturn(mockedUser);
		
		
		//When
		User user = bs.customerLogIn("123", "employee");
	
		//Then
		Assert.assertEquals(user, null);
	}
	
	@Test
	public void test_employeeLogin_success() throws Exception {
		//Given	
		User mockedUser = new User();
		mockedUser.setPassword("123");
		mockedUser.setUserType("employee");
		Mockito.when(userDao.getUser(Mockito.anyString())).thenReturn(mockedUser);
		
		//When
		User user = bs.employeeLogIn("test", "123");
	
		//Then
		Assert.assertEquals(user.getPassword(), "123");
		Assert.assertEquals(user.getUserType(),"employee");
	}
	
	
	@Test
	public void test_employeeLogin_failure() throws Exception {
		//Given	
		User mockedUser = new User();
		mockedUser.setPassword("123");
		mockedUser.setUserType("employee");
		Mockito.when(userDao.getUser(Mockito.anyString())).thenReturn(mockedUser);
		
		//When
		User user = bs.employeeLogIn("234", "customer");
	
		//Then
	Assert.assertEquals(user, null);

	}
		
	@Test
	public void test_getCustomers_success() {
		//Given	
		List<Customer> mockedCustomerList = new ArrayList<>();
		Customer mockedCustomer = new Customer();
		mockedCustomer.setAccountName("test");
		mockedCustomer.setAccountNumber(12321);
		mockedCustomer.setAccountType("checking");
		mockedCustomerList.add(mockedCustomer);
		Mockito.when(customerDao.getCustomersFromCustomerId(Mockito.anyInt())).thenReturn(mockedCustomerList);
		
		//When
		List<Customer> customer = bs.getCustomers(12);
	
		//Then
		Assert.assertNotNull(customer);
		Assert.assertEquals(customer.size(), 1);
	}
	
	@Test
	public void test_deposit_success() {
		bs.deposit(1234, 80.34, 100.00);
		
		Mockito.verify(customerDao).updateCustomerBalance(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void test_withdraw_success() {
		bs.withdraw(1234, 80.24, 100.00);
		
		Mockito.verify(customerDao).updateCustomerBalance(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void test_transfer_success() {
		
		Customer mockedPayToCustomer = new Customer();
		mockedPayToCustomer.setBalance(90.23);
		Mockito.when(customerDao.getCustomerFromAccountNumber(Mockito.anyInt())).thenReturn(mockedPayToCustomer);
		
		
		bs.transfer(1234, 80.24, 100.00, 7881);
		
		Mockito.verify(customerDao, VerificationModeFactory.atLeast(2)).updateCustomerBalance(Mockito.any(), Mockito.any());
	}
	
	
	
}
