package com.revaturebank.presentation;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revaturebank.model.Customer;
import com.revaturebank.model.User;
import com.revaturebank.service.BankService;

public class BankTerminal {
	
	BankTerminal bankTerminal;
	BankService bankService;
	
	final static Logger loggy = Logger.getLogger(BankTerminal.class);
	
	public void welcomePage() {
		Scanner scan = new Scanner(System.in);
		boolean isExit = false;
		bankTerminal = new BankTerminal();
		System.out.println("---------Welcome to THe Bank of Revature-----------");
		
		while(!isExit) {
			System.out.println("Please Login or SignUp!");
			System.out.println("1) Login");
			System.out.println("2) SignUp");
			System.out.println("3) Exit");
			System.out.print("Please enter 1/2/3: ");
			
			String input = scan.nextLine();
			
			switch(input) {
			case "1":
				bankTerminal.logInMenu();
				break;
			case "2":
				bankTerminal.singUpMenu();
				break;
			case "3":
				isExit = true;
				break;
			default:
					System.out.println("Please enter the valid input!!!");
					System.out.println("\n--------------------------------------\n");
			}
		}
		System.out.println("Thank You!!!");
		
		
	}

	public void singUpMenu() {
		Scanner scan = new Scanner(System.in);
		bankService = new BankService();
	    System.out.print("\n--------------------------------------\n");
		System.out.print("Please enter your username: ");
		String userName = scan.next();
		System.out.print("Please enter your password: ");
		String password = scan.next();
		
		try {
			bankService.saveUser(userName, password);
			
			System.out.print("User successfully signed up");
			System.out.println("\n--------------------------------------\n");
			
			loggy.info("Signup Successful");
			loggy.setLevel(Level.INFO);
		} catch (Exception e) {
			loggy.error(e);
		}	
	}

	public void logInMenu() {
		Scanner scan = new Scanner(System.in);
		bankService = new BankService();
		bankTerminal = new BankTerminal();
		
		System.out.print("\n--------------------------------------\n");
		System.out.print("Please enter your username: ");
		String userName = scan.next();
		System.out.print("Please enter your password: ");
		String password = scan.next();
		
		User customer;
		User employee;
		try {
			customer = bankService.customerLogIn(userName, password);
			employee = bankService.employeeLogIn(userName, password);
			
			if(customer != null) {
				bankTerminal.customerMainMenu(customer);
			} else if (employee != null) {
				bankTerminal.employeeMainMenu(employee);
				
			} else {
				System.out.println("Login Failed!!!");
				System.out.println("Please try again or SignUp!!!");
				System.out.println("\n--------------------------------------\n");
			}
		} catch (Exception e) {
			loggy.error(e);
		}

		}

	private void employeeMainMenu(User user) {
		bankTerminal = new BankTerminal();
		Scanner scan = new Scanner(System.in);
		boolean isMenuExit = false;
		
		while(!isMenuExit) {
			System.out.println("\n-------------------Main Menu--------------");
			System.out.println("1) View Accounts By Customer Id");
			System.out.println("2) View Pending Accounts");
			System.out.println("3) Update Pending Accounts");
			System.out.println("4) Exit");
			System.out.print("Please Enter you option: ");
			String input = scan.next();
			
			switch(input) {
				case "1":
					bankTerminal.viewAccountsByCustomerId();
					break;
				case "2":
					bankTerminal.viewPendingAccounts();
					break;
				case "3":
					bankTerminal.updatePendingAccounts();
					break;
				case "4":
					isMenuExit = true;
					break;
				default:
						System.out.println("Please Enter Valid Option");
			}
		}
		System.out.println("\n--------------------------------------\n");	
	}

	private void viewPendingAccounts() {
		bankService = new BankService();
		Scanner scan = new Scanner(System.in);
		System.out.println("\n------------------Pending Accounts--------------------\n");
		List<Customer> customerList = bankService.getCustomersPendingAccounts();
		if(customerList.size() == 0) {
			System.out.println("No pending accounts");
		} else {
			for(Customer customer: customerList) {
				System.out.println(customer.toString());
			}
		}
	}

	private void updatePendingAccounts() {
		bankService = new BankService();
		Scanner scan = new Scanner(System.in);
		viewPendingAccounts();
		System.out.println("\n--------------------------------------\n");
		List<Customer> customerList = bankService.getCustomersPendingAccounts();
		if(customerList.size() > 0) {
			System.out.print("Please Enter Account Number for which you want to update status: ");
			Integer accountNumber = Integer.parseInt(scan.next());
			System.out.print("Please Select:\n 1) Approve: \n 2) Reject: ");
			Integer input = Integer.parseInt(scan.next());
			if(input == 1) {
				bankService.updatePendingAccounts("active", accountNumber);
			} else {
				bankService.updatePendingAccounts("inactive", accountNumber);
			}
			System.out.print("Status successfully updated");
		}
	}

	private void viewAccountsByCustomerId() {
		bankService = new BankService();
		Scanner scan = new Scanner(System.in);
		System.out.println("\n--------------------------------------\n");
		System.out.print("Please Enter Customer Id: ");
		Integer id = Integer.parseInt(scan.next());
		List<Customer> customerList = bankService.getCustomers(id);
		System.out.println("Customer Information for id: " + id);
		for(Customer customer: customerList) {
			System.out.println(customer.toString());
		}
		
	}

	private void customerMainMenu(User user) {
		bankTerminal = new BankTerminal();
		bankService = new BankService();
		Scanner scan = new Scanner(System.in);
		boolean isMenuExit = false;
		
		while(!isMenuExit) {
			System.out.println("\n-------------------Main Menu--------------");
			System.out.println("1) Apply for Checking Account");
			System.out.println("2) Apply for Saving Account");
			System.out.println("3) View Balance");
			System.out.println("4) Deposit");
			System.out.println("5) Withdraw");
			System.out.println("6) Transfer");
			System.out.println("7) Exit");
			System.out.print("Please Enter you option: ");
			String input = scan.next();
			
			switch(input) {
				case "1":
					bankTerminal.applyAccount("checking", user.getId());
					break;
				case "2":
					bankTerminal.applyAccount("saving", user.getId());
					break;
				case "3":
					bankTerminal.viewBalance(user.getId());
					break;
				case "4":
					bankTerminal.deposit(user.getId());		
					break;
				case "5":
					bankTerminal.withdraw(user.getId());
					break;
				case "6":
					bankTerminal.transfer(user.getId());
					break;
				case "7":
					isMenuExit = true;
					break;
				default:
						System.out.println("Pelase Enter Valid Option");
			}
		}
		System.out.println("\n--------------------------------------\n");
	}

	private void transfer(int id) {
		bankService = new BankService();
		List<Customer> customerList = bankService.getCustomers(id);
		if(customerList.size() != 0) {
			System.out.println("\n--------------------------------------\n");
			int count  = 1;
			for(Customer customer: customerList) {
				if(bankService.isAccountActive(customer)) {
					System.out.println(count + ") Account Name: " + customer.getAccountName() +
							", Account Type: " + customer.getAccountType() + ", Account Number: " +
							customer.getAccountNumber());
					System.out.print("Do you want to transfer from this " + customer.getAccountType() + " account(Y/N)? ");
					Scanner scan = new Scanner(System.in);
					String input = scan.next();
					if(input.equalsIgnoreCase("y")) {
						System.out.print("Please enter account number that you want to transer to: ");
						Integer transferAccountNumber = Integer.parseInt(scan.next());
						System.out.print("Please enter an amount you want to transfer: ");
						Double amount = Double.parseDouble(scan.next());
						if(amount <= 0) {
							System.out.print("Please enter a valid amount!!!");
						} else if(amount > customer.getBalance()){
							System.out.print("Insufficient amount!!!");
						}else {
							bankService.transfer(customer.getAccountNumber(), amount, customer.getBalance(), transferAccountNumber);
						}
						
					}
					count++;
				} else {
					System.out.println("Your "+ customer.getAccountType() + " account with account number " + customer.getAccountNumber()  + " is not active. Please wait for approval.");
				}
			}
		}
		
	}

	private void withdraw(int id) {
		bankService = new BankService();
		List<Customer> customerList = bankService.getCustomers(id);
		if(customerList.size() != 0) {
			System.out.println("\n--------------------------------------\n");
			int count  = 1;
			for(Customer customer: customerList) {
				if(bankService.isAccountActive(customer)) {
					System.out.println(count + ") Account Name: " + customer.getAccountName() +
							", Account Type: " + customer.getAccountType() + ", Account Number: " +
							customer.getAccountNumber());
					System.out.print("Do you want to withdraw from this " + customer.getAccountType() + " account(Y/N)? ");
					Scanner scan = new Scanner(System.in);
					String input = scan.next();
					if(input.equalsIgnoreCase("y")) {
						System.out.print("Please enter an amount you want to withdraw: ");
						Double amount = Double.parseDouble(scan.next());
						if(amount <= 0) {
							System.out.print("Please enter a valid amount!!!");
						} else if(amount > customer.getBalance()){
							System.out.print("Insufficient amount!!!");
						}else {
							bankService.withdraw(customer.getAccountNumber(), amount, customer.getBalance());
						}
						
					}
					count++;
				} else {
					System.out.println("Your "+ customer.getAccountType() + " account with account number " + customer.getAccountNumber()  + " is not active. Please wait for approval.");
				}
				
			}
		}
		
	}

	private void deposit(int id) {
		bankService = new BankService();
		List<Customer> customerList = bankService.getCustomers(id);
		if(customerList.size() != 0) {
			//System.out.println("\n--------------------------------------\n");
			int count  = 1;
			for(Customer customer: customerList) {
				if(bankService.isAccountActive(customer)) {
					System.out.println(count + ") Account Name: " + customer.getAccountName() +
							", Account Type: " + customer.getAccountType() + ", Account Number: " +
							customer.getAccountNumber());
					System.out.print("Do you want to deposit to this " + customer.getAccountType() + " account(Y/N)? ");
					Scanner scan = new Scanner(System.in);
					String input = scan.next();
					if(input.equalsIgnoreCase("y")) {
						System.out.print("Please enter an amount you want to deposit: ");
						Double amount = Double.parseDouble(scan.next());
						if(amount <= 0) {
							System.out.print("Please enter a valid amount!!!");
		
						} else {
							bankService.deposit(customer.getAccountNumber(), amount, customer.getBalance());
							loggy.setLevel(Level.INFO);
							loggy.info("Money Deposited." + amount);
						}	
					}
					count++;
				} else {
					System.out.println("Your "+ customer.getAccountType() + " account with account number " + customer.getAccountNumber()  + " is not active. Please wait for approval.");
				}	
			}	
		}
		
	}

	private void viewBalance(int id) {
		bankService = new BankService();
		List<Customer> customerList = bankService.getCustomers(id);
		 System.out.println("\n--------------------------------------\n");
		if(customerList.size() == 0) {
			System.out.println("You don't have any account. Please create an account.");
		}else {
			for(Customer customer: customerList) {
				if(bankService.isAccountActive(customer)) {
					System.out.println("Your balance in " + customer.getAccountType() + " account with account number " + customer.getAccountNumber() + " is: " + customer.getBalance());
				} else {
					System.out.println("Your "+ customer.getAccountType() + " account with account number " + customer.getAccountNumber()  + " is not active. Please wait for approval.");
				}
				
			}
		}
		
	}

	private void applyAccount(String accountType, Integer id) {
		bankService = new BankService();
		Scanner scan = new Scanner(System.in);
		System.out.println("\n--------------------------------------\n");
		System.out.print("Please Enter your first name: ");
		String firstName = scan.next();
		System.out.print("Please Enter your last name: ");
		String lastName = scan.next();
		System.out.print("Please Enter your email: ");
		String email = scan.next();
		System.out.print("Please Enter your phone number: ");
		String phoneNumber = scan.next();
		System.out.print("Please Enter acount nick name: ");
		String accountName = scan.next();
		System.out.print("Please enter your initial deposit(if not enter 0)");
		Double balance = Double.parseDouble(scan.next());
		
		bankService.applyAccount(accountType, firstName, lastName, email, phoneNumber, accountName, balance, id);
	}
}
