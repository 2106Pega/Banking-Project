package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.models.Accounts;
import com.revature.presentation.BankFrontImpl;


public class AccountProcess {
	
	static Scanner sc = new Scanner(System.in);
	final static Logger logger = Logger.getLogger(AccountProcess.class.getName());
	
	public static void createAccount (int userId) {
		String input;
		AccountsDao accountsDao = new AccountsDaoImpl();
		System.out.println("please enter account name:  ");
		input = sc.nextLine();
		accountsDao.createAccount(new Accounts(0, input,0.00, false,userId));
	}
	
	public static List<Accounts> viewAccounts(int userId) {
		List<Accounts> accountsLsit = new ArrayList();
		AccountsDao accountsDao = new AccountsDaoImpl();
		List<Accounts> accountsList = accountsDao.selectAccountsByUserId(userId);
		int account = 1;
		for (Accounts s : accountsList) {
			if (!s.isApprove()) continue;
			//System.out.println(account + ") " + s.toString());
			logger.info (account + ") " + s.toString());
			accountsLsit.add(s);
			account++;
		}
		System.out.println();
		System.out.println();
		return accountsLsit;

	}
	
	public static List<Accounts> viewAllAccounts(int userId) {
		List<Accounts> accountsLsit = new ArrayList();
		AccountsDao accountsDao = new AccountsDaoImpl();
		List<Accounts> accountsList = accountsDao.selectAccountsByUserId(userId);
		int account = 1;
		for (Accounts s : accountsList) {
			if (s.isApprove()) continue;
			System.out.println(account + ") " + s.toString() + "	waiting approve");
			accountsLsit.add(s);
			account++;
		}
		
		for (Accounts s : accountsList) {
			if (!s.isApprove()) continue;
			System.out.println(account + ") " + s.toString() + "	approved!");
			accountsLsit.add(s);
			account++;
		}
		System.out.println();
		return accountsLsit;

	}

}
