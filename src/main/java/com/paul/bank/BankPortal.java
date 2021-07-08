package com.paul.bank;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paul.model.*;
import com.paul.dao.*;

public class BankPortal {
	private static User loggedOnUser = null;
	private static BankDAO dao = new BankDAOImpl();
	static Scanner in = new Scanner(System.in);
	private static final Logger LOG = LogManager.getLogger(BankPortal.class);
	
	public static String hashPasswd(String passwd) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passwd.getBytes());
		byte[] digest = md.digest();
		String hash = new String(digest, StandardCharsets.UTF_8);
		return hash;
	}
	
	public static int handleInput() {
		boolean gotInput = false;	
		int res = -1;
		while (!gotInput) {
			String line = in.nextLine();
			line = line.replaceAll("[\\n\\t ]", "");
			try {
				res = Integer.parseInt(line);
				gotInput = true;
			} catch(NumberFormatException ex) {
				System.out.println("Sorry, that input isn't valid!");
			}
		}
		return res;
	}
	
	static void signOn() throws NoSuchAlgorithmException {
		String uname;
		String passwd;
		int type;
		
		System.out.print("Please enter your account type (1. Client; 2. Employee): ");
		type = handleInput();
		
		System.out.print("Please enter your username: ");
		uname = in.nextLine();
		
		System.out.print("Please enter your password: ");
		passwd = in.nextLine();
		
		loggedOnUser = dao.verifyUser(uname, hashPasswd(passwd), type);
		if (loggedOnUser != null) {
			System.out.println("Welcome, " + loggedOnUser.getUsername());
			LOG.trace("User " + loggedOnUser.getUsername() + " logged on");
			userMenu();
		} else {
			System.out.println("Sorry, username or password is wrong!");
		}
	}
	
	static void createNewUser() throws NoSuchAlgorithmException {
		String uname;
		String passwd = "";
		int type;
		boolean setPasswd = false;
		
		System.out.print("Please enter your account type (1. Client; 2. Employee): ");
		type = handleInput();
		
		System.out.print("Please enter your username: ");
		uname = in.nextLine();
		
		
		while (!setPasswd) {
			System.out.print("Please enter your password: ");
			passwd = in.nextLine();
			
			System.out.print("Please confirm your password: ");
			
			if (passwd.equals(in.nextLine()) == false) {
				System.out.println("Passwords do not match, please try again.");
			} else {
				setPasswd = true;
			}
		}
		
		BankDAO dao = new BankDAOImpl();
		dao.commitUser(uname, hashPasswd(passwd), type);
		System.out.println("Welcome to the system, " + uname);
		LOG.trace("New User " + uname + " created");
	}
	
	static void userMenu() {
		boolean running = true;
		while (running) {
			loggedOnUser.printOptions();
			loggedOnUser.processChoice(handleInput());
		}
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		boolean running = true;
		while (running) {
			System.out.println("What would you like to do?");
			System.out.println("1. Log on; 2. Create new account. 3. Exit");
			switch(handleInput()) {
				case 1:
					signOn();
					break;
				case 2:
					createNewUser();
					break;
				case 3: default:
					running = false;
					in.close();
					break;
			}
					
		}
	}
}
