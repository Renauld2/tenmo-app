package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    double getCurrentBalance(int user_Id);

    void withdraw(double amountToWithdraw, int accountFrom);

    void deposit(double amountToDeposit, int accountTo);

    int getAccountByAccountId(int userId);

    List<Account> getAllAccounts();
}
