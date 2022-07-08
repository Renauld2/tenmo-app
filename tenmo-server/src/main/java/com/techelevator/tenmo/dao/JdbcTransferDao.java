package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public double getTransferHistory(int transferID) {
        return 0;
    }

    @Override
    public double getTransferById(int transferID) {
       double transferBalance = 0;


       String sql = "SELECT tenmo_transfer.transfer_id, tenmo_transfer.transfer_type_id," +
               " tenmo_transfer.transfer_status_id, tenmo_transfer.account_from, tenmo_transfer.account_to, tenmo_transfer.amount" +
               "FROM tenmo_transfer" +
               "INNER JOIN transfer_type ON tenmo_transfer.transfer_type_id = tenmo_transfer.transfer_type_id" +
               "INNER JOIN transfer_status ON tenmo_transfer.transfer_status_id = transfer_status.transfer_status_id" +
               "JOIN tenmo_account accFrom ON account_from = accFrom.account_id" +
               "JOIN tenmo_account accTo ON account_to = accTo.account_id" +
               "WHERE accfrom.user_id ?;";

        transferBalance = jdbcTemplate.queryForObject(sql, double.class, transferID);

        return transferBalance;
    }

    public Transfer createTransfer(Transfer createTransfer){


        String sql = "INSERT INTO tenmo_transfer (transfer_id, transfer_status_id, transfer_type_id, account_from, account_to, amount)" +
                "VALUES (?, ?, ?, ?, ?, ?);";

        Transfer createTransfer = jdbcTemplate.queryForObject(sql, Integer.class,
                createTransfer.getTransferId(),
                createTransfer.getTransferStatusId(),
                createTransfer.getTransferTypeId(),
                createTransfer.getAccountFrom(),
                createTransfer.getAccountTo(),
                createTransfer.getAmount()) ;

        return null;
    }


}
