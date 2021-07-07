package com.revature.repo;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;

public interface AccountDAO {
	

	public boolean insertAccount(Account account);
	public boolean DepositIntoAccount(int accountID, double depositAmount);
	public boolean WithdrawFromAccount(int accountID, double withdrawAmount);
	public Account GetAccountByAccountID(int accountID);
	public boolean TransferMoneyBetweenAccounts(double transferAmount, int targetAccountID, 
			int sourceAccountID, Customer customer);
	public List<Account> GetAccountsByCustomerID(int cusomterID);
}
