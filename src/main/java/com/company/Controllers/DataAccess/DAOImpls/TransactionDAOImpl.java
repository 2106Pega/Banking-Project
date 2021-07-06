package com.company.Controllers.DataAccess.DAOImpls;

import com.company.Controllers.DataAccess.DAO.TransactionsDAO;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.Transaction;
import com.company.Models.ModelTemplates.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionDAOImpl implements TransactionsDAO {
    @Override
    public void addTransaction(String requestType, int accountId, double amount) {
        try {
            Connection connection = this.getConnect();
            String query = "INSERT INTO transacations(request_type,account_id,amount) values(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,requestType);
            statement.setInt(2, accountId);
            statement.setDouble(3,amount);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Transaction> getAllTransacation() {
        ArrayList<Transaction> transactions = new ArrayList();
        try {
            Connection connection = this.getConnect();
            String query = "SELECT * FROM transacations";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setRequestType(rs.getString("request_type"));
                transaction.setDate(rs.getDate("date"));
                transaction.setAmount(rs.getDouble("amount"));
                transactions.add(transaction);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public ArrayList<Transaction> usersTransactions(User user) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for ( Account account: user.getAccounts()){
            transactions.addAll(this.accountsTransactions(account));
        }
        return transactions;
    }

    @Override
    public ArrayList<Transaction> accountsTransactions(Account account) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        
        try {
            Connection connection = this.getConnect();
            String query = "SELECT * FROM transacations WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, account.getAccount_id());
            ResultSet rs = statement.executeQuery();
            
            while ( rs.next()){
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setRequestType(rs.getString("request_type"));
                transaction.setDate(rs.getDate("date"));
                transaction.setAmount(rs.getDouble("amount"));
                transactions.add(transaction);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return transactions;
    }

}
