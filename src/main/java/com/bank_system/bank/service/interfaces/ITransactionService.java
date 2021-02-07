package com.bank_system.bank.service.interfaces;

import com.bank_system.bank.model.BankAccount;
import com.bank_system.bank.model.Recipient;
import com.bank_system.bank.model.Transaction;

import java.security.Principal;
import java.util.List;

public interface ITransactionService {
    List<Transaction> findAccountTransactionList(String username);

    void saveAccountDepositTransaction(Transaction transaction);

    void saveAccountWithdrawTransaction(Transaction transaction);

    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, BankAccount account1, BankAccount account2) throws Exception;

    List<Recipient> findRecipientList(Principal principal);

    Recipient saveRecipient(Recipient recipient);

    Recipient findRecipientByName(String recipientName);

    void deleteRecipientByName(String recipientName);

    void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, BankAccount account1, BankAccount account2);
}
