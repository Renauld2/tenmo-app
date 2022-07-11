package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        //return getCurrentBalance(accountFrom) - amountToWithdraw;

        System.out.println("hello?");
        System.out.println(amountToWithdraw);
        System.out.println(accountFrom);

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
    @Override
    public List<Account> getAllAccounts(){
        List<Account> allAccounts = new ArrayList<>();

        String sql = "SELECT user_id, username  " +
                "FROM tenmo_user";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            Account account = mapRowToAccount(rowSet);
            allAccounts.add(account);

        }
            return allAccounts;

    }

    private Account mapRowToAccount(SqlRowSet rowSet)  {
        Account mapRowToAccount = new Account();

        mapRowToAccount.setAccountId(rowSet.getInt("account_id"));
        mapRowToAccount.setBalance(rowSet.getDouble("balance"));
        mapRowToAccount.setUserId(rowSet.getInt("user_id"));

        return mapRowToAccount;

    }
}
