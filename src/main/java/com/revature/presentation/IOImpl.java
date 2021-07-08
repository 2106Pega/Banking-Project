package com.revature.presentation;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.application.HelperFunctions;
import com.revature.models.BankAccount;
import com.revature.models.Customer;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.util.Money;

public class IOImpl implements IO {
	Logger log;
	Scanner input;
	public IOImpl(Logger log, Scanner input){
		this.log= log;
		this.input= input;
	}
	@Override
	public LoginData loginForm() {
		LoginData output= new LoginData();
		boolean isIncomplete= true;
		while(true) {
			displayMessage("LOGIN or REGISTER");
			String in= input.nextLine();
			if(in.equalsIgnoreCase("EXIT")) {
				output.isExiting= true;
				return output;
			}else if(in.equalsIgnoreCase("LOGIN") || in.equalsIgnoreCase("L")) {
				output.isRegistering= false;
			}else if(in.equalsIgnoreCase("REGISTER") || in.equalsIgnoreCase("REG")
				|| in.equalsIgnoreCase("R")	) {
				output.isRegistering= true;
			}else {
				displayMessage("That's not a valid input");
				continue;
			}
			while(true) {
				displayMessage("Input your username");
				in= input.nextLine();
				if(in.contains(":")) {
					displayMessage("Usernames cannot have \":\" in their name");
					continue;
				}if(in.equalsIgnoreCase("EXIT")) {
					output.isExiting= true;
					return output;
				}else if(in.equalsIgnoreCase("Cancel")){
					break;
				}
				output.username= in;
				displayMessage("Input your password");
				output.password= input.nextLine();
				return output;
			}
		}
	}

	@Override
	public UserMenuResult userMenu(User u) {
		boolean isEmployee= u.getEmployeeId() != 0;
		displayMessage("This is the User Menu from here you can:");
		displayMessage("APPLY for a new bank account, view your BALANCE, \r\n"
				+ "perform a TRANSACTION, view your transaction HISTORY, \r\n"
				+ "update your customer PROFILE, or LOG OUT");
		if(isEmployee){
			displayMessage("As an employee you may also login to your connected EMPLOYEE account");
		}
		while(true) {
			String in= input.nextLine();
			if(in.equalsIgnoreCase("Cancel") || in.equalsIgnoreCase("LOG OUT") 
					|| in.equalsIgnoreCase("L")){
				return UserMenuResult.LOG_OUT;
			}else if(in.equalsIgnoreCase("EXIT")) {
				return UserMenuResult.QUIT_PROGRAM;
			}else if(in.equalsIgnoreCase("APPLY") || in.equalsIgnoreCase("A")){
				return UserMenuResult.APPLY_FOR_BANK_ACCOUNT;
			}else if(in.equalsIgnoreCase("BALANCE") || in.equalsIgnoreCase("B")){
				return UserMenuResult.VIEW_BALANCE;
			}else if(in.equalsIgnoreCase("TRANSACTION") || in.equalsIgnoreCase("T") 
					|| in.equalsIgnoreCase("WITHDRAW") || in.equalsIgnoreCase("W")
					|| in.equalsIgnoreCase("DEPOSIT") || in.equalsIgnoreCase("D")
					|| in.equalsIgnoreCase("TRANSFER")){
				return UserMenuResult.PERFORM_TRANSACTION;
			}else if(in.equalsIgnoreCase("Profile") || in.equalsIgnoreCase("P")){
				return UserMenuResult.EDIT_CUSTOMER_DATA;
			}else if(in.equalsIgnoreCase("HISTORY") || in.equalsIgnoreCase("H")) {
				return UserMenuResult.VIEW_TRANSACTION_HISTORY;
			}else if(isEmployee && (in.equalsIgnoreCase("EMPLOYEE") 
					|| in.equalsIgnoreCase("E"))) {
				return UserMenuResult.LOGIN_AS_EMPLOYEE;
			}else {
				displayMessage("That's not a valid input");
			}
		}
	}

	@Override
	public AccountApplicationData applyForBankAccountForm() {
		AccountApplicationData aad= new AccountApplicationData();
		displayMessage("Input the new account's name");
		String in;
		aad.accountName= null;
		while(aad.accountName == null) {
			in= input.nextLine();
			if(in.contains(":")) {
				displayMessage("Accounts cannot have \":\" in their name");
			}else if(in.equalsIgnoreCase("Cancel")){
				aad.canceled= true;
				return aad;
			}else if(in.equalsIgnoreCase("EXIT")) {
				aad.exitProgram= true;
				return aad;
			}else if(in.length() > 0){
				aad.accountName= in;
			}
		}
		displayMessage("Input the new account's type");
		displayMessage("CHEKCING or SAVING");
		aad.accountType= 0;
		while(aad.accountType == 0) {
			in= input.nextLine();
			if(in.equalsIgnoreCase("Cancel")){
				aad.canceled= true;
				return aad;
			}else if(in.equalsIgnoreCase("EXIT")) {
				aad.exitProgram= true;
				return aad;
			}else if(in.equalsIgnoreCase("CHECKING") || in.equalsIgnoreCase("C")){
				aad.accountType= 2;
			}else if(in.equalsIgnoreCase("Saving") || in.equalsIgnoreCase("S")) {
				aad.accountType= 1;
			}
		}
		displayMessage("How much do you want to put down for an initial deposit?");
		aad.initialDeposit= Money.getMoneyFromString(input.nextLine());
		return aad;
	}

	@Override
	public TransactionData transactionMenu(User u, HelperFunctions hf) {
		TransactionData td= new TransactionData();
		List<BankAccount> bas= hf.getBankAccountsForUser(u);
		if(bas.size() == 0) {
			displayError("You need an approved bank account before you can do transactions");
			td.canceled= true;
			return td;
		}
		displayMessage("Starting a transaction:");
		displayMessage("DEPOSIT, WITHDRAW, TRANSFER, or CANCEL");
		int type= -1;
		while(type == -1) {
			String in= input.nextLine();
			if(in.equalsIgnoreCase("DEPOSIT") || in.equalsIgnoreCase("D")) {
				type= 2;
			}else if(in.equalsIgnoreCase("WITHDRAW") || in.equalsIgnoreCase("W")) {
				type= 1;
			}else if(in.equalsIgnoreCase("TRANSFER") || in.equalsIgnoreCase("T")) {
				type= 0;
			}else if(in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("C")) {
				td.canceled= true;
				return td;
			}else if(in.equalsIgnoreCase("EXIT")) {
				td.exitProgram= true;
				return td;
			}
		}
		String[] strings= {"transfer from","withdraw from","deposit to"};
		
		BankAccount usersBA= this.chooseFromList(bas, 
				"Choose which bank account you'd like to "+strings[type]); 
		td.bankAccountFrom= null;
		td.bankAccountTo= null;
		
		switch(type) {
		case 0:
			
			displayMessage("Choose a bank account to transfer to");
			displayMessage("Username:Account or CANCEL");
			while(td.bankAccountTo == null) {
				String in= input.nextLine();
				if(in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("C")) {
					td.canceled= true;
					return td;
				}else if(in.equalsIgnoreCase("EXIT")) {
					td.exitProgram= true;
					return td;
				}
				td.bankAccountTo= hf.getBankAccountWithName(in);
			}
		case 1:
			td.bankAccountFrom= usersBA;
			break;
		case 2:
			td.bankAccountTo= usersBA;
		}
		String[] strings2= {"transfer","withdraw","deposit"};
		displayMessage("How much would you like to "+strings2[type]+"?");
		td.ammount= 0;
		while(td.ammount <= 0) {
			String in= input.nextLine();
			if(in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("C")) {
				td.canceled= true;
				return td;
			}else if(in.equalsIgnoreCase("EXIT")) {
				td.exitProgram= true;
				return td;
			}else {
				td.ammount= Money.getMoneyFromString(in);
				if(td.ammount <= 0) {
					displayMessage("That's not a valid ammount");
				}
			}
		}
		return td;
	}
	

	@Override
	public void displayAccountBalances(List<BankAccount> accounts) {
		// TODO Auto-generated method stub
		for(BankAccount ba : accounts) {
			System.out.println(ba + "\t - \t" + Money.getStringFromMoney(ba.getAccountBalance()));
		}
	}

	@Override
	public void displayTransactionsAsClient(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		displayTransactionsList(transactions);
	}

	@Override
	public ApplicationAprovalResult employeeApproveAplicationMenu(BankAccount ba) {
		displayMessage(ba + " is in need of approval");
		displayMessage("APPROVE, REJECT, SKIP, or CANCEL");
		while(true) {
			String in= input.nextLine();
			if(in.equalsIgnoreCase("approve") || in.equalsIgnoreCase("A")) {
				return ApplicationAprovalResult.APPROVED;
			}else if(in.equalsIgnoreCase("Reject") || in.equalsIgnoreCase("R")) {
				return ApplicationAprovalResult.REJECTED;
			}else if(in.equalsIgnoreCase("Skip") || in.equalsIgnoreCase("S")) {
				return ApplicationAprovalResult.VIEW_LATER;
			}else if(in.equalsIgnoreCase("Cancel") || in.equalsIgnoreCase("C")) {
				return ApplicationAprovalResult.CLOSE_MENU;
			}else if(in.equalsIgnoreCase("EXIT")) {
				return ApplicationAprovalResult.EXIT_PROGRAM;
			}else {
				displayMessage("That is not a valid choice.");
			}
		}
	}

	@Override
	public EmployeeMenuResult employeeMenu() {
		displayMessage("You are now logged in as an employee");
		displayMessage("view APPLICATIONS, view TRANSACTIONS, or RETURN to customer menu");
		while(true) {
			String in= input.nextLine();
			if(in.equalsIgnoreCase("applications") || 
					in.equalsIgnoreCase("A") || in.equalsIgnoreCase("APPS")) {
				return EmployeeMenuResult.VIEW_ACCOUNTS_TO_APPROVE;
			}else if(in.equalsIgnoreCase("Transactions") 
					|| in.equalsIgnoreCase("T") || in.equalsIgnoreCase("TRANS")) {
				return EmployeeMenuResult.VIEW_TRANSACTION_LOG;
			}else if(in.equalsIgnoreCase("RETURN") || in.equalsIgnoreCase("R")
					||in.equalsIgnoreCase("Cancel") || in.equalsIgnoreCase("C")) {
				return EmployeeMenuResult.RETURN_TO_CUSTOMER;
			}else if(in.equalsIgnoreCase("EXIT")) {
				return EmployeeMenuResult.EXIT_PROGRAM;
			}else {
				displayMessage("That is not a valid choice.");
			}
		}
	}

	@Override
	public void displayTransactionsList(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		for(Transaction t : transactions) {
			System.out.println(t);
		}
	}

	@Override
	public void displayError(String message) {
		// TODO Auto-generated method stub
		log.error(message);
		System.err.println(message);
	}

	@Override
	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		
	}


	@Override
	public CustomerInfoResult UpdateCustomerInfoMenu(Customer c) {
		
		CustomerInfoResult cir= new CustomerInfoResult();
		cir.isNew = (c == null);
		if(c != null) {
			this.displayMessage("Your name is currently set to " + c.getCustomerName());
		}else {
			c= new Customer(0, "");
		}
		this.displayMessage("Please input your value for your new name:");
		String in= input.nextLine();
		if(in.equalsIgnoreCase("Cancel")) {
			cir.isCanceled= true;
		}else if(in.equalsIgnoreCase("Exit")) {
			cir.isExiting= true;
		}else {
			cir.customerWithUpdates= c;
			c.setCustomerName(in);
		}
		return cir;
	}

	@Override
	public void giveMoney(int transferAmmount) {
		log.info("gave user "+ Money.getStringFromMoney(transferAmmount));
	}
	
	@Override
	public <T> T chooseFromList(List<T> list, String message) {
		if(list.size() == 0) {
			return null;
		}else if(list.size() == 1) {
			return list.get(0);
		}
		System.out.println(message);
		for(int i= 0; i<list.size(); i++) {
			T entry= list.get(i);
			System.out.println((i + 1)+")\t" + entry);
		}
		String s= input.nextLine();
		try {
		int x= Integer.parseInt(s);
		if((x > 0) && (x <= list.size())) {
			return list.get(x - 1);
		}else {
			return null;
		}
		}catch(NumberFormatException nfe){
			return null;
		}
	}
	@Override
	public void displayAccountsBegin() {
		displayMessage("Searching for Account applications in need of approval");
		
	}
	@Override
	public void displayAccountsEnd() {
		displayMessage("That's all of the applications");
		
	}
	
	
}
