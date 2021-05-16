package model;

public class Employee extends User {
	
	public Employee(int uId, String uname, String passwd) { 
		this.userId = uId;
		this.username = uname;
		this.password = passwd;
	}
	
	@Override
	public void printOptions() {
		// TODO Auto-generated method stub
		System.out.println("What would you like to do? (1. Approve/reject accounts. 2. View an account. 3. View transaction log. 4. Exit.)");
	}

	@Override
	public int processChoice(int choice) {
		// TODO Auto-generated method stub
		return 0;
	}
}
