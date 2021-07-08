package com.revature.application;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface HelperFunctions {
	BankAccount getBankAccountWithName(String name);
	void run();
	List<BankAccount> getBankAccountsForUser(User u);
}
