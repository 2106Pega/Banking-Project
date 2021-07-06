package com.company.Controllers.DataAccess.DAO;

import com.company.Controllers.DataAccess.DBUtil.DBDriver;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.Transaction;
import com.company.Models.ModelTemplates.User;

import java.util.ArrayList;

public interface TransactionsDAO extends DBDriver {
    void addTransaction(String requestType, int accountId, double amount );
    ArrayList<Transaction> getAllTransacation();
    ArrayList<Transaction> usersTransactions(User user);
    ArrayList<Transaction> accountsTransactions(Account account);
}
