package com.techelevator.tenmo.dao;

public interface AccountDao {

    double getCurrentBalance(int user_Id);

    double withdraw(double amountToWithdraw, int accountFrom);

    double deposit(double amountToDeposit, int accountTo);

    int getAccountByAccountId(int userId);
}
