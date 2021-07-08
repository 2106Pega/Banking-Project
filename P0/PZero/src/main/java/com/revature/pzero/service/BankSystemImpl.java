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
		
		loggy.setLevel(Level.WARN);
		
		
	}

	@Override
	public User login(String username, String password) {
		User user = null;
		
		if(username == null || username.isBlank()) {
			loggy.warn("Username is invalid. -> login(BankSystemImpl)");
			return null;
		}else if(password == null || password.isBlank()) {
			loggy.warn("Password is invalid. -> login(BankSystemImpl)");
			return null;
		}
		
		user = bank.login(username, password);
		if(user != null)
			loggy.info("USER LOGIN: " + user.toString());
		
		return user;
	}

	@Override
	public boolean createNewAccount(int userId, Double balance, String nickName, boolean approved) {
		boolean success = false;
		if(balance == null || balance != 0.00) {
			loggy.warn("Balance is invalid." + balance + "  -> createNewAccount(BankSystemImpl)"); //inputting a value of 0.001?
			return false;
		}	
		
		Account account = new Account(-1, balance, nickName, approved);
		success = bank.newAccount(userId, account);
		loggy.info("ACCOUNT CREATED LOGIN: " + userId);
		
		return success;
	}

	@Override
	public boolean createNewUser(String fName, String lName, String userType, String username, String password) {
		boolean success = false;
		
		User user = new User(-1, fName, lName, userType, username, password, false);

		if(fName == null || fName.isBlank()) {
			loggy.warn("FirstName invalid. -> createNewUser (BankSystemImpl)");
			return false;
		}else if(lName == null || lName.isBlank()) {
			loggy.warn("LastName invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(userType == null || userType.isBlank()) {
			loggy.warn("Usertype invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(username == null || username.isBlank()) {
			loggy.warn("Username invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(password == null || password.isBlank()) {
			loggy.warn("Password invalid -> createNewUser (BankSystemImpl)");
			return false;
		}
		
		//check to see if username is already being used
		List<String> listOfAllUserNames = bank.viewUsernames();
		
		if(listOfAllUserNames != null && listOfAllUserNames.isEmpty() != true) {
			if(listOfAllUserNames.contains(username)) {
				loggy.warn("Username is already taken. Please choose another. -> createNewUser (BankSystemImpl)");
				return false;
			}
		}
		
		success = bank.newUser(user, userType);
		loggy.info("CREATE NEW USER: " + username + " : " + success);
		
		return success;
	}

	@Override
	public boolean authenticate(String username, String password) {
		if(username == null || username.isBlank()) {
			loggy.warn("Username invalid -> authenticate (BankSystemImpl)");
			return false;
		}else if(password == null || password.isBlank()) {
			loggy.warn("Password invalid -> authenticate (BankSystemImpl)");
			return false;
		}
		
		boolean success = false;
		
		User user = bank.login(username, password);
		if(user != null) {
			success = true;
			loggy.info("USER: " + username + " AUTHENTICATED");
		}
		
		return success;
	}

	
	@Override
	public Account verifyAccount(int accountId) {
		if(accountId <= -1) {
			loggy.warn("Account id is invalid. -> verifyAccount (BankSystemImpl)");
			return null;
		}
		
		Account account = null;
		account = bank.viewAccountByAccountId(accountId);
		if(account != null)
			loggy.info("ACCOUNT " + accountId + " VERIFIED.");
		
		return account;
	}

	@Override
	public List<Account> getCustomerAccounts(int userId) {
		List<Account> listOfCustomerAccounts = null;
		if(userId == -1) {
			loggy.warn("Not a valid ID. -> getCustomerAccounts (BankSystemImpl)");
		}
		
		listOfCustomerAccounts = bank.viewAccountByUserID(userId);
		if(listOfCustomerAccounts != null)
			loggy.info("RETRIEVED CUSTOMER #" + userId + " ACCOUNT");
		
		return listOfCustomerAccounts;
	}
	
	public boolean canWithdraw(Account account, double withdrawAmount) {
		if(account == null || account.isApproved() == false) {
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
			if(withdrawAmount > account.getBalance())
				loggy.warn("Invalid balance. Withdraw amount must be lower then account balance.");
			if(withdrawAmount <= 0.0) {
				loggy.warn("Withdraw amount in invalid. Please enter a valid amount.");
			}
			return false;
		}
		
		return true;
	}


	@Override
	public boolean withdraw(Account account, double withdrawAmount) {
		account.setBalance(account.getBalance() - withdrawAmount);
		boolean success = bank.withdraw(account);
		loggy.info("WITHDRAW FROM ACCOUNT #" + account.getId() + " OF AMOUNT " + withdrawAmount);
		return success;
	}
	
	public boolean canDeposit(Account account, double depositAmount) {
		if(account == null || account.isApproved() == false) {
			if(account == null)
				loggy.warn("Account is NULL -> canDeposit (BankSystemImpl)");
			else if(account.isApproved() == false)
				loggy.warn("Account not approved -> canDeposit (BankSystemImpl)");
			
			return false;
		}
		if(depositAmount <= 0.0) {
			if(depositAmount <= 0.0)
				loggy.warn("Deposit amount is invalid -> canDeposit (BankSystemImpl)");
			return false;
		}
		
		if(depositAmount + account.getBalance() > 500000.00) {
			loggy.info("An account max if $500,000.00. Please create a new account if and splint the amount between two accounts.");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deposit(Account account, double depositAmount) {
		account.setBalance(account.getBalance() + depositAmount);
		boolean success = bank.deposit(account);
		loggy.info("DEPOSIT FROM ACCOUNT #" + account.getId() + " OF AMOUNT " + depositAmount);
		return success;
	}
	
	//check to make sure not a negative amount and etc
	public boolean canTransfer(Account transferingFrom, double transferAmount) {
		if(transferingFrom == null || transferingFrom.isApproved() == false) {
			loggy.warn("Account is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		if(transferAmount <= 0.0) {
			loggy.warn("Transfer amount is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean transfer(Account originAccount, Account transferToAccount, double transferAmount) {
		if(transferToAccount == null) {
			loggy.warn("Desired account to transfer to is invalid. -> transfer (BankSystemImpl)");
			return false;
		}
		
		if(originAccount == null) {
			loggy.warn("Account given is invalid. -> transfer (BankSystemImpl)");
			return false;
		}
		
		if(originAccount.getId() == transferToAccount.getId()) {
			loggy.warn("Transfering to the same account. Transaction voided.");
			return false;
		}
		
		if(transferToAccount.isApproved() == false) {
			loggy.warn("Account to transfer to is locked. Transaction voided.");
			return false;
		}
		
		if(transferAmount + transferToAccount.getBalance() > 500000.00) {
			loggy.info("An account max if $500,000.00. Please create a new account if and splint the amount between two accounts.");
		}
		
		originAccount.setBalance(originAccount.getBalance() - transferAmount);
		transferToAccount.setBalance(transferToAccount.getBalance() + transferAmount);
		
		boolean success = bank.transfer(originAccount, transferToAccount, transferAmount);
		if(success)
			loggy.info("TRANSFER FROM ACCOUNT #" + originAccount.getId() + " OF AMOUNT " +transferAmount + " TO ACCOUNT #" + transferToAccount.getId());
		
		return success;
	}
	
	@Override
	public User getUserById(int userId) {
		User user = null;
		if(userId == -1) {
			loggy.warn("Userid invalid. -> getUserById (BankSystemImpl)");
			return null;
		}
			
		user = bank.viewUserById(userId);
		if(user != null)
			loggy.info("USER #" + userId + " WAS RETRIEVED");
		
		return user;
	}

	@Override
	public Account getAccountById(int accountId) {
		if(accountId == -1) {
			loggy.warn("Account id is invalid. -> getAccountById (BankSystemImpl)");
			return null;
		}
		
		Account account = bank.viewAccountByAccountId(accountId);
		if(account != null)
			loggy.info("ACCOUNT #" + accountId + " WAS RETRIEVED");
		
		return account;
	}

	@Override
	public User getUserViaAccountNumber(int accountId) {
		if(accountId == -1) {
			loggy.warn("Account id is invalid. -> getUserViaAccountNumber (BankSystemImpl)");
			return null;
		}
		
		User user = bank.viewUserFromAccountId(accountId);
		if(user != null)
			loggy.info("USER #" + user.getId() + " WAS RETRIEVED");
		
		return user;
	}

	@Override
	public List<Account> accountsToBeApproved(int userId){
		List<Account> unapprovedAccounts = null;
		
		User user = bank.viewUserById(userId);
		if(user == null || user.getUserType().equals("Customer")) {
			loggy.warn("User doesn't exist. Can not approve account. -> accountsToBeApproved (BankSystemImpl)");
			return null;
		}
		
		unapprovedAccounts = bank.viewUnapprovedAccounts();		
		
		return unapprovedAccounts;
	}

	@Override
	public boolean approveAccount(Account account) {
		account.setApproved(!account.isApproved());	
		loggy.info("ACCOUNT #" + account.getId() + " WAS APPROVED");
		
		return bank.updateAccountApproval(account);
	}
	
	@Override
	public List<User> getAllUsers(){
		return bank.viewAllUsers();
	}

	@Override
	public boolean approveUser(User user) {
		user.setUserApproved(!user.isUserApproved());	
		loggy.info("USER #" + user.getId() + " WAS APPROVED");
		
		return bank.updateUserApproval(user);
	}
	
	public boolean updateUserPassword(User user) {
		loggy.info("User #" + user.getId() + "'S PASSWORD WAS UPDATED.");
		return bank.updateUserPassword(user);
	}

	@Override
	public boolean closeAccount(Account a) {
		loggy.info("ACCOUNT #" + a.getId() + " WAS CLOSED");
		return bank.deleteAccount(a);
	}

	@Override
	public List<Account> getAllAccounts() {
		return bank.viewAllAccounts();
	}
	
	
}
