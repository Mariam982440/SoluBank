package com.formation.banque.Service;


import com.formation.banque.Entity.Compte;
import com.formation.banque.Entity.CompteCourant;
import com.formation.banque.Entity.CompteEpargne;

import java.util.List;
import java.util.Optional;

public interface CompteService {

    CompteCourant creerCompteCourant(
            String numero,
            double solde,
            String idClient,
            double decouvertAutorise
    );

    CompteEpargne creerCompteEpargne(
            String numero,
            double solde,
            String idClient,
            double tauxInteret
    );

    Optional<Compte> rechercherParNumero(String numero);

    List<Compte> rechercherParClient(String idClient);

    List<Compte> listerComptes();

    void modifierSolde(String idCompte, double nouveauSolde);

    Optional<Compte> compteSoldeMaximum();

    Optional<Compte> compteSoldeMinimum();

    void supprimerCompte(String id);
}