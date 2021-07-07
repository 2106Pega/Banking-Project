package com.revature.display;

import java.util.Scanner;

import com.revature.Dao.CustomerDao;
import com.revature.Dao.CustomerDaoImpl;
import com.revature.Dao.UserDao;
import com.revature.Dao.UserDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class Display {
	UserDao u = new UserDaoImpl();
	CustomerDao c = new CustomerDaoImpl();

	
	public void user_menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you have an account?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		String ans = sc.nextLine();
		if(ans.equals("1") || ans.toLowerCase().equals("yes"))
		{
			System.out.println("Please input username: ");
			String username = sc.nextLine();
			System.out.println("Please input password: ");
			String passcode = sc.nextLine();
			User user = new User(username, passcode);
			u.login(user);
			customer_menu(user);
			sc.close();
		}
		else if(ans.equals("2") || ans.toLowerCase().equals("no"))
		{
			System.out.println("Please input username you want to register: ");
			String username_r = sc.nextLine();
			System.out.println("Please input password you want to register: ");
			String passcode_r = sc.nextLine();
			User user_r = new User(username_r, passcode_r);
			u.apply(user_r);
			customer_menu(user_r);
			sc.close();
		}
		else
		{
			System.out.println("Invalid input!");
			user_menu();
			
		}
	}
	
	public void customer_menu(User u) {
		Scanner sc = new Scanner(System.in);
		System.out.println("What do you want?");
		System.out.println("1. open a new account");
		System.out.println("2. view balance of specific account");
		System.out.println("3. deposit to your specific account");
		System.out.println("4. withdraw to your specific account");
		System.out.println("5. post a money transfer to another account");
		System.out.println("6. accept a money transfer from another account");
		System.out.println("Please enter your option(number of the menu): ");
		String option = sc.nextLine();
		
		switch(option.toLowerCase())
		{
		case "1":
			System.out.println("Please enter the account name you want to name: ");
			String account_name_a = sc.nextLine();
			System.out.println("Please enter the initial amount you want to put in this account: ");
			double amount = sc.nextDouble();
			Account account_a = new Account(u.getUsername(), account_name_a, amount);
			c.apply_account(account_a);
			break;
			
		case "2":
			System.out.println("Please enter the account name you want to view the balance: ");
			String account_name_b = sc.nextLine();
			Account account_b = new Account(u.getUsername(), account_name_b, 0);
			c.view_balance(account_b);
			System.out.println("The current balance of account " + account_b.getAccount_name() + "is " + account_b.getBalance());
			break;
		
		case "3":
			System.out.println("Please enter the account name you want to deposit: ");
			String account_name_d = sc.nextLine();
			Account account_d = new Account(u.getUsername(), account_name_d, 0);
			System.out.println("Please enter the amount you want to deposit: ");
			double amount_d = sc.nextDouble();
			c.deposite(account_d, amount_d);
			System.out.println("The current balance of account " + account_d.getAccount_name() + "is " + account_d.getBalance());
			break;
			
		case "4":
			System.out.println("Please enter the account name you want to withdraw: ");
			String account_name_w = sc.nextLine();
			Account account_w = new Account(u.getUsername(), account_name_w, 0);
			System.out.println("Please enter the amount you want to withdraw: ");
			double amount_w = sc.nextDouble();
			c.deposite(account_w, amount_w);
			System.out.println("The current balance of account " + account_w.getAccount_name() + "is " + account_w.getBalance());
			break;
		
		case "5":
			System.out.println("Please enter the account name you want to make transfer: ");
			String account_name_t = sc.nextLine();
			Account account_t = new Account(u.getUsername(), account_name_t, 0);
			System.out.println("Please enter the amount you want to transfer: ");
			double amount_t = sc.nextDouble();
			System.out.println("Please enter the username of another party: ");
			String username_r = sc.nextLine();
			System.out.println("Please enter the account name of another party: ");
			String account_name_r = sc.nextLine();
			Account account_r = new Account(username_r, account_name_r, 0);
			c.post(account_t, account_r, amount_t);
			System.out.println("The current balance of account " + account_t.getAccount_name() + "is " + account_t.getBalance());
			break;
			
		case "6":
			System.out.println("Please enter the account name you want to make transfer: ");
			String account_name_rs = sc.nextLine();
			Account account_rs = new Account(u.getUsername(), account_name_rs, 0);
			System.out.println("Please enter the amount you want to receive: ");
			double amount_rs = sc.nextDouble();
			System.out.println("Please enter the username of another party: ");
			String username_ta = sc.nextLine();
			System.out.println("Please enter the account name of another party: ");
			String account_name_ta = sc.nextLine();
			Account account_ta = new Account(username_ta, account_name_ta, 0);
			c.post(account_rs, account_ta, amount_rs);
			System.out.println("The current balance of account " + account_rs.getAccount_name() + "is " + account_rs.getBalance());
			break;
			
		default:
			System.out.println("Option is not valid, please leave the menu.");
		}
		
		sc.close();
	}
}
