package com.bank_system.bank.service;

import com.bank_system.bank.dao.AccountDao;
import com.bank_system.bank.dao.RecipientDao;
import com.bank_system.bank.dao.TransactionDao;
import com.bank_system.bank.model.BankAccount;
import com.bank_system.bank.model.Recipient;
import com.bank_system.bank.model.Transaction;
import com.bank_system.bank.model.User;
import com.bank_system.bank.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService implements ITransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionDao primaryTransactionDao;

    @Autowired
    private AccountDao primaryAccountDao;

    @Autowired
    private RecipientDao recipientDao;

    @Override
    public List<Transaction> findAccountTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<Transaction> primaryTransactionList = user.getBankAccount().getTransactions();

        return primaryTransactionList;
    }

    @Override
    public void saveAccountDepositTransaction(Transaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    public void saveAccountWithdrawTransaction(Transaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, BankAccount account1, BankAccount account2) throws Exception {
        if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
            account1.setAccountBalance(account1.getAccountBalance().subtract(new BigDecimal(amount)));
            account2.setAccountBalance(account2.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountDao.save(account1);

            Date date = new Date();

            Transaction primaryTransaction = new Transaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), account1.getAccountBalance(), account1);
            primaryTransactionDao.save(primaryTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }

    public List<Recipient> findRecipientList(Principal principal) {
        String username = principal.getName();
        List<Recipient> recipientList = recipientDao.findAll().stream()
                .filter(recipient -> username.equals(recipient.getUser().getUsername()))
                .collect(Collectors.toList());

        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientDao.save(recipient);
    }

    public Recipient findRecipientByName(String recipientName) {
        return recipientDao.findByName(recipientName);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientDao.deleteByName(recipientName);
    }

    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, BankAccount primaryAccount, BankAccount savingsAccount) {
        if (accountType.equalsIgnoreCase("Primary")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            Transaction primaryTransaction = new Transaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
            primaryTransactionDao.save(primaryTransaction);
        }
    }
}
