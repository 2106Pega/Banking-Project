package com.revature.service;

import java.util.Scanner;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.models.Accounts;

public class TransactionsProcess {

	static Scanner sc = new Scanner(System.in);

	public static boolean withdrawal(Accounts account, double amount) {
		if (amount > 0) {
			AccountsDao accountDao = new AccountsDaoImpl();
			accountDao.updateAccounts(account, amount * (-1));
		}
		return true;
	}

	public static boolean deposit(Accounts account, double amount) {
		if (amount > 0) {
			AccountsDao accountDao = new AccountsDaoImpl();
			accountDao.updateAccounts(account, amount);
		}

		return true;
	}

	public static boolean transfer(Accounts account, Accounts anotherAccount, double amount) {
		if (amount > 0) {
			TransactionsProcess.withdrawal(account, amount);
			TransactionsProcess.deposit(anotherAccount, amount);
		}
		return true;
	}

}
