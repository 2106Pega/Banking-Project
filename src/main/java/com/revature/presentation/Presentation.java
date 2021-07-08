/*************************
 * Author: Jason Hubbs
 * Date: 07-07-21
 */
package com.revature.presentation;

import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;

public class Presentation {
	Scanner scanner;
	PresentationService presentationService;
	
	public Presentation() {
		this.scanner = new Scanner(System.in);
		this.presentationService = new PresentationService();
	}
	
	public void DisplayEmployeeLoginPage() {
		System.out.println("**********Employee login**********\n");
		String username = null, password = null;
		User user = null;
		
		System.out.println("Please enter your username (or Q to exit employee login): ");
		username = scanner.nextLine();
		if(username.equals("Q")) {
			DisplayInitialPage();
			return;
		}
		System.out.println("Please enter your password (or Q to exit employee login): ");
		password = scanner.nextLine();
		if(password.equals("Q")) {
			DisplayInitialPage();
			return;
		}
		user = presentationService.GetEmployeeByUsernameAndPassword(username, password);

		while(user == null) {
			System.out.println("Employee not found. Please try again.\n");
			System.out.println("Please enter your username (or Q to exit employee login): ");
			username = scanner.nextLine();
			if(username.equals("Q")) {
				DisplayInitialPage();
				return;
			}
			System.out.println("Please enter your password (or Q to exit employee login): ");
			password = scanner.nextLine();
			if(password.equals("Q")) {
				DisplayInitialPage();
				return;
			}
			user = presentationService.GetEmployeeByUsernameAndPassword(username, password);
		}
		DisplayEmployeePage();
	}
	
	public void DisplayLoginPage() {
		System.out.println("\n\n\n**********Customer login**********\n");
		String username = null, password = null;
		Customer customer = null;

		while(customer == null) {
			System.out.println("Please enter your username: ");
			username = scanner.nextLine();
			System.out.println("Please enter your password: ");
			password = scanner.nextLine();
			customer = presentationService.GetCustomerByUsernameAndPassword(username, password);
			if(customer != null) {
				DisplayCustomerPage(customer);
			}else {
				System.out.println("No account exists with that username/password, please try again.");
				username = null;
				password = null;
			}
		}
	}
	
	public void DisplayNewBankAccountSignup(Customer customer) {
		System.out.println("\n\n\n**********New Account Signup**********\n");
		String depositAmount = null;
		System.out.println("Welcome to new account signup. You will have access after an employee approves the new account.\n");
		System.out.print("Please enter deposit amount (or enter QUIT to exit): ");
		depositAmount = scanner.nextLine();
		if(depositAmount.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
			
		while( ! (presentationService.IsDoubleParsable(depositAmount)) || !(presentationService.InsertAccountByCustomer(customer, Double.parseDouble(depositAmount)))) {
			System.out.print("Invalid deposit amount, please try again: ");
			depositAmount = scanner.nextLine();
			if(depositAmount.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
		}
		System.out.println("Account created successfully. Returning to main customer summary...");
		DisplayCustomerPage(customer);
	}
	
	public void ViewPendingAccounts() {
		System.out.println("\n\n\n**********Pending Accounts**********\n");
		List<Account> accounts = presentationService.GetPendingAccounts();
		String substr = null, inputResult = null;
		System.out.println("Bank Accounts:");
		
		ListIterator<Account> accountListIterator = accounts.listIterator();
		if(accountListIterator.hasNext()) {
			while (accountListIterator.hasNext()) {
				Account account = accountListIterator.next();
				System.out.println("CustomerID: " + account.getCustomer_id() + ", AccountID: " + account.getAccount_id() + ", Balance: " + String.format("%.2f",account.getBalance()));
			}
			System.out.println("Enter +AccountID to approve account, -AccountID to reject account, or QUIT to go back.");
			while(inputResult == null) {
				inputResult = scanner.nextLine();
				substr = inputResult.substring(1);
				if(inputResult.charAt(0) == '+') {
					presentationService.ApproveAccount(substr);
				}else if (inputResult.charAt(0) == '-') {
					presentationService.DenyAccount(substr);
				}else if (inputResult.equals("QUIT")){
					DisplayEmployeePage();
					break;
				}
				inputResult = null;
			}
		}
	}
	
	public void DisplayCustomerAccounts() {
		ListIterator<Customer> customerListIterator;
		ListIterator<Account> accountListIterator;
		String customerID = null;
		List<Account> accounts = null;
		List<Customer> customers = presentationService.GetAllCustomers();
		System.out.println("\n\n\n**********Customers**********\n");
		
		customerListIterator = customers.listIterator();
		while (customerListIterator.hasNext()) {
			Customer customer = customerListIterator.next();
			System.out.println("CustomerID: " + customer.getCustomerId() + ", First Name: " + customer.getFirstName() + ", Last name: " + customer.getLastName() + ", Email: " + customer.getEmail() + ", Phone Number: " + customer.getPhoneNumber());
		}
		
		while(customerID == null) {
			System.out.println("Enter customer's ID to view the customer's accounts (or QUIT to return to employee portal): ");
			customerID = scanner.nextLine();
			if(customerID.equals("QUIT")) {
				DisplayEmployeePage();
				break;
			}else {
				accounts = presentationService.GetAccountsByCustomer(Integer.parseInt(customerID));
				accountListIterator = accounts.listIterator();
				while (accountListIterator.hasNext()) {
					Account account = accountListIterator.next();
					System.out.println("AccountID: " + account.getAccount_id() + ", CustomerID: " + account.getCustomer_id() + ", Balance: " +String.format("%.2f", account.getBalance()));
				}
			}
			customerID = null;
		}
		
	}
	
	public void DisplayEmployeePage() {
		String inputResult = null;
		System.out.println("\n\n\n**********Employee Portal**********\n");
		
		while(inputResult == null) {
			System.out.println("Enter (1) to view pending accounts");
			System.out.println("Enter (2) to view customer accounts");
			System.out.println("Enter (3) to exit employee portal");
			
			inputResult = scanner.nextLine();
			if(inputResult.equals("1")) {
				ViewPendingAccounts();
			}else if (inputResult.equals("2")) {
				DisplayCustomerAccounts();
			}else if (inputResult.equals("3")){
				System.out.println("Returning to login page..");
				DisplayInitialPage();
			}else {
				inputResult = null;
				System.out.println("Invalid input, please enter a valid command.\n");
			}
		}
	}
	
	
	
	
	public void DisplayTransferPage(Customer customer) {
		System.out.println("\n\n\n**********Transfer Funds**********\n");
		System.out.println("Bank Accounts:");
		List<Account> accounts = presentationService.GetAccountsByCustomer(customer.getCustomerId());
		String targetAccountID = null, sourceAccountID = null, transferAmount = null;
		
		ListIterator<Account> accountListIterator = accounts.listIterator();
		while (accountListIterator.hasNext()) {
			Account account = accountListIterator.next();
			if(account.isApproved())
				System.out.println(account.getAccount_id() + "     " + String.format("%.2f",account.getBalance()));
		}
		
		System.out.println("\nEnter the ID of the account you wish to transfer to, or enter QUIT to exit transfer page: ");
		targetAccountID = scanner.nextLine();
		if(targetAccountID.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		
		System.out.println("Enter the ID of the account you wish to transfer from, or enter QUIT to exit transfer page: ");
		sourceAccountID = scanner.nextLine();
		if(sourceAccountID.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		
		System.out.println("Enter the amount (USD) you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
		transferAmount = scanner.nextLine();
		if(transferAmount.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		
		while( ! (presentationService.IsIntParsable(targetAccountID)) ||  ! (presentationService.IsIntParsable(sourceAccountID)) ||  ! (presentationService.IsDoubleParsable(transferAmount)) || ! presentationService.TransferBetweenAccounts(Double.parseDouble(transferAmount), Integer.parseInt(targetAccountID), Integer.parseInt(sourceAccountID), customer)) {
			System.out.println("Error transfering money between accounts. Please try again.");
			System.out.println("Enter the ID of the account you wish to transfer to, or enter QUIT to exit transfer page: ");
			targetAccountID = scanner.nextLine();
			if(targetAccountID.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
			
			System.out.println("Enter the ID of the account you wish to transfer from, or enter QUIT to exit transfer page: ");
			sourceAccountID = scanner.nextLine();
			if(sourceAccountID.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
			
			System.out.println("Enter the amount (USD) you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
			transferAmount = scanner.nextLine();
			if(transferAmount.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
		}
		System.out.println("Withdraw of " + transferAmount + " successful, returning to account summary...");
		DisplayCustomerPage(customer);
	}
	
	
	
	
	
	
	public void DisplayWithdrawPage(Customer customer) {
		System.out.println("\n\n\n**********Withdraw**********\n");
		System.out.println("Bank Accounts:");
		List<Account> accounts = presentationService.GetAccountsByCustomer(customer.getCustomerId());
		String withdrawAccountID = null, withdrawAmount = null;
		
		ListIterator<Account> accountListIterator = accounts.listIterator();
		while (accountListIterator.hasNext()) {
			Account account = accountListIterator.next();
			if(account.isApproved())
				System.out.println(account.getAccount_id() + "     " + String.format("%.2f",account.getBalance()));
		}
		
		System.out.println("Enter the ID of the account you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
		withdrawAccountID = scanner.nextLine();
		if(withdrawAccountID.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		System.out.println("Enter the amount (USD) you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
		withdrawAmount = scanner.nextLine();
		if(withdrawAmount.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		
		while( ! (presentationService.IsIntParsable(withdrawAccountID)) || ! (presentationService.IsDoubleParsable(withdrawAmount)) || !(presentationService.WithdrawFromAccount(Integer.parseInt(withdrawAccountID), Double.parseDouble(withdrawAmount), customer))) {
			System.out.println("Error withdrawing money from account. Please try again.");
			System.out.println("Enter the ID of the account you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
			withdrawAccountID = scanner.nextLine();
			if(withdrawAccountID.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
			System.out.println("Enter the amount (USD) you wish to withdraw from, or enter QUIT to exit withdrawal page: ");
			withdrawAmount = scanner.nextLine();
			if(withdrawAmount.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
		}
		System.out.println("Withdraw of " + withdrawAmount + " successful, returning to account summary...");
		DisplayCustomerPage(customer);
	}
	
	public void DisplayDepositPage(Customer customer) {
		System.out.println("\n\n\n**********Deposit**********\n");
		System.out.println("Bank Accounts:");
		List<Account> accounts = presentationService.GetAccountsByCustomer(customer.getCustomerId());
		String depositAccountID = null, depositAmount = null;
		
		ListIterator<Account> accountListIterator = accounts.listIterator();
		while (accountListIterator.hasNext()) {
			Account account = accountListIterator.next();
			if(account.isApproved())
				System.out.println(account.getAccount_id() + "     " + String.format("%.2f",account.getBalance()));
		}
		
		System.out.println("\nEnter the ID of the account you wish to deposit into, or enter QUIT to exit deposit page: ");
		depositAccountID = scanner.nextLine();
		if(depositAccountID.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		System.out.println("Enter the amount (USD) you wish to deposit, or enter QUIT to exit deposit page: ");
		depositAmount = scanner.nextLine();
		if(depositAmount.equals("QUIT")) {
			DisplayCustomerPage(customer);
			return;
		}
		
		while(  ! (presentationService.IsIntParsable(depositAccountID)) || ! (presentationService.IsDoubleParsable(depositAmount)) || !(presentationService.DepositIntoAccount(Integer.parseInt(depositAccountID), Double.parseDouble(depositAmount), customer))) {
			System.out.println("Error depositing money into account. Please try again.");
			System.out.println("Enter the ID of the account you wish to deposit into, or enter QUIT to exit deposit page: ");
			depositAccountID = scanner.nextLine();
			if(depositAccountID.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
			System.out.println("Enter the amount (USD) you wish to deposit, or enter QUIT to exit deposit page: ");
			depositAmount = scanner.nextLine();
			if(depositAccountID.equals("QUIT")) {
				DisplayCustomerPage(customer);
				return;
			}
		}
		System.out.println("Deposit of " + depositAmount + " successful, returning to account summary...");
		DisplayCustomerPage(customer);
	}
	
	public void DisplayCustomerPage(Customer customer) {
		System.out.println("\n\n\n**********Customer Summary**********\n");
		System.out.println("Welcome, " + customer.getFirstName() + " " + customer.getLastName() + "!\n");
		System.out.println("Bank Accounts:");
		List<Account> accounts = presentationService.GetAccountsByCustomer(customer.getCustomerId());
		String inputResult = null;
		
		ListIterator<Account> accountListIterator = accounts.listIterator();
		while (accountListIterator.hasNext()) {
			Account account = accountListIterator.next();
			if(account.isApproved())
				System.out.println(account.getAccount_id() + "     " + String.format("%.2f",account.getBalance()));
		}
		
		while(inputResult == null) {
			System.out.println("\nEnter (1) sign up for a new bank account");
			System.out.println("Enter (2) to deposit money into a bank account");
			System.out.println("Enter (3) to withdraw money from a bank account");
			System.out.println("Enter (4) to move money from one account to another");
			System.out.println("Enter (5) to return to main menu");
			
			inputResult = scanner.nextLine();
			
			if(inputResult.equals("1")) {
				DisplayNewBankAccountSignup(customer);
			}else if (inputResult.equals("2")) {
				DisplayDepositPage(customer);
			}else if (inputResult.equals("3")) {
				DisplayWithdrawPage(customer);
			}else if (inputResult.equals("4")){
				DisplayTransferPage(customer);
			}else if (inputResult.equals("5")){
				System.out.println("Returning to main menu...");
				DisplayInitialPage();
			}else {
				inputResult = null;
				System.out.println("Invalid input, please enter a valid command...");
			}
		}
	}
	
	public void DisplayInitialPage() {
		String inputResult = null;
		System.out.println("\n\n\n**********Revature Bank Portal**********\n");
		System.out.println("Welcome to Revature Bank!\n");
		
		while(inputResult == null || inputResult.equals("4")) {
			System.out.println("Enter (1) to log in as a customer");
			System.out.println("Enter (2) to log in as an employee");
			System.out.println("Enter (3) to register as a new customer");
			System.out.println("Enter (4) to close banking application\n\n");
			
			inputResult = scanner.nextLine();
			if(inputResult.equals("1")) {
				DisplayLoginPage();
			}else if (inputResult.equals("2")) {
				DisplayEmployeeLoginPage();
			}else if (inputResult.equals("3")) {
				DisplayRegistrationPage();
			}else if (inputResult.equals("4")){
				scanner.close();
				System.out.println("Closing banking application...");
				return;
			}else {
				inputResult = null;
				System.out.println("Invalid input, please enter a valid command.\n");
			}
		}
	}
	
	
	public void DisplayRegistrationPage() {
		System.out.println("\n\n\n**********Customer Registration**********\n");
		System.out.println("Thank you for considering an account with us, please answer the following questions as honestly as possible:\n");
		String username = null, password = null, firstName = null, lastName = null, email = null, phoneNumber = null, finalDecision = null;
		
		System.out.print("Please enter a username (must contain at least 6 characters): ");
		username = scanner.nextLine();
		while( ! presentationService.VerifyUsername(username)) {
			System.out.print("Invalid entry for username, please try again:  ");
			username = scanner.nextLine();
		}
		
		System.out.print("Please enter a password (must contain at least 8 characters): ");
		password = scanner.nextLine();
		while( ! presentationService.VerifyPassword(password)) {
			System.out.print("Invalid entry for password, please try again:  ");
			password = scanner.nextLine();
		}
		
		System.out.print("Please enter your first name (must contain at least 2 characters): ");
		firstName = scanner.nextLine();
		while( ! presentationService.VerifyName(firstName)) {
			System.out.print("Invalid entry for first name, please try again:  ");
			firstName = scanner.nextLine();
		}
		
		System.out.print("Please enter your last name (must contain at least 2 characters): ");
		lastName = scanner.nextLine();
		while( ! presentationService.VerifyName(lastName)) {
			System.out.print("Invalid entry for last name, please try again:  ");
			lastName = scanner.nextLine();
		}
		
		System.out.print("Please enter your email (must contain at least 6 characters): ");
		email = scanner.nextLine();
		while( ! presentationService.VerifyEmail(email)) {
			System.out.print("Invalid entry for email, please try again:  ");
			email = scanner.nextLine();
		}
		
		System.out.print("Please enter your phone number (must contain at least 10 numbers): ");
		phoneNumber = scanner.nextLine();
		while( ! presentationService.VerifyPhoneNumber(phoneNumber)) {
			System.out.print("Invalid entry for phoneNumber, please try again:  ");
			phoneNumber = scanner.nextLine();
		}
		
		System.out.print("\n\nThank you for filling in all the fields. Do you wish to proceed? (Y/N)\n");
		finalDecision = scanner.nextLine();
		while( ! finalDecision.equals("Y") || finalDecision.equals("N")) {
			System.out.println("Invalid response. Please enter Y to procees with account creation or N to cancel: ");
			finalDecision = scanner.nextLine();
		}
		if(finalDecision.equals("Y")) {
			if(presentationService.InsertCustomer(username, password, firstName, lastName, phoneNumber, email)) {
				System.out.print("\n\nCustomer account created successfully.\n");
			}else {
				System.out.print("\n\nError creating account, please try again.\n");
			}
		}else {
			System.out.print("\n\nCustomer account was not created, returning to menu.\n");
		}
		DisplayInitialPage();
	}
}
