package com.formation.banque.DAO;

import com.formation.banque.Entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDAO {

    void create(Transaction transaction);

    Optional<Transaction> findById(String id);

    List<Transaction> findByCompte(String idCompte);

    List<Transaction> findAll();

    void update(Transaction transaction);

    void delete(String id);
}