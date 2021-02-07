package com.bank_system.bank.dao;

import com.bank_system.bank.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionDao extends CrudRepository<Transaction, Long> {

    List<Transaction> findAll();
}
