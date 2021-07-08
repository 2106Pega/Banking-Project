package com.revature.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.database.DAOs;
import com.revature.models.BankAccount;
import com.revature.models.Customer;
import com.revature.models.GenericModel;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.presentation.AccountApplicationData;
import com.revature.presentation.ApplicationAprovalResult;
import com.revature.presentation.CustomerInfoResult;
import com.revature.presentation.EmployeeMenuResult;
import com.revature.presentation.IO;
import com.revature.presentation.LoginData;
import com.revature.presentation.TransactionData;
import com.revature.presentation.UserMenuResult;

public class Application implements HelperFunctions {

	User loggedInUser;
	Logger log;
	IO io;
	DAOs daos;
	public Application(IO io, DAOs daos, Logger gol) {
		this.io= io;
		this.log= gol;
		this.daos= daos;
	}
	@Override
	public BankAccount getBankAccountWithName(String name) {
		String[] spl= name.split(":");
		User owner= loggedInUser;
		if(spl.length > 2) {
			io.displayError("This is not a valid bank account");
			return null;
		}
		if(spl.length == 2) {
			owner= daos.users.getByUsername(spl[0]);
			if(owner == null) {
				List<Customer> potentialCustomers= daos.customers.getByName(spl[0]);
				List<User> potUsers= new ArrayList<>();
				for(Customer c : potentialCustomers) {
					potUsers.add(daos.users.getByCustomer(c));
				}
				owner= io.chooseFromList(potUsers, "There are many users with that name:");
			}
			if(owner == null) {
				io.displayError("This is not a valid bank account");
				return null;
			}
			name= spl[1];
			BankAccount ba= daos.bankAccounts.getByName(name, owner);
			if(ba == null) {
				io.displayError("This is not a valid bank account");
			}
			return ba;
		}else {
			BankAccount ba= daos.bankAccounts.getByName(name, owner);
			if(ba != null) {
				return ba;
			}
			owner= daos.users.getByUsername(name);
			if(owner == null) {
				List<Customer> potentialCustomers= daos.customers.getByName(spl[0]);
				List<User> potUsers= new ArrayList<>();
				for(Customer c : potentialCustomers) {
					potUsers.add(daos.users.getByCustomer(c));
				}
				owner= io.chooseFromList(potUsers, "There are many users with that name:");
			}
			if(owner == null) {
				io.displayError("This is not a valid bank account");
				return null;
			}
			ba= daos.bankAccounts.getPrimaryFromUser(owner);
			if(ba == null) {
				io.displayError("This is not a valid bank account");
			}
			return ba;
		}
	}
	@Override
	public void run() {
		LoginData ld;
		while(true){
			ld= this.io.loginForm();
			if((ld == null) || ld.isExiting) {
				return;
			}
			if(ld.isRegistering) {
				loggedInUser= tryToRegister(ld);
				if(loggedInUser == null) {
					io.displayError("That Username has already been claimed\n"
							+ "Please try again.");
				}
				
			}else {
				log.info("Tried to Log into account:" + ld.username);
				loggedInUser= tryToLogIn(ld);
				if(loggedInUser == null) {
					io.displayError("The Username/password was not recognized.\n"
							+ "Please try again.");

				}
			}
			if(loggedInUser != null) {
				if(!handleUserLoop()) {
					return;
				}
				loggedInUser= null;
			}
		}
		
	}
	
	public User tryToLogIn(LoginData ld) {
		User out= daos.users.getByUsername(ld.username, ld.password);
		if(out == null) {
			log.warn("Log in Failed");
		}
		return out;
	}
	public User tryToRegister(LoginData ld) {
		User user= new User(ld);
		log.info("Tried to Register account:" + user);
		if(daos.users.add(user)) { //this should attempt a login immediately after
			return user;
		}else {
			log.warn("Registration Failed");
			return null; //if registration fails
		}
		
	}
	//returns true if logout, returns false if exit;
	public boolean handleUserLoop() {
		while(true) {
			UserMenuResult umr= io.userMenu(loggedInUser);
			switch(umr) {
			case APPLY_FOR_BANK_ACCOUNT:
				if(!handleApplyForBankAccount()) {
					return false;
				}
				break;
			case VIEW_BALANCE:
				io.displayAccountBalances(daos.bankAccounts.getFromUser(loggedInUser));
				break;
			case PERFORM_TRANSACTION:
				if(!handlePerformTransaction()) {
					return false;
				}
				break;
			case LOGIN_AS_EMPLOYEE:
				if(!handleEmployeeLoop()) {
					return false;
				}
				break;
			case VIEW_TRANSACTION_HISTORY:
				io.displayTransactionsAsClient(daos.transactions.getFromUser(loggedInUser));
				break;
			case EDIT_CUSTOMER_DATA:
				if(!handleUpdateCustomerData()) {
					return false;
				}
				break;
			case LOG_OUT:
				return true;
			case QUIT_PROGRAM:
				return false;
			}
		}
	}
	private boolean handleEmployeeLoop() {
		while(true) {
			EmployeeMenuResult emr= io.employeeMenu();
			switch(emr) {
			case RETURN_TO_CUSTOMER:
				return true;
			case VIEW_ACCOUNTS_TO_APPROVE:
				if(!handleApproveAccounts()) {
					return false;
				}
				break;
			case VIEW_TRANSACTION_LOG:
				io.displayTransactionsList(daos.transactions.getAll());
				break;
			case EXIT_PROGRAM:
				return false;
			}
		}
	}
	
	private boolean handleApproveAccounts() {
		io.displayAccountsBegin();
		List<BankAccount> accounts = daos.bankAccounts.getAccountsNeedingApproval();
		for(BankAccount ba : accounts) {
			ApplicationAprovalResult aar= io.employeeApproveAplicationMenu(ba);
			switch(aar) {
			case APPROVED:

				log.info(ba+" is being Approved.");
				ba.setActivated();
				if(daos.bankAccounts.update(ba)) {
					if(ba.getAccountBalance() != 0) {
						daos.transactions.add(new Transaction(0, ba.getPrimaryKey(), ba.getAccountBalance()));
						
					}
					User owner= ba.getOwner();
					if(owner.getMainAccountId() == 0) {
						owner.setMainAccountId(ba.getPrimaryKey());
						daos.users.update(owner);
					}
					io.displayMessage(ba + " has been approved!");
				}else {
					log.warn("account approval failed");
					io.displayError("An unknown error has occured");
				}
				break;
			case REJECTED:

				log.info(ba+" is being rejected.");
				if(daos.bankAccounts.delete(ba)) {
					io.displayMessage(ba + " has been rejected.");
				}else {
					io.displayError("An unknown error has occured");
					log.error("rejection failed");
				}
				break;
			case VIEW_LATER:
				io.displayMessage(ba + " has been skipped for now.");
				break;
			case CLOSE_MENU:
				return true;
			case EXIT_PROGRAM:
				return false;
			}
		}
		io.displayAccountsEnd();
		return true;
	}
	
	private boolean handleUpdateCustomerData() {
		Customer c= daos.customers.getById(loggedInUser.getCustomerId());
		CustomerInfoResult cir= io.UpdateCustomerInfoMenu(c);
		if(cir == null || cir.isCanceled) {
			return true;
		}else if(cir.isExiting) {
			return false;
		}else {
			if(c == null) {
				daos.customers.add(cir.customerWithUpdates);
				//c= daos.customers.getByName(cir.customerWithUpdates.getCustomerName()).get(0);
				loggedInUser.setCustomerData(cir.customerWithUpdates);
				daos.users.update(loggedInUser);
			}else {
				daos.customers.update(c);
			}
		}
		return true;
	}
	private boolean handlePerformTransaction() {
		while(true) {
			TransactionData td= io.transactionMenu(loggedInUser, this);
			if(td == null || td.canceled) {
				return true;
			}else if(td.exitProgram) {
				return false;
			}else if(td.ammount < 0){
				io.displayError("You cannot transfer a negative ammount");
			}else if(td.ammount == 0) {
				io.displayError("You cannot transfer a zero ammount");
			}else if(GenericModel.areEqual(td.bankAccountFrom, td.bankAccountTo)) {
				io.displayError("You cannot transfer from an account to itself");
			}else {
				
				Transaction t= daos.transactions.handleTransaction(
						td.bankAccountFrom, td.bankAccountTo, td.ammount); 
				if(t != null) {
					io.displayMessage("The money was successfully transfered "
							+ t);
					if(t.getAccountToId() == 0) {
						io.giveMoney(t.getTransferAmmount());
					}
				}else {
					io.displayError("An unknown Error occured");
					
				}
				return true;
				
			}
		}
	}
	public boolean handleApplyForBankAccount() {
		while(true) {
			AccountApplicationData aad= io.applyForBankAccountForm();
			if(aad == null || aad.canceled) {
				return true;
			}else if(aad.exitProgram) {
				return false;
			}else if(aad.initialDeposit < 0){
				io.displayError("Your initial deposit may not be negative");
			}else {
				BankAccount ba= new BankAccount(loggedInUser,aad);
				log.info("attempted to apply for account "+ ba);
				if(daos.bankAccounts.add(ba)) {
					io.displayMessage(ba + " is awaiting approval.");
				}else {
					io.displayError("An unknown error has occured");
					log.warn("Apply for account failed");
					
				}
				return true;
			}
		}
	}
	@Override
	public List<BankAccount> getBankAccountsForUser(User u) {
		return daos.bankAccounts.getFromUser(u);
	}
}
