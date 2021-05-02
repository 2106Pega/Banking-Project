package bank;

import java.util.Scanner;

public class BankPortal {
	private static User loggedOnUser;
	static Scanner in = new Scanner(System.in);
	
	static void signOn() {
		String uname;
		String passwd;
		
		System.out.print("Please enter your username: ");
		uname = in.nextLine();
		
		System.out.print("Please enter your password: ");
		passwd = in.nextLine();
		
		System.out.println("TODO: working user login");
	}
	
	static void createNewUser() {
		String uname;
		String passwd;
		int type;
		boolean setPasswd = false;
		
		System.out.print("Please enter your account type (1. user; 2. employee) :");
		type = in.nextInt();
		in.nextLine();
		
		System.out.print("Please enter your username: ");
		uname = in.nextLine();
		
		
		while (!setPasswd) {
			System.out.print("Please enter your password: ");
			passwd = in.nextLine();
			
			System.out.print("Please confirm your password: ");
			
			if (passwd.equals(in.nextLine()) == false) {
				System.out.println("Passwords do not match, please try again.");
			} else {
				setPasswd = true;
			}
		}
		
		System.out.println("TODO: working user signup");
	}
	
	static void userMenu() {
		boolean running = true;
		while (running) {
			loggedOnUser.printOptions();
			loggedOnUser.processChoice(in.nextInt());
		}
	}
	
	public static void main(String[] args) {
		boolean running = true;
		while (running) {
			System.out.println("What would you like to do?");
			System.out.println("1. Log on; 2. Create new account. 3. Exit");
			switch(in.nextInt()) {
				case 1:
					in.nextLine();
					signOn();
					break;
				case 2:
					createNewUser();
					break;
				case 3:
					running = false;
					in.close();
					break;
			}
					
		}
	}
}
