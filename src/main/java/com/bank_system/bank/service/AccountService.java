package com.bank_system.bank.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import com.bank_system.bank.dao.Account1Dao;
import com.bank_system.bank.dao.Account2Dao;
import com.bank_system.bank.model.*;
import com.example.bank.model.*;
import com.bank_system.bank.service.interfaces.ITransactionService;
import com.bank_system.bank.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_system.bank.model.Account1;
import com.bank_system.bank.service.interfaces.IAccountService;

@Service
public class AccountService implements IAccountService {
	
	private static int nextAccountNumber = 11223145;

    @Autowired
    private Account1Dao account1Dao;

    @Autowired
    private Account2Dao account2Dao;

    @Autowired
    private IUserService IUserService;
    
    @Autowired
    private ITransactionService ITransactionService;

    public Account1 createAccount1() {
        Account1 account1 = new Account1();
        account1.setAccountBalance(new BigDecimal(0.0));
        account1.setAccountNumber(accountGen());

        account1Dao.save(account1);

        return account1Dao.findByAccountNumber(account1.getAccountNumber());
    }

    public Account2 createAccount2() {
        Account2 account2 = new Account2();
        account2.setAccountBalance(new BigDecimal(0.0));
        account2.setAccountNumber(accountGen());

        account2Dao.save(account2);

        return account2Dao.findByAccountNumber(account2.getAccountNumber());
    }
    
    public void deposit(String accountType, double amount, Principal principal) {
        User user = IUserService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Account1")) {
            Account1 account1 = user.getAccount1();
            account1.setAccountBalance(account1.getAccountBalance().add(new BigDecimal(amount)));
            account1Dao.save(account1);

            Date date = new Date();

            Account1Transaction account1Transaction = new Account1Transaction(date, "Deposit to Account1", "Account", "Finished", amount, account1.getAccountBalance(), account1);
            ITransactionService.saveAccount1DepositTransaction(account1Transaction);
            
        } else if (accountType.equalsIgnoreCase("Account2")) {
            Account2 account2 = user.getAccount2();
            account2.setAccountBalance(account2.getAccountBalance().add(new BigDecimal(amount)));
            account2Dao.save(account2);

            Date date = new Date();
            Account2Transaction account2Transaction = new Account2Transaction(date, "Deposit to Account2", "Account", "Finished", amount, account2.getAccountBalance(), account2);
            ITransactionService.saveAccount2DepositTransaction(account2Transaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = IUserService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Account1")) {
            Account1 account1 = user.getAccount1();
            account1.setAccountBalance(account1.getAccountBalance().subtract(new BigDecimal(amount)));
            account1Dao.save(account1);

            Date date = new Date();

            Account1Transaction account1Transaction = new Account1Transaction(date, "Withdraw from Account1", "Account", "Finished", amount, account1.getAccountBalance(), account1);
            ITransactionService.saveAccount1WithdrawTransaction(account1Transaction);
        } else if (accountType.equalsIgnoreCase("Account2")) {
            Account2 account2 = user.getAccount2();
            account2.setAccountBalance(account2.getAccountBalance().subtract(new BigDecimal(amount)));
            account2Dao.save(account2);

            Date date = new Date();
            Account2Transaction account2Transaction = new Account2Transaction(date, "Withdraw from Account2", "Account", "Finished", amount, account2.getAccountBalance(), account2);
            ITransactionService.saveAccount2WithdrawTransaction(account2Transaction);
        }
    }
    
    private int accountGen() {
        return ++nextAccountNumber;
    }

}
