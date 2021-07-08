package com.revature.service;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDao;
import com.revature.dao.UsersDaoImpl;
import com.revature.models.Users;
import com.revature.presentation.BankFrontImpl;

public class LogProcess {
	
	
	static Scanner sc = new Scanner(System.in);
	final static Logger logger = Logger.getLogger(LogProcess.class.getName());
	
	public static boolean userRegistration() {
		
		String input;
		UsersDao userDao = new UsersDaoImpl();
		System.out.println("please enter username:  ");
		input = sc.nextLine();
		Users tempUser = userDao.selectUserByUsername(input);
		
		if (tempUser != null && tempUser.getUser_name() != null) {
			logger.info("failed: The user name is existed!");
			System.out.println("Please try again!");
			return false;
		} else {
			System.out.println("please input your first name!");
			
			String firstName = sc.nextLine();
			System.out.println("please input your last name!");
			String lastName = sc.nextLine();
			userDao.createUser(new Users(1,input,firstName,lastName));
			logger.info("success: created a user in db, username: "+input);
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
			logger.info("failed: The username is not existed!");
			System.out.println("Please try again!");
			return null;
		} else {
			logger.info("success: username is existed in db, username= " + input);
			return tempUser;
		}
	}
	
	public static List<Users> getAllUsers() {
		UsersDao userDao = new UsersDaoImpl();
		return userDao.selectAllUsers();
		
	}

}
