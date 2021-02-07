package com.bank_system.bank.dao;

import java.util.List;

import com.bank_system.bank.model.Account2Transaction;
import org.springframework.data.repository.CrudRepository;

public interface Account2TransactionDao extends CrudRepository<Account2Transaction, Long> {

    List<Account2Transaction> findAll();
}

