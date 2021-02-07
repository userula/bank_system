package com.bank_system.bank.service.interfaces;

import java.security.Principal;
import java.util.List;

import com.bank_system.bank.model.*;
import com.example.bank.model.*;
import com.bank_system.bank.model.Account1Transaction;

public interface ITransactionService {
	List<Account1Transaction> findAccount1TransactionList(String username);

    List<Account2Transaction> findAccount2TransactionList(String username);

    void saveAccount1DepositTransaction(Account1Transaction account1Transaction);

    void saveAccount2DepositTransaction(Account2Transaction account2Transaction);
    
    void saveAccount1WithdrawTransaction(Account1Transaction account1Transaction);
    void saveAccount2WithdrawTransaction(Account2Transaction account2Transaction);
    
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, Account1 account1, Account2 account2) throws Exception;
    
    List<Recipient> findRecipientList(Principal principal);

    Recipient saveRecipient(Recipient recipient);

    Recipient findRecipientByName(String recipientName);

    void deleteRecipientByName(String recipientName);
    
    void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, Account1 account1, Account2 account2);
}
