package com.bank_system.bank.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bank_system.bank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_system.bank.dao.Account1Dao;
import com.bank_system.bank.dao.Account1TransactionDao;
import com.bank_system.bank.dao.RecipientDao;
import com.bank_system.bank.dao.Account2Dao;
import com.bank_system.bank.dao.Account2TransactionDao;
import com.bank_system.bank.model.Account1;
import com.bank_system.bank.service.interfaces.ITransactionService;

@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private com.bank_system.bank.service.interfaces.IUserService IUserService;
	
	@Autowired
	private Account1TransactionDao account1TransactionDao;
	
	@Autowired
	private Account2TransactionDao account2TransactionDao;
	
	@Autowired
	private Account1Dao account1Dao;
	
	@Autowired
	private Account2Dao account2Dao;
	
	@Autowired
	private RecipientDao recipientDao;
	

	public List<Account1Transaction> findAccount1TransactionList(String username){
        User user = IUserService.findByUsername(username);
        List<Account1Transaction> account1TransactionList = user.getAccount1().getAccount1TransactionList();

        return account1TransactionList;
    }

    public List<Account2Transaction> findAccount2TransactionList(String username) {
        User user = IUserService.findByUsername(username);
        List<Account2Transaction> account2TransactionList = user.getAccount2().getAccount2TransactionList();

        return account2TransactionList;
    }

    public void saveAccount1DepositTransaction(Account1Transaction account1Transaction) {
        account1TransactionDao.save(account1Transaction);
    }

    public void saveAccount2DepositTransaction(Account2Transaction account2Transaction) {
        account2TransactionDao.save(account2Transaction);
    }
    
    public void saveAccount1WithdrawTransaction(Account1Transaction account1Transaction) {
        account1TransactionDao.save(account1Transaction);
    }

    public void saveAccount2WithdrawTransaction(Account2Transaction account2Transaction) {
        account2TransactionDao.save(account2Transaction);
    }
    
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, Account1 account1, Account2 account2) throws Exception {
        if (transferFrom.equalsIgnoreCase("Account1") && transferTo.equalsIgnoreCase("Account2")) {
            account1.setAccountBalance(account1.getAccountBalance().subtract(new BigDecimal(amount)));
            account2.setAccountBalance(account2.getAccountBalance().add(new BigDecimal(amount)));
            account1Dao.save(account1);
            account2Dao.save(account2);

            Date date = new Date();

            Account1Transaction account1Transaction = new Account1Transaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), account1.getAccountBalance(), account1);
            account1TransactionDao.save(account1Transaction);
        } else if (transferFrom.equalsIgnoreCase("Account2") && transferTo.equalsIgnoreCase("Account1")) {
            account1.setAccountBalance(account1.getAccountBalance().add(new BigDecimal(amount)));
            account2.setAccountBalance(account2.getAccountBalance().subtract(new BigDecimal(amount)));
            account1Dao.save(account1);
            account2Dao.save(account2);

            Date date = new Date();

            Account2Transaction account2Transaction = new Account2Transaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), account2.getAccountBalance(), account2);
            account2TransactionDao.save(account2Transaction);
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
    
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, Account1 account1, Account2 account2) {
        if (accountType.equalsIgnoreCase("Account1")) {
            account1.setAccountBalance(account1.getAccountBalance().subtract(new BigDecimal(amount)));
            account1Dao.save(account1);

            Date date = new Date();

            Account1Transaction account1Transaction = new Account1Transaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), account1.getAccountBalance(), account1);
            account1TransactionDao.save(account1Transaction);
        } else if (accountType.equalsIgnoreCase("Account2")) {
            account2.setAccountBalance(account2.getAccountBalance().subtract(new BigDecimal(amount)));
            account2Dao.save(account2);

            Date date = new Date();

            Account2Transaction account2Transaction = new Account2Transaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), account2.getAccountBalance(), account2);
            account2TransactionDao.save(account2Transaction);
        }
    }
}
