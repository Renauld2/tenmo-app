package com.techelevator.tenmo.model;


public class Transfer {

    private int transferId;
    private int transferStatusId;
    private int transferTypeId;

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferStatusId=" + transferStatusId +
                ", transferTypeId=" + transferTypeId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }

    private long accountFrom;
    private long accountTo;
    private double amount;

    public Transfer(int transferId, int transferStatusId, int transferTypeId, int accountFrom, int accountTo, double amount) {
        this.transferId = transferId;
        this.transferStatusId = transferStatusId;
        this.transferTypeId = transferTypeId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {

    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

}
