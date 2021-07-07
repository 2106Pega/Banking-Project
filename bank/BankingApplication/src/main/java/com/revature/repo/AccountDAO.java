package com.revature.repo;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	
	//Create
	public void createAccount(int userId, double startingBalance);
	
	public void createApplication(int userId, double startingBalance);
		
	//Read
	public List<Account> selectAllApplications();
	
	public List<Account> selectAllAccounts();
	
	public List<Account> selectAccountsbyUser(int userId);
	
	public Account selectAccountbyId(int accountId);
		
	//Update 
	public void updateAccount(int accountId, double depositAmount);
		
	//Delete 
	public void deleteAccount(Account acc);

	public void deleteApplication(Account acc);
		
}
