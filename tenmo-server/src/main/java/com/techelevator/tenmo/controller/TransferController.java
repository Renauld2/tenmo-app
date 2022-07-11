package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private final AccountDao  accountDao;
    private final TransferDao transferDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/tenmo_transfer", method = RequestMethod.POST)
    public void createTransfer(@Valid @RequestBody Transfer transfer) throws AccountNotFoundException {

        System.out.println("DEBUG");
        System.out.println(transfer);

       int accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
       int accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());

        System.out.println(accountFrom);
        System.out.println(accountTo);
        transfer.setAccountFrom(accountFrom);
        transfer.setAccountTo(accountTo);

       double transferAmount = transfer.getAmount();


       accountDao.withdraw(transferAmount, accountFrom);
       accountDao.deposit(transferAmount, accountTo); //FixMe these are not performing correctly


       transferDao.createTransfer(transfer);

    }


}
