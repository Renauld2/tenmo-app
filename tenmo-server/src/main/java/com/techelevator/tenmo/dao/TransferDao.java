package com.techelevator.tenmo.dao;

public interface TransferDao {

    double getTransferHistory(int transferID);

    double getTransferById(int transferID);
}
