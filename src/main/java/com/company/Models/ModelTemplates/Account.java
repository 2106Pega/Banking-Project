package com.company.Models.ModelTemplates;

public class Account {



    public int getAccount_id() {
        return account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRouting_number() {
        return routing_number;
    }

    public int getAccount_number() {
        return account_number;
    }

    public double getBalance() {
        return balance;
    }


    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRouting_number(int routing_number) {
        this.routing_number = routing_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", user_id=" + user_id +
                ", routing_number=" + routing_number +
                ", account_number=" + account_number +
                ", balance=" + balance +
                ", approved=" + approved +
                '}';
    }

    private int account_id;
    private int user_id;
    private int routing_number;
    private int account_number;
    private double balance;
    private boolean approved;
    private boolean reviewed;

}
