package com.bank_system.bank.dao;

import com.bank_system.bank.model.Account2;
import org.springframework.data.repository.CrudRepository;

public interface Account2Dao extends CrudRepository<Account2, Long> {

    Account2 findByAccountNumber (int accountNumber);
}
