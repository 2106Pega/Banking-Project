package com.revature;

import java.util.List;

public interface BankService {

	User logIn(String username, String password);

	boolean testNewUser(String un); //return true if username is unique

	boolean createNewUser(String firstName, String lastName, String userName, String password);

	boolean withdraw(User u, int amount);

	void transfer(User u, int toAccount, int amount);

	void deposit(User u, int amount);

	void approveAllAccounts();

	List<User> getUnapproved();

	User logInbyId(User n);


}

