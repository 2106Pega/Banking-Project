package com.revature.database;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface BankAccountDAO extends GenericDAO<BankAccount> {
	List<BankAccount> getFromUser(User u);

	List<BankAccount> getAccountsNeedingApproval();
	
	BankAccount getByName(String s, User u);

	BankAccount getPrimaryFromUser(User owner);
	
}
