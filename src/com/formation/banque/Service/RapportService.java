package com.formation.banque.Service;



import com.formation.banque.Entity.Client;
import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface RapportService {

    List<Client> top5ClientsParSolde();

    Map<TypeTransaction, Long> nombreTransactionsParType(
            YearMonth mois
    );

    double volumeTotalMensuel(YearMonth mois);

    List<Transaction> transactionsMontantEleve(double seuil);

    List<String> comptesInactifs(int nombreMois);

    List<Transaction> transactionsFrequenceAnormale();
    List<Transaction> transactionsLieuInhabituel();
}
