package bank;

public class Employee extends User {

	@Override
	void printOptions() {
		// TODO Auto-generated method stub
		System.out.println("What would you like to do? (1. View bank account. 2. Make a transaction. 3. Create a bank account. 4. Money transfer. 5. Exit.)");
	}

	@Override
	int processChoice(int choice) {
		// TODO Auto-generated method stub
		return 0;
	}
}
