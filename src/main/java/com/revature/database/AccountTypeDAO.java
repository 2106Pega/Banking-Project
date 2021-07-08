package com.revature.database;

import com.revature.models.AccountType;

public interface AccountTypeDAO extends GenericDAO<AccountType> {
	AccountType getByName(String s);
}
