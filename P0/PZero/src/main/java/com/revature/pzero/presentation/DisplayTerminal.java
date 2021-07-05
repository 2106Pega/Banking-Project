package com.revature.pzero.presentation;

import java.util.Scanner;

import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystemImpl;

public abstract class DisplayTerminal {
	
	public Scanner scanner = new Scanner(System.in);
	public BankSystemImpl bankSystem;
	public Bank bank = new BankImpl();
	
	public String bankName = "[insert bank name here]";
	public String divider = "----------------------------------------";
	
	//REMOVE
	String u = "username";
	String p = "password";

	
	public void welcome() {
		//Display welcome and choose between employee and customer login
	}
	
	public void displayLogin() {
//		System.out.println("Please login:");
//		String userInput = "";
//		boolean quit = false;
//		User u = null;
//		
//		while(quit == false) {
//			u = checkLogin();
//			if(!u.equals(null)) { break; } //if login is valid -> display user accounts
//			quit = quitDecision(); //if login wasn't valid, check if they want to quit
//		}	
//		
//		if(quit != true) {
//			displayAccounts(u);
//		}	
//		
//		System.out.println("Display Login: END");
	}
	
	public User checkLogin() {
		System.out.println("USERNAME: ");
		String username = scanner.next();
		System.out.println("PASSWORD: ");
		String password = scanner.next();
		
//		boolean userCheck = bankSystem.authenticateLoginUsername(username);
//		boolean passCheck = bankSystem.authenticateLoginPassword(password);
		
		boolean userCheck = username.equals(u);
		boolean passCheck = password.equals(p);
		
		if(userCheck == true && passCheck == true) {
			//return bankSystem.getUserViaLogin(username, password);
			return new User(1, "John", "Smith", "Customer", "" , "");
		}
		
		if(userCheck == false){
			System.out.println("USERNAME failed. Please check to make sure you entered the correct username.");
		}else if(passCheck == false){
			System.out.println("PASSWORD failed. Please check to make sure you entered the correct password.");
		}
		
		return null;//return false;
	}
	
	
	
	public boolean quitDecision() {
		boolean willQuit = true;
		System.out.println("Quit? [yes/no]");
		
		willQuit = yesOrNoDecision("Quit?");
		
		if(willQuit == true) {
			System.out.println("Thanks for using " + bankName + ".");
			return willQuit;
		}
		
		return willQuit;	
	}
	
	//  yes returns true | no returns false
	public boolean yesOrNoDecision(String prompt) {
		boolean yesOrNo = false;
		
		while(yesOrNo == false) {
			String userInput = scanner.next().toLowerCase();
			
			switch(userInput) {
				case "yes":
					yesOrNo = true;
					break;
				case "no":
					yesOrNo = false;
					break;
				default:
					System.out.println("Invalid input. Please input either [yes] or [no].\n" + prompt);
			}
		}
		return yesOrNo;
	}
	
	public boolean areYouSure() {
//		super.areYouSure();
		boolean yesOrNo = false;
		String prompt = "Are you sure?";
		return yesOrNoDecision(prompt);
	}
	
	public void logout() {
		//Logout
	}

}
