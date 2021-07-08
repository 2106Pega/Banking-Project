package com.revature.presentation;

import java.util.List;

import com.revature.application.HelperFunctions;
import com.revature.models.BankAccount;
import com.revature.models.Customer;
import com.revature.models.Transaction;
import com.revature.models.User;

public interface IO {
	
	LoginData loginForm();
	UserMenuResult userMenu(User u);
	AccountApplicationData applyForBankAccountForm();
	TransactionData transactionMenu(User u, HelperFunctions hf);
	void displayAccountBalances(List<BankAccount> accounts);

	void displayTransactionsAsClient(List<Transaction> transactions);
	CustomerInfoResult UpdateCustomerInfoMenu(Customer c);
	ApplicationAprovalResult employeeApproveAplicationMenu(BankAccount ba);
	EmployeeMenuResult employeeMenu();
	void displayTransactionsList(List<Transaction> transactions);
	void displayError(String message);
	void displayMessage(String message);
	void giveMoney(int transferAmmount);
	<T> T chooseFromList(List<T> list, String message);
	void displayAccountsBegin();
	void displayAccountsEnd();
};
