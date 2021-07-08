package com.revature;

import java.util.List;

public interface BankDAO {
	
	
	public User insertNewUser(User u);
	
	public User selectUserbyId(User u);
	public User selectUserbyLogIn(User u);
	
	public boolean updateWithdraw(User u, int Ammount);
	public boolean updateDeposit(User u, int Ammount);

	public boolean testUsername(String un);

	public boolean checkAccount(User u);

	public void approveAllAccounts();

	public List<User> getUnapproved();
}
