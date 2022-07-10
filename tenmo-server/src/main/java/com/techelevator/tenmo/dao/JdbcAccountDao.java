package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getCurrentBalance(int userId) {
        Double balance = null;

        String sql = "SELECT balance " +
                "FROM tenmo_account " +
                "WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        if (rowSet.next()) {
            balance = rowSet.getDouble("balance");
        }
       // balance = jdbcTemplate.queryForObject(sql, double.class, userId);

        return balance;
    }

    @Override
    public double withdraw(double amountToWithdraw, int accountFrom) {
        return getCurrentBalance(accountFrom) - amountToWithdraw;
    }

    @Override
    public double deposit(double amountToDeposit, int accountTo) {
        return getCurrentBalance(accountTo) + amountToDeposit;
    }


    public int getAccountByAccountId(int userId)   {
        Integer accountId  = null;

        String sql = "SELECT account_id " +
                "FROM tenmo_account " +
                "WHERE user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        if (rowSet.next()) {
            accountId = rowSet.getInt("account_id");
        }
        return accountId;
    }

    private Account mapRowToAccount(SqlRowSet rowSet)  {
        Account mapRowToAccount = new Account();

        mapRowToAccount.setAccountId(rowSet.getInt("account_id"));
        mapRowToAccount.setBalance(rowSet.getDouble("balance"));
        mapRowToAccount.setUserId(rowSet.getInt("user_id"));

        return mapRowToAccount;

    }
}
