package com.revature.pzero.service;

import java.util.List;

import com.revature.pzero.models.Account;
import com.revature.pzero.models.User;
import com.revature.pzero.repository.Bank;

public class BankSystemImpl implements BankSystem{

	private Bank bank;
	
	public BankSystemImpl(Bank bank) {
		this.bank = bank;
	}

//	@Override
//	public boolean approveRequest() {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public User login(String username, String password) {
		User user = null;
		
		if(username.isBlank() || username.equals(null)) {
			System.out.println("Username is invalid. -> login(BankSystemImpl)");
			return null;
		}else if(password.isBlank() || password.equals(null)) {
			System.out.println("Password is invalid. -> login(BankSystemImpl)");
			return null;
		}
		
		user = bank.login(username, password);
		
		return user;
	}

	@Override
	public boolean createNewAccount(Double balance, String nickName, boolean approved) {
		boolean success = false;
		if(balance < 0.001 || balance > Double.MAX_VALUE || balance == null) {
			System.out.println("Balance is invalid." + balance + "  -> createNewAccount(BankSystemImpl)"); //inputting a value of 0.001?
			return false;
		}	
		
		Account account = new Account(-1, balance, nickName, approved);
		success = bank.newAccount(account);
		
		return false;
	}

	@Override
	public boolean createNewUser(String fName, String lName, String userType, String username, String password) {
		boolean success = false;
		
		User user = new User(-1, fName, lName, userType, username, password);

		if(fName.equals(null) || fName.isBlank()) {
			System.out.println("FirstName invalid. -> createNewUser (BankSystemImpl)");
			return false;
		}else if(lName.equals(null) || lName.isBlank()) {
			System.out.println("LastName invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(userType.isBlank() || userType.equals(null)) {
			System.out.println("Usertype invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(username.equals(null) || username.isBlank()) {
			System.out.println("Username invalid -> createNewUser (BankSystemImpl)");
			return false;
		}else if(password.equals(null) || password.isBlank()) {
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
		return success;
	}

//	@Override
//	public boolean register(User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public boolean viewLog() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticate(String username, String password) {
		if(username.equals(null) || username.isBlank()) {
			System.out.println("Username invalid -> authenticate (BankSystemImpl)");
			return false;
		}else if(password.equals(null) || password.isBlank()) {
			System.out.println("Password invalid -> authenticate (BankSystemImpl)");
			return false;
		}
		
		boolean success = false;
		
		User user = bank.login(username, password);
		if(!user.equals(null))
			success = true;
		
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
		
		return account;
	}

	@Override
	public List<Account> getCustomerAccounts(int userId) {
		List<Account> listOfCustomerAccounts = null;
		if(userId == -1) {
			System.out.println("Not a valid ID. -> getCustomerAccounts (BankSystemImpl)");
		}
		
		listOfCustomerAccounts = bank.viewAccountByUserID(userId);
		return listOfCustomerAccounts;
	}
	
	public boolean canWithdraw(Account account, double withdrawAmount) {
		if(account.equals(null) || account.isApproved() == false) {
			System.out.println("Account is invalid. -> canWithdraw (BankSystemImpl)");
			return false;
		}
		if(withdrawAmount > account.getBalance() || withdrawAmount <= 0.0) {
			System.out.println("Withdraw amount is invalid. -> canWithdraw (BankSystemImpl)");
			return false;
		}
		
		return true;
	}


	@Override
	public boolean withdraw(Account account, double withdrawAmount) {
		boolean success = bank.withdraw(account, withdrawAmount);
		return success;
	}
	
	public boolean canDeposit(Account account, double depositAmount) {
		if(account.equals(null) || account.isApproved() == false) {
			System.out.println("Account is invalid. -> canDeposit (BankSystemImpl)");
			return false;
		}
		if(depositAmount > account.getBalance() || depositAmount <= 0.0) {
			System.out.println("Deposit amount is invalid. -> canDeposit (BankSystemImpl)");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deposit(Account account, double depositAmount) {
		boolean success = bank.deposit(account, depositAmount);
		return success;
	}
	
	//check to make sure not a negative amount and etc
	public boolean canTransfer(Account transferingFrom, double transferAmount) {
		if(transferingFrom.equals(null) || transferingFrom.isApproved() == false) {
			System.out.println("Account is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		if(transferAmount > transferingFrom.getBalance() || transferAmount <= 0.0) {
			System.out.println("Transfer amount is invalid. -> canTransfer (BankSystemImpl)");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean transfer(Account originAccount, Account transferToAccount, double transferAmount) {
		if(transferToAccount.equals(null)) {
			System.out.println("Desired account to transfer to is invalid. -> transfer (BankSystemImpl)");
			return false;
		}
		
		boolean success = bank.transfer(originAccount, transferToAccount, transferAmount);
		return success;
	}
	
	@Override
	public User getUserById(int userId) {
		User user = null;
		if(userId == -1) {
			System.out.println("Userid invalid. -> getUserById (BankSystemImpl)");
			return null;
		}
			
		user = bank.getUserById(userId);
		
		return user;
	}

	@Override
	public Account getAccountById(int accountId) {
		if(accountId == -1) {
			System.out.println("Account id is invalid. -> getAccountById (BankSystemImpl)");
			return null;
		}
		
		Account account = bank.viewAccountByAccountId(accountId);
		return account;
	}

	@Override
	public User getUserViaAccountNumber(int accountId) {
		if(accountId == -1) {
			System.out.println("Account id is invalid. -> getUserViaAccountNumber (BankSystemImpl)");
			return null;
		}
		
		User user = bank.getUserFromAccountId(accountId);
		return user;
	}

	@Override
	public List<Account> accountsToBeApproved(int userId){
		List<Account> unapprovedAccounts = null;
		
		User user = bank.getUserById(userId);
		if(user.equals(null) || user.getUserType().equals("Customer")) {
			System.out.println("User doesn't exist. Can not approve account. -> accountsToBeApproved (BankSystemImpl)");
			return null;
		}
		
		unapprovedAccounts = bank.viewUnapprovedAccounts();
		
		return unapprovedAccounts;
	}

	@Override
	public boolean approveAccount(Account account) {
		account.setApproved(!account.isApproved());	
		bank.updateApproval(account);
		
		return false;
	}
	
	
}
