package com.revature.dao;

import java.util.List;

import com.revature.models.Transactions;

public interface TransactionsDao {

	void createTranRecord(int accountNumber, double amount, String transactionType);

	void createTransferTranRecord(int accountNumber, double amount, String transactionType, int transferToNumber);

	List<Transactions> getAllTransactions();

	void createAppTranRecord(double amount, String transactionType, String accountType);
}
