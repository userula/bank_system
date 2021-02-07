package com.bank_system.bank.service.interfaces;

import java.security.Principal;

import com.bank_system.bank.model.Account1;
import com.bank_system.bank.model.Account2;

public interface IAccountService {

	Account1 createAccount1();

	Account2 createAccount2();

	void deposit(String accountType, double amount, Principal principal);

	void withdraw(String accountType, double amount, Principal principal);
}
