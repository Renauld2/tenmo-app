package com.techelevator.tenmo.model;

public class Account {

    private int accountID;
    private int userID;
    private double balance;

    public Account (int accountID, int userID, double balance) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
    }

    public Account() {

    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}