package model;

import java.util.ArrayList;
import java.util.Scanner;

import dao.*;

public class Employee extends User {
	
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
		switch(choice) {
		case 1:
			approveAccounts();
			break;
		case 2:
			break;
		case 3:
			break;
		case 4: default:
			System.out.println("Goodbye!");
			System.exit(0);
			break;
		}
		
		return 0;
	}
	
	void approveAccounts() {
		BankDAO dao = new BankDAOImpl();
		Scanner in = new Scanner(System.in);
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
}
