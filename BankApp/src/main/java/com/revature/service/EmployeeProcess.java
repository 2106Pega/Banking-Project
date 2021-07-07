package com.revature.service;

import java.util.Scanner;

import com.revature.dao.EmployeesDao;
import com.revature.dao.EmployeesDaoImpl;
import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Users;

public class EmployeeProcess {
	static Scanner sc = new Scanner(System.in);
	
	public static boolean isExists() {
		System.out.println("Please intput employee username: ");
		String userName = sc.nextLine();
		System.out.println("Please intput employee password: ");
		String passwords = sc.nextLine();
		EmployeesDao employeeDao = new EmployeesDaoImpl();
		return employeeDao.selectByUsernamePasswords(userName, passwords);
	}
	
	
	public static void viewCustomers() {
		UsersDao userDao = new UsersDaoImpl();
		System.out.println();
		System.out.println("All customers information: ");
		for (Users user : userDao.selectAllUsers()) {
			System.out.println(user.toString());
		}
		System.out.println();
		System.out.println("Please enter the corresponding customer number to check customer accounts!");
		
		
	}
	
	
	
	
	

}
