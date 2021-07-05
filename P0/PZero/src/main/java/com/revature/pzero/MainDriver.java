package com.revature.pzero;

import java.sql.Connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.presentation.DisplayTerminalCustomer;
import com.revature.pzero.repository.Bank;
import com.revature.pzero.repository.BankImpl;
import com.revature.pzero.util.ConnectionPoint;

public class MainDriver {
	
	public static void main(String[] args) {
//		DisplayTerminal terminal = new DisplayTerminal();
//		terminal.welcome();
		
//		Bank bank = new BankImpl();
//		
////		//int id, Double balance, String nickName, boolean approved
//		Account dummyAccount = new Account(1, 7324.00, "Good account", true);
//		Account secondDummyAccount = new Account(1, 893673.73, "So much money" ,false);
//		
//		//int id, String firstName, String lastName, String userType, String userName, String password
//		User dummyUser = new User(0, "Tom", "Nook", "Customer", "NookParadise", "P4ssword");
//		User secondDummyUser = new User(0, "Timmy", "Nook", "Customer", "DifferentUsername", "Whoops");
//		
//		boolean a = bank.newUser(dummyUser, "Customer");
//		boolean b = bank.newUser(secondDummyUser, "Employee");
//		
//		boolean c = bank.newAccount(dummyAccount);
//		boolean d = bank.newAccount(secondDummyAccount);
//		
//		System.out.println("A: " + a + "\nB: " + b + "\nC: " + c + "\nD: " + d);
		
//		Account a = bank.viewAccountByAccountId(1);
//		System.out.println(a.toString());
		
		
		System.out.println("MainDriver: END.");
	}

}
