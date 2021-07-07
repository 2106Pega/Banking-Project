package com.revature.service;

import java.util.List;

import com.revature.dao.TransactionsDao;
import com.revature.models.Transactions;

public class TransactionManagerImpl implements TransactionManager {
	
	TransactionsDao tDao;

	public TransactionManagerImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTransactionRecord(int accountNumber, double amount, String transactionType) {
		tDao.createTranRecord(accountNumber, amount, transactionType);

	}

	@Override
	public void createTransactionRecord(int accountNumber, double amount, String transactionType, int transferToNumber) {
		tDao.createTransferTranRecord(accountNumber, amount, transactionType, transferToNumber);

	}

	@Override
	public void createTransactionRecord(double amount, String transactionType, String accountType) {
		tDao.createAppTranRecord(amount, transactionType, accountType);

	}

	@Override
	public List<Transactions> getAllTransactions() {
		
		return tDao.getAllTransactions();
	}

}
