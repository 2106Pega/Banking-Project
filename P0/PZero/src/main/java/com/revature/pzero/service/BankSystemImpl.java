package com.revature.pzero.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.pzero.MainDriver;
import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;

public class BankSystemImpl implements BankSystem{

	private Bank bank;
	final static Logger loggy = Logger.getLogger(BankSystem.class);
	
	public BankSystemImpl(Bank bank) {
		this.bank = bank;
		
//		loggy.setLevel(Level.DEBUG);
//		loggy.info("User viewed the Display menu");
//		loggy.setLevel(Level.WARN);
//		loggy.debug("");
		
		
	}

	@Override
	public User login(String username, String password) {
		User user = null;
		
		if(username.isBlank() || username == null) {
			System.out.println("Username is invalid. -> login(BankSystemImpl)");
			return null;
		}else if(password.isBlank() || password == null) {
			System.out.println("Password is invalid. -> login(BankSystemImpl)");
			return null;
		}
		
		user = bank.login(username, password);
//		loggy.info("USER LOGIN: " + user.toString());	//must check to see if null
		
		return user;
	}

	@Override
	public boolean createNewAccount(int userId, Double balance, String nickName, boolean approved) {
		boolean success = false;
		if(balance != 0.00 || balance == null) {
			System.out.println("Balance is invalid." + balance + "  -> createNewAccount(BankSystemImpl)"); //inputting a value of 0.001?
			return false;
		}	
		
		Account account = new Account(-1, balance, nickName, approved);
		success = bank.newAccount(userId, account);
//		loggy.info("");
		
		return success;
	}

	@Override
	public boolean createNewUser(String fName, String lName, String userType, String username, String password) {
		boolean success = false;
		
		User user = new User(-1, fName, lName, userType, username, password, false);

		if(fName == null || fName.isBlank()) {
			System.out.println("FirstName invalid. -> createNewUser (BankSystemImpl)");
			return false;
		}else if(lName == null || lName.isBlank()) {
			System.out.println("LastName invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(userType.isBlank() || userType == null) {
			System.out.println("Usertype invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(username == null || username.isBlank()) {
			System.out.println("Username invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(password == null || password.isBlank()) {
			System.out.println("Password invalid -> createNewUser (BankSystemImpl)");
			return false;
		}
		
		//check to see if username is already being used
		List<String> listOfAllUserNames = bank.viewUsernames();
		if(listOfAllUserNames != null || listOfAllUserNames.isEmpty() != true) {
			if(listOfAllUserNames.contains(username)) {
				System.out.println("Username is already taken. Please choose another. -> createNewUser (BankSystemImpl)");
				return false;
			}
		}
		
		success = bank.newUser(user, userType);
//		loggy.info("");
		
		return success;
	}

	@Override
	public boolean authenticate(String username, String password) {
		if(username == null || username.isBlank()) {
			System.out.println("Username invalid -> authenticate (BankSystemImpl)");
			return false;
		}else if(password == null || password.isBlank()) {
			System.out.println("Password invalid -> authenticate (BankSystemImpl)");
			return false;
		}
		
		boolean success = false;
		
		User user = bank.login(username, password);
		if(user != null) {
			success = true;
//			loggy.debug("");
		}
		
		return success;
	}

//	@Override
//	public boolean authenticateLoginUsername(String usernameInput) {
//		
//		return false;
//	}
//
//	@Override
//	public boolean authenticateLoginPassword(String passwordInput) {
//		
//		return false;
//	}
	
//	@Override ///////////////////////
	public Account verifyAccount(int accountId) {
		if(accountId == -1) {
			System.out.println("Account id is invalid. -> verifyAccount (BankSystemImpl)");
			return null;
		}
		
		Account account = null;
		account = bank.viewAccountByAccountId(accountId);
//		loggy.debug("");
		
		return account;
	}

	@Override
	public List<Account> getCustomerAccounts(int userId) {
		List<Account> listOfCustomerAccounts = null;
		if(userId == -1) {
			System.out.println("Not a valid ID. -> getCustomerAccounts (BankSystemImpl)");
		}
		
		listOfCustomerAccounts = bank.viewAccountByUserID(userId);
//		loggy.info("");
		
		return listOfCustomerAccounts;
	}
	
	public boolean canWithdraw(Account account, double withdrawAmount) {
		if(account == null || account.isApproved() == false) {
//			System.out.println("Account is invalid. -> canWithdraw (BankSystemImpl)");
			if(account == null)
				System.out.println("Account = null.");
			if(account.isApproved() == false) {
				if(account.getBalance() != 0.0) {
					System.out.println("Account is frozen. No transactions can be done at this time.\n"
							+ "Please call XXX-XXX-XXXX for support.");
				}else {
					System.out.println("Account has yet to be approved. No transactions can be done at this time.\n"
						+ "Please allow for a full 24hrs for your request to be reviewed and approved. Thank you.");
				}
			}
			return false;
		}
		if(withdrawAmount > account.getBalance() || withdrawAmount <= 0.0) {
//			System.out.println("Withdraw amount is invalid. -> canWithdraw (BankSystemImpl)");
			if(withdrawAmount > account.getBalance())
				System.out.println("Invalid balance. Withdraw amount must be lower then account balance.");
			if(withdrawAmount <= 0.0) {
				System.out.println("Withdraw amount in ivalid. Please enter a valid amount.");
			}
			return false;
		}
		
		return true;
	}


	@Override
	public boolean withdraw(Account account, double withdrawAmount) {
		account.setBalance(account.getBalance() - withdrawAmount);
		boolean success = bank.withdraw(account);
//		loggy.info("");
		return success;
	}
	
	public boolean canDeposit(Account account, double depositAmount) {
		if(account == null || account.isApproved() == false) {
//			System.out.println("Account is invalid. -> canDeposit (BankSystemImpl)");
			if(account == null)
				System.out.println("Account is NULL -> canDeposit (BankSystemImpl)");
			else if(account.isApproved() == false)
				System.out.println("Account not approved -> canDeposit (BankSystemImpl)");
			
			return false;
		}
		if(depositAmount <= 0.0) {
//			System.out.println("Deposit amount is invalid. -> canDeposit (BankSystemImpl)");
//			if(depositAmount  account.getBalance())
//				System.out.println("-> canDeposit (BankSystemImpl)");
			if(depositAmount <= 0.0)
				System.out.println("Deposit amount is invalid -> canDeposit (BankSystemImpl)");
			return false;
		}
		
		if(depositAmount + account.getBalance() > 500000.00) {
			System.out.println("An account max if $500,000.00. Please create a new account if and splint the amount between two accounts.");
		}
		
		return true;
	}

	@Override
	public boolean deposit(Account account, double depositAmount) {
		account.setBalance(account.getBalance() + depositAmount);
		boolean success = bank.deposit(account);
//		loggy.info("");
		return success;
	}
	
	//check to make sure not a negative amount and etc
	public boolean canTransfer(Account transferingFrom, double transferAmount) {
		if(transferingFrom == null || transferingFrom.isApproved() == false) {
			System.out.println("Account is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		if(transferAmount <= 0.0) {
			System.out.println("Transfer amount is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean transfer(Account originAccount, Account transferToAccount, double transferAmount) {
		if(originAccount.getId() == transferToAccount.getId()) {
			System.out.println("Transfering to the same account. Ending.");
			return false;
		}
		
		if(transferToAccount == null) {
			System.out.println("Desired account to transfer to is invalid. -> transfer (BankSystemImpl)");
			return false;
		}
		
		if(transferToAccount.isApproved() == false) {
			System.out.println("Account to transfer to is locked. Transaction voided.");
			return false;
		}
		
		if(transferAmount + transferToAccount.getBalance() > 500000.00) {
			System.out.println("An account max if $500,000.00. Please create a new account if and splint the amount between two accounts.");
		}
		
		originAccount.setBalance(originAccount.getBalance() - transferAmount);
		transferToAccount.setBalance(transferToAccount.getBalance() + transferAmount);
		
		boolean success = bank.transfer(originAccount, transferToAccount, transferAmount);
//		loggy.info("");
		return success;
	}
	
	@Override
	public User getUserById(int userId) {
		User user = null;
		if(userId == -1) {
			System.out.println("Userid invalid. -> getUserById (BankSystemImpl)");
			return null;
		}
			
		user = bank.viewUserById(userId);
//		loggy.info("");
		
		return user;
	}

	@Override
	public Account getAccountById(int accountId) {
		if(accountId == -1) {
			System.out.println("Account id is invalid. -> getAccountById (BankSystemImpl)");
			return null;
		}
		
		Account account = bank.viewAccountByAccountId(accountId);
//		loggy.info("");
		
		return account;
	}

	@Override
	public User getUserViaAccountNumber(int accountId) {
		if(accountId == -1) {
			System.out.println("Account id is invalid. -> getUserViaAccountNumber (BankSystemImpl)");
			return null;
		}
		
		User user = bank.viewUserFromAccountId(accountId);
//		loggy.info("");
		
		return user;
	}

	@Override
	public List<Account> accountsToBeApproved(int userId){
		List<Account> unapprovedAccounts = null;
		
		User user = bank.viewUserById(userId);
		if(user == null || user.getUserType().equals("Customer")) {
			System.out.println("User doesn't exist. Can not approve account. -> accountsToBeApproved (BankSystemImpl)");
			return null;
		}
		
		unapprovedAccounts = bank.viewUnapprovedAccounts();
		
		return unapprovedAccounts;
	}

	@Override
	public boolean approveAccount(Account account) {
		account.setApproved(!account.isApproved());	
//		loggy.info("");
		
		return bank.updateAccountApproval(account);
	}
	
	@Override
	public List<User> getAllUsers(){
		return bank.viewAllUsers();
	}

	public boolean approveUser(User user) {
		
		return false;
	}
	
	public boolean updateUserPassword(User user) {
//		loggy.info("");
		return bank.updateUserPassword(user);
	}

	@Override
	public boolean closeAccount(Account a) {
//		loggy.info("");
		return bank.deleteAccount(a);
	}

	@Override
	public List<Account> getAllAccounts() {
//		loggy.info("");
		return bank.viewAllAccounts();
	}
	
	
}
