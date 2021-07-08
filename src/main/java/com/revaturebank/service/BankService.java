package com.revaturebank.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revaturebank.dao.CustomerDao;
import com.revaturebank.dao.CustomerDaoImpl;
import com.revaturebank.dao.UserDao;
import com.revaturebank.dao.UserDaoImpl;
import com.revaturebank.model.Customer;
import com.revaturebank.model.User;

public class BankService {
	private UserDao userDao;
	private CustomerDao customerDao;
	
	public BankService() {
		this.userDao = new UserDaoImpl();
		this.customerDao = new CustomerDaoImpl();
	}
	
	public BankService(UserDao userDao, CustomerDao customerDao) {
		this.userDao = userDao;
		this.customerDao = customerDao;
	}
	


	private static final String CUSTOMER_USER = "customer";
	private static final String EMPLOYEE_USER = "employee";

	public User customerLogIn(String userName, String password) throws Exception {
		User user = userDao.getUser(userName);

		if(user.getPassword() == null) {
			throw new Exception("UserName/Password does not match");
		} else if(user.getUserName() == null) {
			throw new Exception("UserName/Password does not match");
	
		} else if(user.getPassword().equals(password) && user.getUserType().equals(CUSTOMER_USER)){
			return user;
		} else {
			return null;
		}
	
	}

	public User employeeLogIn(String userName, String password) throws Exception {
		 User user = userDao.getUser(userName);
		if(user.getPassword().equals(password) && user.getUserType().equals(EMPLOYEE_USER)) {
			return user; }
		
	
		return null;
	}

	public void saveUser(String userName, String password) throws Exception {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setUserType(CUSTOMER_USER);
		userDao.saveUser(user);
	}

	public void applyAccount(String accountType, String firstName, String lastName, String email, String phoneNumber,
			String accountName, Double balance, Integer id) {
		Customer customer = new Customer();
		customer.setAccountType(accountType);
		customer.setAccountName(accountName);
		customer.setBalance(balance);
		customer.setCustomerId(id);
		customer.setEmail(email);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setStatus("pending");
		customer.setPhoneNumber(phoneNumber);
		customerDao.saveCustomer(customer);
	}

	public List<Customer> getCustomers(Integer id) {
		return customerDao.getCustomersFromCustomerId(id);
	}

	public void deposit(Integer accountNumber, Double amount, Double balance) {
		Double updatedBalance = balance + amount;
		customerDao.updateCustomerBalance(accountNumber, updatedBalance);
	}

	public void withdraw(Integer accountNumber, Double amount, Double balance) {
		Double updatedBalance = balance - amount;
		customerDao.updateCustomerBalance(accountNumber, updatedBalance);
	}

	public void transfer(Integer accountNumber, Double amount, Double balance, Integer payToAccountNumber) {
		Double updatedFromAccountBalance = balance - amount;
		customerDao.updateCustomerBalance(accountNumber, updatedFromAccountBalance);
		
		System.out.println("payto :: " + payToAccountNumber);
		Customer payToCustomerAccount = customerDao.getCustomerFromAccountNumber(payToAccountNumber);
		Double updatedToAccountBalance = payToCustomerAccount.getBalance() + amount;
		customerDao.updateCustomerBalance(payToAccountNumber, updatedToAccountBalance);
		
	}

	public Customer getAccountsByCustomerId(Integer id) {
		return customerDao.getCustomerFromCustomerId(id);
	}

	public List<Customer> getCustomersPendingAccounts() {
		return customerDao.getCustomersFromStatus("pending");
	}

	public void updatePendingAccounts(String status, Integer accountNumber) {
		customerDao.updateCustomerAccountStatus(status, accountNumber);
	}
	
	public boolean isAccountActive(Customer customer) {
		return customer.getStatus().equals("active");
	}
	
}
