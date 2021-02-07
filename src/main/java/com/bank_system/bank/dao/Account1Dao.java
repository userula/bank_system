package com.bank_system.bank.dao;

import com.bank_system.bank.model.Account1;
import org.springframework.data.repository.CrudRepository;

public interface Account1Dao extends CrudRepository<Account1,Long> {

    Account1 findByAccountNumber (int accountNumber);
}
