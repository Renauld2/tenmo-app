package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }


    @Override
    public double getTransferHistory(int transferID) {
        return 0;
    }

    @Override
    public Transfer getTransferById(int transferID) {
       Transfer transferBalance = null;


       String sql = "SELECT tenmo_transfer.transfer_id, tenmo_transfer.transfer_type_id, " +
               " tenmo_transfer.transfer_status_id, tenmo_transfer.account_from, tenmo_transfer.account_to, tenmo_transfer.amount " +
               "FROM tenmo_transfer " +
               "INNER JOIN transfer_type ON tenmo_transfer.transfer_type_id = tenmo_transfer.transfer_type_id " +
               "INNER JOIN transfer_status ON tenmo_transfer.transfer_status_id = transfer_status.transfer_status_id " +
               "JOIN tenmo_account accFrom ON account_from = accFrom.account_id " +
               "JOIN tenmo_account accTo ON account_to = accTo.account_id " +
               "WHERE accfrom.user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferID);

        if (rowSet.next()) {
            transferBalance = mapRowToTransfer(rowSet);
        }
        return transferBalance;
    }


    @Override
    public Transfer createTransfer(Transfer transfer) throws AccountNotFoundException {


        String sql = "INSERT INTO tenmo_transfer (transfer_status_id, transfer_type_id, account_from, account_to, amount) " +
                "VALUES (2, 2, ?, ?, ?) " +
                "RETURNING transfer_id;";


        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class,
                //transfer.getTransferId(),
                transfer.getAccountFrom(),
                transfer.getAccountTo(),
                transfer.getAmount()) ;

       return getTransferById(newTransferId);
    }

    @Override
    public double withdraw(double amountToWithdraw, int accountFrom) {
        //return getCurrentBalance(accountFrom) - amountToWithdraw;
        String sql = "UPDATE tenmo_account " +
                "SET balance =  balance - ?" +
                "WHERE user_id  = ?;";
        return jdbcTemplate.update(sql, amountToWithdraw, accountFrom);

    }

    @Override
    public double deposit(double amountToDeposit, int accountTo) {
        String sql = "UPDATE tenmo_account " +
                "SET balance = balance +  ? " +
                "WHERE user_id = ?;";

        return jdbcTemplate.update(sql, amountToDeposit, accountTo);
    }



    private Transfer mapRowToTransfer(SqlRowSet rowSet)  {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getDouble("amount"));

        return transfer;

    }

}
