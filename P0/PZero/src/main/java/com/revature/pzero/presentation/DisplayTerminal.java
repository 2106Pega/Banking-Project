package com.revature.pzero.presentation;

import java.util.Scanner;

import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystemImpl;

public abstract class DisplayTerminal {
	
	Scanner scanner = new Scanner(System.in);
	BankSystemImpl bankSystem;
	Bank bank = new BankImpl();
	String userType = "Either CUSTOMER or EMPLOYEE";
	
	String bankName = "[insert bank name here]";
	String divider = "----------------------------------------";
	
	//REMOVE
	String u = "username";
	String p = "password";

	
	//Bank terminal welcome prompt. Begins here.
	public void welcome() {
		String prompt = "[0] - Login?\\n[1] - Sign Up?\\n[-1] - Quit?";
		System.out.println("Welcome to " + bankName + "." + prompt);
		
		int decision = numberDecisions(prompt);
		
		switch(decision) {
		 	case 0:
		 		displayLogin();
		 		break;
		 	case 1:
		 		displaySignUp();
		 		break;
		 	case -1: //quit
		 		break;
		 	default:
		 		System.out.println("ERROR.");
		 		break;
		}
	}
	
	//customer and employee prompts must be overridden to create the correct user
	public void displaySignUp() {
//		System.out.println("DISPLAY SIGNUP");
		boolean quit = false;
		boolean successful = false;
		
		//int id, String firstName, String lastName, String userType
		while(quit == false && successful == false) {
			System.out.print("Please enter your first name: ");
			String firstName = scanner.next();
			System.out.print("Please enter your last name: ");
			String lastName = scanner.next();
			
			boolean nameDone = areYouSure();
			
			if(nameDone == false) {
				quit = quitDecision();
				continue;
			}
			
			System.out.println("Please input a username.");
			String username = scanner.next();
			System.out.println("Please input a password.");
			String password = scanner.next();
			
			boolean credentialsDone = areYouSure();
			
			if(credentialsDone == true) {
				successful = bankSystem.createNewUser(firstName, lastName, userType, username, password);
			}else {
				quit = quitDecision();
			}
		}
		
		if(quit != true) {
			System.out.println("Customer account successfully made.");
			welcome();
		}
	}
	
	//Assists when user must chose a number. 
	//Prompt is used when correct number isn't supplied
	protected int numberDecisions(String prompt) {
		int userChosenNumber = -1;
		boolean quit = false;
		
		while(userChosenNumber == -1 && quit == false) {
			String userInput = scanner.next();
			try {
				userChosenNumber = Integer.parseInt(userInput);
			}catch(Exception e) {
				System.out.println("Invalid input. Please input one of the following:/n" + prompt);
				quit = quitDecision();
			}
		}
		
		return userChosenNumber;
	}
	
	protected double moneyDecisions(String prompt) {
		double userChosenAmount = -1.0;
		boolean quit = false;
		
		while(userChosenAmount == -1.0 && quit == false) {
			String userInput = scanner.next();
			try {
				userChosenAmount = Double.parseDouble(userInput);
			}catch(Exception e) {
				System.out.println("Invalid input. Please input one of the following:/n" + prompt);
				quit = quitDecision();
			}
		}
		
		return userChosenAmount;
	}
	
	
	protected void displayLogin() {
		System.out.println("Please login:");
		String userInput = "";
		boolean quit = false;
		User u = null;
		
		while(quit == false) {
			u = checkLogin();
			if(!u.equals(null)) { 
				accessAccount(u);
				break; 
			} //if login is valid -> display user accounts
			quit = quitDecision(); //if login wasn't valid, check if they want to quit
		}	
//		
//		if(quit != true) {
//			displayAccounts(u);
//		}	
//		
//		System.out.println("Display Login: END");
	}
	
	protected User checkLogin() {
		System.out.println("USERNAME: ");
		String username = scanner.next();
		System.out.println("PASSWORD: ");
		String password = scanner.next();
		
//		boolean userCheck = bankSystem.authenticateLoginUsername(username);
//		boolean passCheck = bankSystem.authenticateLoginPassword(password);
		
//		boolean userCheck = username.equals(u);
//		boolean passCheck = password.equals(p);
		
		boolean loginSuccessful = bankSystem.authenticate(username, password);
		
		if(loginSuccessful == true) {
			return bankSystem.login(username, password);
//			return new User(1, "John", "Smith", "Customer", "" , "");
		}else {
			System.out.println("login unsuccessful. Please check over your credentials carefully.");
		}
		
		return null;//return false;
	}
	
	//after the user logs in, their account is accessed
	protected abstract void accessAccount(User user);
	
	protected boolean quitDecision() {
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
	protected boolean yesOrNoDecision(String prompt) {
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
	
	protected boolean areYouSure() {
//		super.areYouSure();
		boolean yesOrNo = false;
		String prompt = "Are you sure?";
		return yesOrNoDecision(prompt);
	}
	
//	protected void logout() {
//		//Logout
//		System.out.println("You have been logged out.");
//	}

}
