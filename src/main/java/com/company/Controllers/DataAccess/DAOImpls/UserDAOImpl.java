package com.company.Controllers.DataAccess.DAOImpls;


import com.company.Controllers.DataAccess.DAO.UserDAO;
import com.company.Controllers.DataAccess.DBUtil.DBDriver;
import com.company.Controllers.PasswordHelper.PasswordHelper;
import com.company.Models.ModelTemplates.Account;
import com.company.Models.ModelTemplates.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {


    @Override
    public ArrayList<User> getAllUsers() {
        try {
            Connection connection = getConnect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId( resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAccessLevel(resultSet.getInt("access"));
                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                users.add(user);
            }
            return users;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Connection connection;

        try{
            connection = getConnect();
            User user = null;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username= ? ");
            statement.setString(1,username);
            ResultSet userQueryResults = statement.executeQuery();
            if ( userQueryResults.next()  ){
                user = new User();
                user.setId( userQueryResults.getInt("id"));
                user.setUsername(userQueryResults.getString("username"));
                user.setPassword(userQueryResults.getString("password"));
                user.setAccessLevel(userQueryResults.getInt("access"));
                user.setFirst_name(userQueryResults.getString("first_name"));
                user.setLast_name(userQueryResults.getString("last_name"));
            }
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }



    @Override
    public String createUser(String username, String password, String first_name, String last_name) {
        try {
            Connection connection  = this.getConnect();
            if  ( this.getUserByUsername(username) != null){
                return "User exist";
            }
            String insertQuery = "INSERT INTO users(username,password,first_name,last_name) values (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1,username);
            PasswordHelper helper = new PasswordHelper();
            String hashedPassword = helper.generateHash(password).toString();
            statement.setString(2, hashedPassword);
            statement.setString(3,first_name);
            statement.setString(4,last_name);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Successfully created account";
    }
    public User accountToUser(Account account ){
        User user = null; 
        try {
            Connection connection = this.getConnect();
            String statement = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,account.getUser_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAccessLevel(resultSet.getInt("access"));
            user.setFirst_name(resultSet.getString("first_name"));
            user.setLast_name(resultSet.getString("last_name"));
            
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
