package com.revature.service;

import java.util.List;

import com.revature.models.Account;

public interface EmployeeManager {

	List<Account> reviewNewAccounts();

	List<Account> viewCustomerAccounts(int accountNumber);

	void removeApplication(int accountID);
}
