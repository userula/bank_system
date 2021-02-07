package com.bank_system.bank.dao;

import java.util.List;

import com.bank_system.bank.model.Account1Transaction;
import org.springframework.data.repository.CrudRepository;

public interface Account1TransactionDao extends CrudRepository<Account1Transaction, Long> {

    List<Account1Transaction> findAll();
}
