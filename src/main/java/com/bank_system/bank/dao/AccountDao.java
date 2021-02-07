package com.bank_system.bank.dao;

import com.bank_system.bank.model.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<BankAccount,Long> {

    BankAccount findByAccountNumber (int accountNumber);
}