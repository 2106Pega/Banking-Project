package com.company.Controllers.DataAccess.DAOImpls;

import com.company.Controllers.DataAccess.DAO.AccountDAO;
import com.company.Controllers.DataAccess.DBUtil.DBDriver;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAOImpl implements AccountDAO {


    @Override
    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        Connection connection;
        try{
            connection = getConnect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account");
            statement.execute();
            ResultSet result = statement.getResultSet();
            while ( result.next() ){
                int account_id = result.getInt("id");
                int user_id = result.getInt("user_id");
                int routing_number = result.getInt("routing_number");
                int account_number= result.getInt("account_number");
                double balance = result.getInt("balance");
                boolean approved= result.getBoolean("approved");

                Account account = new Account();
                account.setAccount_id(account_id);
                account.setUser_id(user_id);
                account.setRouting_number(routing_number);
                account.setAccount_number(account_number);
                account.setBalance(balance);
                accounts.add(account);
                account.setApproved(approved);


            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return accounts;

    }
    @Override
    public ArrayList<Account> getAccountByUser(User user) {
        ArrayList<Account> userAccounts = new ArrayList<>();
        try {
            PreparedStatement statment = this.getConnect().prepareStatement("SELECT * FROM account WHERE user_id = ?");
            statment.setInt(1,user.getId());
            ResultSet set = statment.executeQuery();
            while ( set.next() ){
                int account_id = set.getInt("id");
                int user_id = set.getInt("user_id");
                int routing_number = set.getInt("routing_number");
                int account_number= set.getInt("account_number");
                double balance = set.getInt("balance");
                boolean approved = set.getBoolean("approved");

                Account account = new Account();
                account.setAccount_id(account_id);
                account.setUser_id(user_id);
                account.setRouting_number(routing_number);
                account.setAccount_number(account_number);
                account.setBalance(balance);
                account.setApproved(approved);
                userAccounts.add(account);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userAccounts;
    }
    @Override
    public boolean saveAccount(Account account){
        try {
            Connection connection = this.getConnect();
            String query = "UPDATE account SET " +
                    "id=" + "?,"+
                    "user_id=" + "?,"+
                    "routing_number=" + "?,"+
                    "account_number=" + "?,"+
                    "balance=" + "?,"+
                    "approved=" + "?"+
                    "WHERE id = " + "?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,account.getAccount_id());
            preparedStatement.setInt(2,account.getUser_id());
            preparedStatement.setInt(3,account.getRouting_number());
            preparedStatement.setInt(4, account.getAccount_number());
            preparedStatement.setDouble(5,account.getBalance());
            preparedStatement.setBoolean(6,account.isApproved());
            preparedStatement.setInt(7,account.getAccount_id());


            return preparedStatement.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
