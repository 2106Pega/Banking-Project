package com.revature.presentation;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountsDao;
import com.revature.dao.AccountsDaoImpl;
import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Accounts;
import com.revature.models.Users;
import com.revature.service.AccountProcess;
import com.revature.service.EmployeeProcess;
import com.revature.service.LogProcess;
import com.revature.service.TransactionsProcess;

public class BankFrontImpl implements BankFront {

	static Scanner sc = new Scanner(System.in);

	@Override
	public void displayLogin() {
		System.out.println("1. New Customer? Please select 1 to register!");
		System.out.println("2. Customer! Please select 2 to log in!");
		System.out.println("3. Employee! Please select 3 to log in!");
		System.out.println("9. Exit? please select 9 to exit!");

	}

	@Override
	public void displayUserAccount() {
		System.out.println("1. View all current accounts, plese select 1 !");
		System.out.println("2. Create new bank account, plese select 2 !");
		System.out.println("9. Exit? please select 9 to exit!");

	}

	public static void displayAccount(List<Accounts> list) {
		System.out.println("Please select the corresponding number to operate the account!");
		int input = sc.nextInt();

		if (input < 1 || input > list.size()) {
			System.out.println("Please select the correct number!");
			System.out.println("Please try again!");
			BankFrontImpl.displayAccount(list);
		}

		System.out.println("1) withdrawal money");
		System.out.println("2) deposit money");
		System.out.println("3) transfer to another account");
		int chosen = sc.nextInt();

		while (chosen < 1 || chosen > 3) {
			System.out.println("Please select the correct number!");
			System.out.println("Please try again!");
			System.out.println();
			System.out.println("1) withdrawal money");
			System.out.println("2) deposit money");
			System.out.println("3) transfer to another account");
			chosen = sc.nextInt();
		}

		System.out.println();
		System.out.println("please input amount!");
		double amount = sc.nextDouble();

		while (Double.doubleToLongBits(amount) <= 0) {
			System.out.println();
			System.out.println("input error!");
			System.out.println("please input amount again!");
			amount = sc.nextDouble();
		}

		// withdrawal
		if (chosen == 1) {

			while (Double.doubleToLongBits(amount) > Double.doubleToLongBits(list.get(input - 1).getBanlance())) {
				System.out.println();
				System.out.println("Insufficient Balance!");
				System.out.println("please input amount again!");
				amount = sc.nextDouble();
			}

			TransactionsProcess.withdrawal(list.get(input - 1), amount);

			// deposit
		} else if (chosen == 2) {

			TransactionsProcess.deposit(list.get(input - 1), amount);

			// transfer
		} else if (chosen == 3) {

			while (Double.doubleToLongBits(amount) > Double.doubleToLongBits(list.get(input - 1).getBanlance())) {
				System.out.println();
				System.out.println("Insufficient Balance!");
				System.out.println("please input amount again!");
				amount = sc.nextDouble();
			}

			UsersDao userDao = new UsersDaoImpl();
			System.out.println();
			System.out.println("Please input another customer username: ");
			String anotherAccount = sc.nextLine();
			anotherAccount = sc.nextLine();

			while (userDao.selectUserByUsername(anotherAccount) == null) {
				System.out.println();
				System.out.println("can not find it, try agian!");
				System.out.println("Please input another customer username: ");
				anotherAccount = sc.nextLine();
			}

			Users userTemp = userDao.selectUserByUsername(anotherAccount);
			List<Accounts> accountList = AccountProcess.viewAccounts(userTemp.getID());
			System.out.println("Please select the corresponding number that you want to transfer money to the account!");
			int selectAccount = sc.nextInt();
			
			TransactionsProcess.transfer(list.get(input - 1), accountList.get(selectAccount-1), amount);
			
			System.out.println();
			
			
			
			
			
			

			// TransactionsProcess.transfer(list.get(input-1),
			// userDao.selectUserByUsername(anotherAccount));

		}

	}

	public static void frontEnd(BankFront bankApp) {

		while (true) {
			bankApp.displayLogin();
			String num = sc.nextLine();

			if (num.equals("9"))
				break;

			else if (num.equals("1")) {
				boolean successful = LogProcess.userRegistration();
				while (!successful) {
					successful = LogProcess.userRegistration();
				}

			} else if (num.equals("2")) {

				Users tempCustomer = LogProcess.customerLogin();
				while (tempCustomer == null) {
					tempCustomer = LogProcess.customerLogin();
				}

				bankApp.displayUserAccount();
				String chose = sc.nextLine();

				// view all accounts
				if (chose.equals("1")) {
					List<Accounts> accList = AccountProcess.viewAccounts(tempCustomer.getID());
					BankFrontImpl.displayAccount(accList);

					// break;

					// create new bank account
				} else if (chose.equals("2")) {

					AccountProcess.createAccount(tempCustomer.getID());

				}

			} else if (num.equals("3")) {

				AccountsDao accountDao = new AccountsDaoImpl();
				boolean exist = EmployeeProcess.isExists();
				while (!exist) {
					System.out.println("username or password is not matched!");
					System.out.println("Please try again!");
					System.out.println("");
					exist = EmployeeProcess.isExists();
				}

				EmployeeProcess.viewCustomers();
				int chose = sc.nextInt();
				UsersDao userDao = new UsersDaoImpl();
				while (userDao.selectAccountsById(chose) == null) {
					System.out.println("input number is not matched customers number!");
					System.out.println("Please try again!");
					EmployeeProcess.viewCustomers();
					chose = sc.nextInt();
				}

				System.out.println();
				List<Accounts> accountList = AccountProcess.viewAllAccounts(chose);
				System.out.println("Please enter the corresponding account number to approve it!");
				int choseNext = sc.nextInt();

				accountDao.approveAccounts(accountList.get(choseNext - 1).getAccountID());

				AccountProcess.viewAllAccounts(chose);

				// EmployeeProcess.viewCustomers();

				// break;

			} else {
				System.out.println("Please enter the correct number, and try again!");
			}

		}
	}

}
