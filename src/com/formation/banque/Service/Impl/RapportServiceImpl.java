package com.formation.banque.Service.Impl;


import com.formation.banque.DAO.ClientDAO;
import com.formation.banque.DAO.CompteDAO;
import com.formation.banque.DAO.TransactionDAO;
import com.formation.banque.Entity.Client;
import com.formation.banque.Entity.Compte;
import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;
import com.formation.banque.Service.RapportService;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RapportServiceImpl implements RapportService {

    private final ClientDAO clientDAO;
    private final CompteDAO compteDAO;
    private final TransactionDAO transactionDAO;

    public RapportServiceImpl(
            ClientDAO clientDAO,
            CompteDAO compteDAO,
            TransactionDAO transactionDAO
    ) {
        this.clientDAO = clientDAO;
        this.compteDAO = compteDAO;
        this.transactionDAO = transactionDAO;
    }




    @Override
    public List<Client> top5ClientsParSolde() {

        return clientDAO.findAll()
                .stream()

                .sorted(
                        Comparator.comparingDouble(
                                this::soldeTotalClient
                        ).reversed()
                )

                .limit(5)

                .toList();
    }



    @Override
    public Map<TypeTransaction, Long>
    nombreTransactionsParType(YearMonth mois) {

        return transactionDAO.findAll()
                .stream()

                .filter(transaction ->
                        YearMonth.from(transaction.date())
                                .equals(mois)
                )

                .collect(
                        Collectors.groupingBy(
                                Transaction::type,
                                Collectors.counting()
                        )
                );
    }



    @Override
    public double volumeTotalMensuel(YearMonth mois) {

        return transactionDAO.findAll()
                .stream()

                .filter(transaction ->
                        YearMonth.from(transaction.date())
                                .equals(mois)
                )

                .mapToDouble(Transaction::montant)

                .sum();
    }



    @Override
    public List<Transaction> transactionsMontantEleve(
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
    public List<String> comptesInactifs(int nombreMois) {

        LocalDateTime limite =
                LocalDateTime.now()
                        .minusMonths(nombreMois);

        return compteDAO.findAll()
                .stream()

                .filter(compte -> {

                    List<Transaction> transactions =
                            transactionDAO.findByCompte(
                                    compte.getId()
                            );

                    // aucune transaction sgnifie inactif
                    if (transactions.isEmpty()) {
                        return true;
                    }

                    // dernière transaction
                    LocalDateTime derniereDate =
                            transactions.stream()
                                    .map(Transaction::date)
                                    .max(LocalDateTime::compareTo)
                                    .orElse(LocalDateTime.MIN);

                    return derniereDate.isBefore(limite);
                })

                .map(Compte::getNumero)

                .toList();
    }



    @Override
    public List<Transaction>
    transactionsFrequenceAnormale() {

        return compteDAO.findAll()
                .stream()

                .flatMap(compte -> {

                    List<Transaction> transactions =
                            transactionDAO
                                    .findByCompte(compte.getId())
                                    .stream()
                                    .sorted(
                                            Comparator.comparing(
                                                    Transaction::date
                                            )
                                    )
                                    .toList();

                    return transactions.stream()
                            .filter(transaction -> {

                                int index =
                                        transactions.indexOf(
                                                transaction
                                        );

                                if (index == 0) {
                                    return false;
                                }

                                Transaction precedente =
                                        transactions.get(index - 1);

                                long secondes =
                                        ChronoUnit.SECONDS.between(
                                                precedente.date(),
                                                transaction.date()
                                        );

                                return secondes < 60;
                            });
                })

                .toList();
    }

    @Override
    public List<Transaction> transactionsLieuInhabituel() {

        return compteDAO.findAll()
                .stream()

                .flatMap(compte -> {
                    List<Transaction> transactions =
                            transactionDAO.findByCompte(
                                    compte.getId()
                            );

                    Map<String, Long> nombreParLieu =
                            transactions.stream()
                                    .collect(
                                            Collectors.groupingBy(
                                                    transaction ->
                                                            transaction
                                                                    .lieu()
                                                                    .toLowerCase(),

                                                    Collectors.counting()
                                            )
                                    );

                    return transactions.stream()
                            .filter(transaction ->

                                    nombreParLieu.getOrDefault(
                                            transaction
                                                    .lieu()
                                                    .toLowerCase(),

                                            0L
                                    ) == 1
                            );
                })

                .toList();
    }

    private double soldeTotalClient(Client client) {

        return compteDAO
                .findByClient(client.id())
                .stream()
                .mapToDouble(Compte::getSolde)
                .sum();
    }
}
