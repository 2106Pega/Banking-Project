package com.company.Models.ModelTemplates;

import com.company.Controllers.DataAccess.DAOImpls.AccountDAOImpl;

import java.util.ArrayList;

public class User {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void print(){
        System.out.println(first_name);
        System.out.println(last_name);
        System.out.println(username);
        System.out.println("password::" + password);
        System.out.println(accessLevel);
        System.out.println(id);


    }
    public ArrayList<Account> getAccounts(){
        AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
        return accountDAOImpl.getAccountByUser(this);

    }


    private String first_name;
    private String last_name;
    private int id;
    private String username;
    private String password;
    private int accessLevel;
    /*
    1 == Registered user
    2 == Employee access

     */
}
