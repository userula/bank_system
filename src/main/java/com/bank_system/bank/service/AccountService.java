package com.bank_system.bank.service;

import com.bank_system.bank.dao.AccountDao;
import com.bank_system.bank.model.BankAccount;
import com.bank_system.bank.model.Transaction;
import com.bank_system.bank.model.User;
import com.bank_system.bank.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

public class AccountService {

    private static int nextAccountNumber = 11223145;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ITransactionService transactionService;

    public BankAccount createAccount() {
        BankAccount account = new BankAccount();
        account.setAccountBalance(new BigDecimal(0.0));
        account.setAccountNumber(accountGen());

        accountDao.save(account);

        return accountDao.findByAccountNumber(account.getAccountNumber());
    }

    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            BankAccount account = user.getBankAccount();
            account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
            accountDao.save(account);

            Date date = new Date();

            Transaction primaryTransaction = new Transaction(date, "Deposit to Primary Account", "Account", "Finished", amount, account.getAccountBalance(), account);
            transactionService.saveAccountDepositTransaction(primaryTransaction);
        }
    }

    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            BankAccount primaryAccount = user.getBankAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            accountDao.save(primaryAccount);

            Date date = new Date();

            Transaction primaryTransaction = new Transaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.saveAccountWithdrawTransaction(primaryTransaction);
        }
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }

}
