package com.revature.DAO;

import java.util.List;

import com.revature.Models.Account;

public interface CustomerDAO 
{
	public boolean register(String username, String password);
	public boolean login(String username, String password);
	public boolean newAccount(String username, String type, int balance);
	public int viewBalance(int id, String username);
	public boolean withdraw(int amount, int id, String username);
	public boolean deposit(int amount, int id, String username);
	public boolean transfer(int amount, int source, int receiving, String username);
	public List<Account> viewAccounts(String username);
}
