package com.formation.banque.Service.Impl;



import com.formation.banque.DAO.CompteDAO;
import com.formation.banque.DAO.TransactionDAO;
import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;
import com.formation.banque.Service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final CompteDAO compteDAO;

    public TransactionServiceImpl(
            TransactionDAO transactionDAO,
            CompteDAO compteDAO
    ) {
        this.transactionDAO = transactionDAO;
        this.compteDAO = compteDAO;
    }


    @Override
    public Transaction ajouterTransaction(
            double montant,
            TypeTransaction type,
            String lieu,
            String idCompte
    ) {

        compteDAO.findById(idCompte)
                .orElseThrow(() ->
                        new RuntimeException("Compte introuvable")
                );

        if (montant <= 0) {
            throw new IllegalArgumentException(
                    "Le montant doit être supérieur à 0"
            );
        }

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                montant,
                type,
                lieu,
                idCompte
        );

        transactionDAO.create(transaction);

        return transaction;
    }


    @Override
    public List<Transaction> listerTransactions() {

        return transactionDAO.findAll()
                .stream()
                .sorted(
                        Comparator.comparing(Transaction::date)
                                .reversed()
                )
                .toList();
    }



    @Override
    public List<Transaction> transactionsParCompte(
            String idCompte
    ) {

        return transactionDAO.findByCompte(idCompte)
                .stream()
                .sorted(
                        Comparator.comparing(Transaction::date)
                                .reversed()
                )
                .toList();
    }


    @Override
    public List<Transaction> filtrerParType(
            TypeTransaction type
    ) {

        return transactionDAO.findAll()
                .stream()
                .filter(transaction ->
                        transaction.type() == type
                )
                .toList();
    }


    @Override
    public List<Transaction> filtrerParMontant(
            double montantMinimum
    ) {

        return transactionDAO.findAll()
                .stream()
                .filter(transaction ->
                        transaction.montant() >= montantMinimum
                )
                .toList();
    }


    @Override
    public List<Transaction> filtrerParDate(
            LocalDate date
    ) {

        return transactionDAO.findAll()
                .stream()
                .filter(transaction ->
                        transaction.date()
                                .toLocalDate()
                                .equals(date)
                )
                .toList();
    }


    @Override
    public List<Transaction> filtrerParLieu(
            String lieu
    ) {

        return transactionDAO.findAll()
                .stream()
                .filter(transaction ->
                        transaction.lieu()
                                .equalsIgnoreCase(lieu)
                )
                .toList();
    }



    @Override
    public Map<TypeTransaction, List<Transaction>>
    grouperParType() {

        return transactionDAO.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Transaction::type
                        )
                );
    }


    @Override
    public double montantTotal() {

        return transactionDAO.findAll()
                .stream()
                .mapToDouble(Transaction::montant)
                .sum();
    }



    @Override
    public double montantMoyen() {

        return transactionDAO.findAll()
                .stream()
                .mapToDouble(Transaction::montant)
                .average()
                .orElse(0.0);
    }



    @Override
    public List<Transaction> detecterTransactionsSuspectes(
            double seuil
    ) {

        return transactionDAO.findAll()
                .stream()
                .filter(transaction ->
                        transaction.montant() > seuil
                )
                .toList();
    }



    @Override
    public void supprimerTransaction(String id) {

        transactionDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaction introuvable"
                        )
                );

        transactionDAO.delete(id);
    }

    @Override
    public List<Transaction> transactionsParClient(String idClient) {

        return compteDAO.findByClient(idClient)
                .stream()

                .flatMap(compte ->
                        transactionDAO
                                .findByCompte(compte.getId())
                                .stream()
                )

                .sorted(
                        Comparator.comparing(
                                Transaction::date
                        ).reversed()
                )

                .toList();
    }
}
