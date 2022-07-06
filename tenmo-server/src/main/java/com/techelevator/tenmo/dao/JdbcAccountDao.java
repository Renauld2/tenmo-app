package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getCurrentBalance(int account_id) {
        double balance = 0;

        String sql = "SELECT balance" +
                "FROM tenmo_account" +
                "WHERE user_id = ?;";
        SqlRowSet rowset  = jdbcTemplate.queryForRowSet(sql, account_id);

        return mapRowToAccount(rowset).getBalance();
    }

    private Account mapRowToAccount(SqlRowSet rowSet)  {
        Account mapRowToAccount = new Account();

        mapRowToAccount.setAccountID(rowSet.getInt("account_id"));
        mapRowToAccount.setBalance(rowSet.getDouble("balance"));
        mapRowToAccount.setUserID(rowSet.getInt("user_id"));

        return mapRowToAccount;

    }
}
