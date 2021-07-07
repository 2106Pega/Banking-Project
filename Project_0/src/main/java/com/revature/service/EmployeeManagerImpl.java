package com.revature.service;

import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.ApplicationDao;
import com.revature.dao.CustomerDao;
import com.revature.models.Account;
import com.revature.models.Customer;

public class EmployeeManagerImpl implements EmployeeManager {

	CustomerDao cDao;
	AccountDao aDao;
	ApplicationDao appDao;
	Customer currentCustomerInstance;
	Account currentAccountInstance;
	
	@Override
	public List<Account> viewCustomerAccounts(int accountNumber) {
		return aDao.getAllCustomerAccounts(accountNumber);
	}

	@Override
	public List<Account> reviewNewAccounts() {
		return appDao.selectAllApplications();
	}

	@Override
	public void removeApplication(int accountID) {
		appDao.removeApplication(accountID);		
	}

}
