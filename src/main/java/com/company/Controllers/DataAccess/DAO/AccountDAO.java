package com.company.Controllers.DataAccess.DAO;

import com.company.Controllers.DataAccess.DBUtil.DBDriver;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.Transaction;
import com.company.Models.ModelTemplates.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AccountDAO extends DBDriver {
    public ArrayList<Account> getAllAccounts();
    public ArrayList<Account> getAccountByUser(User user);
    public boolean saveAccount(Account account);
    public boolean generateAccount(User user, double initialValue);
    public Account accountByAccountRoutingNumber(int accountNumber, int routingNumber);

}
