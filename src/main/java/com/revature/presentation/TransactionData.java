package com.revature.presentation;

import com.revature.models.BankAccount;

public class TransactionData {
	public BankAccount bankAccountTo;
	public BankAccount bankAccountFrom;
	public int ammount;
	public boolean canceled = false;
	public boolean exitProgram= false;
}
