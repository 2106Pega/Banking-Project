package com.revature.pzero.presentation;

import java.util.Scanner;

import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystemImpl;

public abstract class DisplayTerminal {
	
	Scanner scanner = new Scanner(System.in);
	BankSystemImpl bankSystem;
	String userType = "Either CUSTOMER or EMPLOYEE";
	
	String bankName = "[insert bank name here]";
	String divider = "----------------------------------------";
	
	//REMOVE
	String u = "username";
	String p = "password";

	
	//Bank terminal welcome prompt. Begins here.
	public void welcome() {
		String prompt = "[0] - Login?\n[1] - Sign Up?\n[-1] - Quit?";
		System.out.println("Welcome to " + bankName + ".\n" + prompt);
		
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
	
	//displays the sign up process
	public void displaySignUp() {
		System.out.println("Please be informed that all information you enter here on in\n" 
				 + "will be acocunted as correct and any information that is found\n"
				 + "to be incorrect could be liable to legal action.");
		
		boolean quit = false;
		boolean successful = false;
		
		while(quit == false && successful == false) {
			
			String prompt = "Please enter your first name: ";
			System.out.println(prompt);
			String firstName = validStringInput(prompt);
			
			prompt = "Please enter your last name: ";
			System.out.println(prompt);
			String lastName = validStringInput(prompt);
				
			boolean nameDone = areYouSure();
				
			if(nameDone == false) {
				quit = quitDecision();
				continue;
			}
			
			prompt = "Please input a username: ";
			System.out.println(prompt);
			String username = validStringInput(prompt);
			
			prompt = "Please input a password: ";
			System.out.println(prompt);
			String password = validStringInput(prompt);

			boolean credentialsDone = areYouSure();
			
			if(credentialsDone == true) {
				successful = bankSystem.createNewUser(firstName, lastName, userType, username, password);
			}else {
				quit = quitDecision();
			}
		}
	}
	
	//Assists when user must chose a number. 
	//Prompt is used when correct number isn't supplied and user must be reprompted
	protected int numberDecisions(String prompt) {
		int userChosenNumber = -2;
		boolean quit = false;
		
		while(userChosenNumber == -2 && quit == false) {
			String userInput = scanner.next();
			try {
				userChosenNumber = Integer.parseInt(userInput);
			}catch(Exception e) {
				System.out.println("Invalid input.");
				quit = quitDecision();
				if(quit != true) {
					System.out.println(prompt);
				}
			}
		}
		
		return userChosenNumber;
	}
	
	//Assists when user must chose a $ amount. 
	//Prompt is used when correct number isn't supplied and user must be reprompted
	protected double moneyDecisions(String prompt) {
		double userChosenNumber = -2;
		boolean quit = false;
		
		while(userChosenNumber == -2 && quit == false) {
			String userInput = scanner.next();
			try {
				userChosenNumber = Double.parseDouble(userInput);
			}catch(Exception e) {
				System.out.println("Invalid input.");
				quit = quitDecision();
				if(quit != true) {
					System.out.println("Please input one of the following:/n" + prompt);
				}
			}
		}
		
		return userChosenNumber;
	}
	
	//displays login prompt -> leads to accessAccount()
	protected void displayLogin() {
		System.out.println("Please login:");
		String userInput = "";
		boolean quit = false;
		User u = null;
		
		while(quit == false) {
			u = checkLogin();
			if(u != null) { 
				if(u.isUserApproved() == false) {
					System.out.println("User has yet to be approved. Please wait 24hrs from sign up for it to be approved. Thank you!\n");
					break;
				}
				accessAccount(u);
				break; 
			}
			quit = quitDecision();
		}	
	}
	
	//makes sure login input is appropriate and "safe"
	protected User checkLogin() {
		System.out.print("USERNAME: ");
		String username = scanner.next();
		System.out.print("PASSWORD: ");
		String password = scanner.next();
		
		if(username.length() > 25 || password.length() > 25) {
			System.out.println("Login unsuccessful. Please check over your credentials carefully.");
			return null;
		}
		
		boolean loginSuccessful = bankSystem.authenticate(username, password);
		
		if(loginSuccessful == true) {
			return bankSystem.login(username, password);
		}else {
			System.out.println("Login unsuccessful. Please check over your credentials carefully.");
		}
		
		return null;
	}
	
	//after the user logs in, their account is accessed
	protected abstract void accessAccount(User user);
	
	//helper method to assist in quits
	protected boolean quitDecision() {
		System.out.println("Quit? [yes/no]");
		return yesOrNoDecision("Quit?");
		
	}
	
	//  yes returns true | no returns false
	//helper method to assist with [yes/no] questions
	protected boolean yesOrNoDecision(String prompt) {
		boolean quit = false;
		boolean yesOrNo = false;
		
		while(quit == false) {
			String userInput = scanner.next().toLowerCase().trim();
			
			switch(userInput) {
				case "yes":
					yesOrNo = true;
					quit = true;
					break;
				case "no":
					yesOrNo = false;
					quit = true;
					break;
				default:
					System.out.println("Invalid input. Please input either [yes] or [no].\n" + prompt);
			}
		}
		return yesOrNo;
	}
	
	//helper method for double checking if user wants to proceed
	protected boolean areYouSure() {
		boolean yesOrNo = false;
		String prompt = "Are you sure [yes/no]? ";
		System.out.print(prompt);
		return yesOrNoDecision(prompt);
	}
	
	//makes sure user input is pretty and valid (no special characters, lengthy input, or leading/trailing spaces
	protected String validStringInput(String prompt) {
		String decision = "";
		
		while(decision.isBlank()) {
			decision = scanner.next();
			
			//remove all special characters and spaces in string
			int beforeLen = decision.length();
			decision = decision.trim();
			decision = decision.replaceAll("[^a-zA-Z0-9]", "");
			
			if(beforeLen > decision.length()) {
				System.out.println("All special characters removed.");
			}
			
			//make sure the length is of appropriate size
			if(decision.length() > 25 || decision.length() < 5) {
				System.out.println("Invalid input. Please input between 5 and 25 characters or less.");
				decision = "";
				System.out.println(prompt);
			}
		}
		
		return decision;
	}
}
