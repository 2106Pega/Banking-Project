package com.revature.display;

import java.util.Scanner;

import com.revature.Dao.CustomerDao;
import com.revature.Dao.CustomerDaoImpl;
import com.revature.Dao.EmployeeDao;
import com.revature.Dao.EmployeeDaoImpl;
import com.revature.Dao.UserDao;
import com.revature.Dao.UserDaoImpl;
import com.revature.models.Account;
import com.revature.models.Employee;
import com.revature.models.User;

public class Display {
	UserDao u = new UserDaoImpl();
	CustomerDao c = new CustomerDaoImpl();
	EmployeeDao e = new EmployeeDaoImpl();

	public void main_menu()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Are you a customer or employee?");
		System.out.println("1. Customer");
		System.out.println("2. Employee");
		System.out.println("Please select the number of the options.");
		String ans = sc.nextLine();
		if(ans.equals("1")) {
			user_menu();
		}
		else if(ans.equals("2")) {
			employee_login();
		}
		else
		{
			System.out.println("Invalid input, please select again.");
			main_menu();
		}
	}
	
	public void employee_login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input username: ");
		String username = sc.nextLine();
		System.out.println("Please input password: ");
		String passcode = sc.nextLine();
		Employee employee = new Employee(username, passcode);
		if(e.login(employee))
		{
			employee_menu(employee);
		}
		else
		{
			System.out.println("Invalid input, please reselect the option.");
			employee_login();
		}
	}
	
	public void employee_menu(Employee employee)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Approve an account");
		System.out.println("2. Reject an account");
		System.out.println("3. View the information of an account");
		System.out.println("Please enter the number of the options.");
		String ans = sc.nextLine();
		switch(ans)
		{
		case "1":
			System.out.println("Please enter the username that you want to approve.");
			String username_a = sc.nextLine();
			System.out.println("Please enter the account name that you want to approve.");
			String account_name_a = sc.nextLine();
			Account account_a = new Account(username_a, account_name_a, false, 0);
			e.approve(account_a);
			
		case "2":
			System.out.println("Please enter the username that you want to reject.");
			String username_r = sc.nextLine();
			System.out.println("Please enter the account name that you want to reject.");
			String account_name_r = sc.nextLine();
			Account account_r = new Account(username_r, account_name_r, false, 0);
			e.reject(account_r);
		
		case "3":
			System.out.println("Please enter the username that you want to view.");
			String username_v = sc.nextLine();
			System.out.println("Please enter the account name that you want to view.");
			String account_name_v = sc.nextLine();
			Account account_v = new Account(username_v, account_name_v, false, 0);
			e.view_customer(account_v);
			
		default:
			System.out.println("Invalid input, please reselect the option.");
			employee_menu(employee);
		}		
	}
	
	public void user_menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you have an account?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		String ans = sc.nextLine();
		if(ans.equals("1") || ans.toLowerCase().equals("yes"))
		{
			user_login();
		}
		else if(ans.equals("2") || ans.toLowerCase().equals("no"))
		{
			user_apply();
		}
		else
		{
			System.out.println("Invalid input!");
			user_menu();		
		}
	}
	
	public void user_login()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input username: ");
		String username = sc.nextLine();
		System.out.println("Please input password: ");
		String passcode = sc.nextLine();
		User user = new User(username, passcode);
		if(u.login(user))
		{
			customer_menu(user);	
		}
		else
		{
			System.out.println("Invalid input, please do it again.");
			user_login();
		}
	}
	
	public void user_apply()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input username you want to register: ");
		String username_r = sc.nextLine();
		System.out.println("Please input password you want to register: ");
		String passcode_r = sc.nextLine();
		User user_r = new User(username_r, passcode_r);
		if (u.apply(user_r))
		{
			customer_menu(user_r);
		}
		else
		{
			System.out.println("Invalid input, please do it again.");
			user_apply();
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
			Account account_a = new Account(u.getUsername(), account_name_a, false, amount);
			c.apply_account(account_a);
			break;
			
		case "2":
			System.out.println("Please enter the account name you want to view the balance: ");
			String account_name_b = sc.nextLine();
			Account account_b = new Account(u.getUsername(), account_name_b, false, 0);
			c.view_balance(account_b);
			break;
		
		case "3":
			System.out.println("Please enter the account name you want to deposit: ");
			String account_name_d = sc.nextLine();
			Account account_d = new Account(u.getUsername(), account_name_d, false, 0);
			System.out.println("Please enter the amount you want to deposit: ");
			double amount_d = sc.nextDouble();
			c.deposite(account_d, amount_d);
			break;
			
		case "4":
			System.out.println("Please enter the account name you want to withdraw: ");
			String account_name_w = sc.nextLine();
			Account account_w = new Account(u.getUsername(), account_name_w, false, 0);
			System.out.println("Please enter the amount you want to withdraw: ");
			double amount_w = sc.nextDouble();
			c.deposite(account_w, amount_w);
			break;
		
		case "5":
			System.out.println("Please enter the account name you want to make transfer: ");
			String account_name_t = sc.nextLine();
			Account account_t = new Account(u.getUsername(), account_name_t, false, 0);
			System.out.println("Please enter the amount you want to transfer: ");
			double amount_t = sc.nextDouble();
			System.out.println("Please enter the username of another party: ");
			String username_r = sc.nextLine();
			System.out.println("Please enter the account name of another party: ");
			String account_name_r = sc.nextLine();
			Account account_r = new Account(username_r, account_name_r, false, 0);
			c.post(account_t, account_r, amount_t);
			break;
			
		case "6":
			System.out.println("Please enter the account name you want to make transfer: ");
			String account_name_rs = sc.nextLine();
			Account account_rs = new Account(u.getUsername(), account_name_rs, false, 0);
			System.out.println("Please enter the amount you want to receive: ");
			double amount_rs = sc.nextDouble();
			System.out.println("Please enter the username of another party: ");
			String username_ta = sc.nextLine();
			System.out.println("Please enter the account name of another party: ");
			String account_name_ta = sc.nextLine();
			Account account_ta = new Account(username_ta, account_name_ta, false, 0);
			c.post(account_rs, account_ta, amount_rs);
			break;
			
		default:
			System.out.println("Option is not valid. Will show the menu again.");
			customer_menu(u);
		}

	}
}
