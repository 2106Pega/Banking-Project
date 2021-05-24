package com.paul.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.paul.dao.*;

public class Employee extends User {
	Scanner in = new Scanner(System.in);
	public Employee(int uId, String uname, String passwd) { 
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
	}
	
	@Override
	public void printOptions() {
		System.out.println("What would you like to do? (1. Approve/reject accounts. 2. View an account. 3. View transaction log. 4. Exit.)");
	}

	@Override
	public int processChoice(int choice) {
		BankDAO dao = new BankDAOImpl();
		switch(choice) {
		case 1:
			approveAccounts();
			break;
		case 2:
			ArrayList<Client> allClients = dao.getAllClients();
			int customerChoice = -1;
			for (int i = 0; i < allClients.size(); i++) {
				System.out.println(String.format("Client index %d: %s", i, allClients.get(i).username));
			}
			System.out.println("Which client do you want to view?");
			customerChoice = in.nextInt();
			in.nextLine();
			
			if (customerChoice < 0 || customerChoice >= allClients.size()) {
				System.out.println("Sorry, that customer number is not a valid choice!");
				break;
			}
			ArrayList<Account> custAccounts = dao.getAccounts(allClients.get(customerChoice).userId);
			for (Account acct : custAccounts) {
				System.out.println(String.format("Account %d: %f", acct.accountID, acct.balance));
			}
			if (custAccounts.size() == 0) {
				System.out.println("This custoemr doesn't seem to have any approved accounts.");
			}
			break;
		case 3:
			parseLog();
			break;
		case 4: default:
			System.out.println("Goodbye!");
			in.close();
			System.exit(0);
			break;
		}
		
		return 0;
	}
	
	void approveAccounts() {
		BankDAO dao = new BankDAOImpl();
		ArrayList<Message> pendingAccounts = dao.getMessageInbox(Message.EMPLOYEE_RID);
		
		if (pendingAccounts.size() == 0) {
			System.out.println("There are no pending accounts currently.");
		}
		
		for (Message msg : pendingAccounts) {
			Client applicant = dao.getClient(msg.senderID);
			System.out.println(String.format("Applicant: %s. Starting balance: %f. Approve? (y/n)", applicant.username, msg.balance));
			String ans = in.nextLine();
			if (ans.equals("Yes") || ans.equals("yes") || ans.equals("y") || ans.equals("Y")) {
				dao.createBankAccount(msg.senderID, msg.balance);
				System.out.println("Account application approved.");
			} else {
				System.out.println("Account application rejected.");
			}
			dao.deleteMessage(msg.messageID);
		}
	}
	
	void parseLog() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\paulk\\eclipse-workspace\\bank-maven\\logs\\logfile.log"));
			String l = reader.readLine();
			while (l != null) {
				if (l.contains("INFO")) { System.out.println(l); } //INFO will be tagged with all transaction informations, hiding other log entries
				l = reader.readLine();
			}
			reader.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
}
