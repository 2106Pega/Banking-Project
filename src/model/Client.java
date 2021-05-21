package model;

import java.util.ArrayList;
import java.util.Scanner;

import dao.*;

public class Client extends User {
	private ArrayList<Account> accounts;
	
	public Client(int uId, String uname, String passwd) { 
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
		this.accounts = null;
	}
	
	public Client(int uId, String uname, String passwd, ArrayList<Account> accounts) {
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
		this.accounts = accounts;
	}
	
	@Override
	public void printOptions() {
		// TODO Auto-generated method stub
		System.out.println("What would you like to do? (1. View bank accounts. 2. Make a transaction. 3. Create a bank account. 4. Money transfer. 5. Exit.)");
	}

	@Override
	public int processChoice(int choice) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		switch(choice) {
		case 1:
			getMyAccounts();
			for (Account acct : this.accounts) {
				System.out.println(String.format("Account %d: %f", acct.accountID, acct.balance));
			}
			if (this.accounts.size() == 0) {
				System.out.println("You don't seem to have any approved accounts.");
			}
			break;
		case 2:
			break;
		case 3:
			System.out.println("With how much do you want to apply for an account with?: ");
			double bal = in.nextDouble();
			in.nextLine();
			applyForBankAccount(bal);
			break;
		case 4:
			break;
		case 5: default:
			System.out.println("Goodbye!");
			in.close();
			System.exit(0);
			break;
		}
		
		return 0;
	}
	
	void applyForBankAccount(double startingAmount) {	
		Message app = new Message(this.userId, Message.EMPLOYEE_RID, startingAmount);
		BankDAO dao = new BankDAOImpl();
		dao.sendMessage(app);
		System.out.println("You have applied for a bank account!");
	}
	
	void getMyAccounts() {
		BankDAO dao = new BankDAOImpl();
		ArrayList<Account> accts = dao.getAccounts(this.userId);
		this.accounts = accts;
	}
}
