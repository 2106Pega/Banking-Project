package com.revature.service;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Users;

public class LogProcess {
	
	
	static Scanner sc = new Scanner(System.in);
	
	
	public static boolean userRegistration() {
		
		String input;
		UsersDao userDao = new UsersDaoImpl();
		System.out.println("please enter username:  ");
		input = sc.nextLine();
		Users tempUser = userDao.selectUserByUsername(input);
		
		if (tempUser != null && tempUser.getUser_name() != null) {
			System.out.println("The user name is existed!");
			System.out.println("Please try again!");
			return false;
		} else {
			System.out.println("please input your first name!");
			
			String firstName = sc.nextLine();
			System.out.println("please input your last name!");
			String lastName = sc.nextLine();
			userDao.createUser(new Users(1,input,firstName,lastName, null));
			return true;
		}
	}
	
	public static Users customerLogin() {
		
		String input;
		UsersDao userDao = new UsersDaoImpl();
		System.out.println("please enter username:  ");
		input = sc.nextLine();
		Users tempUser = userDao.selectUserByUsername(input);
		
		if (tempUser == null) {
			System.out.println("The username is not existed!");
			System.out.println("Please try again!");
			return null;
		} else {
			return tempUser;
		}
	}
	
	public static List<Users> getAllUsers() {
		UsersDao userDao = new UsersDaoImpl();
		return userDao.selectAllUsers();
		
	}

}
