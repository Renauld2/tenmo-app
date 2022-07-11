package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {


    private final AccountDao accountDao;
    private final UserDao userDao;


    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }



    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public double getBalanceForUser(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        return accountDao.getCurrentBalance(userId);
    }



    @RequestMapping(path = "/tenmo_account", method = RequestMethod.GET)
    public List<User> getAllAccounts() {

        return userDao.findAll();
    }

//    @RequestMapping(path = "/tenmo_account", method = RequestMethod.PUT)
//    public void makeWithdraw(double amountToWithdraw, int accountFrom) {
//        accountDao.withdraw(amountToWithdraw, accountFrom);
//
//
//    }
//
//    @RequestMapping(path = "/tenmo_account", method = RequestMethod.PUT)
//    public void makeDeposit(double amountToDeposit, int accountTo) {
//        accountDao.deposit(amountToDeposit, accountTo);
//    }



}
