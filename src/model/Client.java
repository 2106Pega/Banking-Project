package model;

import java.util.ArrayList;

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
		System.out.println("What would you like to do? (1. View bank account. 2. Make a transaction. 3. Create a bank account. 4. Money transfer. 5. Exit.)");
	}

	@Override
	public int processChoice(int choice) {
		// TODO Auto-generated method stub
		return 0;
	}
}
