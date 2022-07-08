package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;

public interface TransferDao {

    double getTransferHistory(int transferId);

    Transfer getTransferById(int transferId);

    Transfer createTransfer(Transfer transfer) throws AccountNotFoundException;
}
