package bank;

import java.util.ArrayList;

public class Client extends User {
	String firstName;
	String lastName;
	private ArrayList<Account> accounts;
	
	@Override
	void printOptions() {
		// TODO Auto-generated method stub
		System.out.println("What would you like to do? (1. Approve/reject accounts. 2. View an account. 3. View transaction log. 4. Exit.)");
	}

	@Override
	int processChoice(int choice) {
		// TODO Auto-generated method stub
		return 0;
	}
}
