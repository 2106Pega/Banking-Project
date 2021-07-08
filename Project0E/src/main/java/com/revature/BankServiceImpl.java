package com.revature;

import java.util.List;

public class BankServiceImpl implements BankService {

	BankDAO bd = new BankDAOImpl();
	
	public User logIn(String username, String password) {
		User u = new User("", "", username, password);
		bd.selectUserbyLogIn(u);
		return u;		
	}
	
	public User logInbyId(User n) {
		//System.out.println(n.getAccountId());
		bd.selectUserbyId(n);
		return n;		
	}

	public boolean testNewUser(String un) {
		return bd.testUsername(un);
	}

	public boolean createNewUser(String firstName, String lastName, String userName, String password) {
		User u = new User(firstName, lastName, userName, password);
		bd.insertNewUser(u);
		return true;
	}

	@Override
	public boolean withdraw(User u, int amount) {
		//System.out.println("BSI" +amount);
		if(u.getBalance() >= amount) {
			bd.updateWithdraw(u, amount);
			u.setBalance(u.getBalance() - amount);
			return true;
		}
		else {
			System.out.println("Insufficent funds");
			return false;
		}
	}

	@Override
	public void transfer(User u, int toAccount, int amount) {
		User n = new User();
		n.setAccountId(toAccount);
		if(bd.checkAccount(n)) {
			if(this.withdraw(u, amount)) {
				this.deposit(n, amount);
				System.out.println("The funds have been transfered");
			}
		} else {
			System.out.println("The account you are trying to send to doesn't exist");
		}

	}

	@Override
	public void deposit(User u, int amount) {
		bd.updateDeposit(u, amount);	
		u.setBalance(u.getBalance() + amount);
	}

	@Override
	public void approveAllAccounts() {
		bd.approveAllAccounts();
		
	}

	@Override
	public List<User> getUnapproved() {
		return bd.getUnapproved();
	}
	
}
