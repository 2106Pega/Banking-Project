package com.revature.pzero;

import java.sql.Connection;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.presentation.DisplayTerminal;
import com.revature.pzero.presentation.DisplayTerminalCustomer;
import com.revature.pzero.presentation.DisplayTerminalEmployee;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.service.BankSystem;
import com.revature.pzero.service.BankSystemImpl;
import com.revature.pzero.util.ConnectionPoint;

public class MainDriver {
	
	final static Logger loggy = Logger.getLogger(MainDriver.class);
//	final static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		DisplayTerminal employee;
		DisplayTerminal customer;
		Scanner scanner = new Scanner(System.in);
		
		boolean quit = false;
		String prompt = "Are you a [1] customer or [2] employee? [-1] to quit.";
		while(quit == false) {
			System.out.println("Welcome to THE BANK!\n" + prompt);
			int decision = numberDecisions(prompt);
			switch(decision) {
				case 1:
					customer = new DisplayTerminalCustomer();
					customer.welcome();
					scanner = new Scanner(System.in);
					break;
				case 2:
					employee = new DisplayTerminalEmployee();
					employee.welcome();
					scanner = new Scanner(System.in);
					break;
				case -1:
					System.out.println("Thank you!");
					quit = true;
					break;
				default:
					System.out.println("Please input the appropriate input.");
					break;
			}
		}
		
		
		System.out.println("MainDriver: END.");
	}
	
	protected static int numberDecisions(String prompt) {
		Scanner scanner = new Scanner(System.in);
		int userChosenNumber = -2;
		boolean quit = false;
		
		while(userChosenNumber == -2 && quit == false) {
			String userInput = scanner.next();
			try {
				userChosenNumber = Integer.parseInt(userInput);
			}catch(Exception e) {
				System.out.println("Invalid input. Please input one of the following:/n" + prompt);
			}
		}
		
		return userChosenNumber;
	}

}
