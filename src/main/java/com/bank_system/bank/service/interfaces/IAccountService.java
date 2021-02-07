package com.bank_system.bank.service.interfaces;

import com.bank_system.bank.model.BankAccount;

import java.security.Principal;

public interface IAccountService {
    BankAccount createBankAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
}
