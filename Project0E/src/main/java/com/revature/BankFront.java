package com.revature;

public interface BankFront {
	
	public int getUserChoice(int numberOfChoices, int defaultInt);
	public void displayDirections();
	public void displayGreeting();
	public void displayMainMenu();
	public void displayFairwell();
	public User logIn();
	public void createAccount();
	public void displayEmployeeChoices();
	public void displayCustomerChoices();
	public void displayBalance(User u);
	public void displayWithdrawMenu(User u);
	public void displayDepositMenu(User u);
	public void displayTransferMenu(User u);
	public void viewAccountMenu();
	public void pendingAccountsMenu();
	public boolean isNumeric(String strNum);

	
}
