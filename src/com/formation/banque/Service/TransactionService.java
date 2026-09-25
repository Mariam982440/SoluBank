package com.formation.banque.Service;


import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    Transaction ajouterTransaction(
            double montant,
            TypeTransaction type,
            String lieu,
            String idCompte
    );

    List<Transaction> listerTransactions();

    List<Transaction> transactionsParCompte(String idCompte);

    List<Transaction> filtrerParType(TypeTransaction type);

    List<Transaction> filtrerParMontant(double montantMinimum);

    List<Transaction> filtrerParDate(LocalDate date);

    List<Transaction> filtrerParLieu(String lieu);
    List<Transaction> transactionsParClient(String idClient);

    Map<TypeTransaction, List<Transaction>> grouperParType();

    double montantTotal();

    double montantMoyen();

    List<Transaction> detecterTransactionsSuspectes(double seuil);

    void supprimerTransaction(String id);
}
