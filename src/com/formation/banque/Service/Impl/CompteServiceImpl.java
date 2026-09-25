package com.formation.banque.Service.Impl;



import com.formation.banque.DAO.ClientDAO;
import com.formation.banque.DAO.CompteDAO;
import com.formation.banque.Entity.Compte;
import com.formation.banque.Entity.CompteCourant;
import com.formation.banque.Entity.CompteEpargne;
import com.formation.banque.Service.CompteService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CompteServiceImpl implements CompteService {

    private final CompteDAO compteDAO;
    private final ClientDAO clientDAO;

    public CompteServiceImpl(
            CompteDAO compteDAO,
            ClientDAO clientDAO
    ) {
        this.compteDAO = compteDAO;
        this.clientDAO = clientDAO;
    }


    @Override
    public CompteCourant creerCompteCourant(
            String numero,
            double solde,
            String idClient,
            double decouvertAutorise
    ) {

        clientDAO.findById(idClient)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable")
                );

        String id = UUID.randomUUID().toString();

        CompteCourant compte = new CompteCourant(
                id,
                numero,
                solde,
                idClient,
                decouvertAutorise
        );

        compteDAO.create(compte);

        return compte;
    }


    @Override
    public CompteEpargne creerCompteEpargne(
            String numero,
            double solde,
            String idClient,
            double tauxInteret
    ) {

        clientDAO.findById(idClient)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable")
                );

        String id = UUID.randomUUID().toString();

        CompteEpargne compte = new CompteEpargne(
                id,
                numero,
                solde,
                idClient,
                tauxInteret
        );

        compteDAO.create(compte);

        return compte;
    }



    @Override
    public Optional<Compte> rechercherParNumero(String numero) {
        return compteDAO.findByNumero(numero);
    }


    @Override
    public List<Compte> rechercherParClient(String idClient) {
        return compteDAO.findByClient(idClient);
    }


    @Override
    public List<Compte> listerComptes() {
        return compteDAO.findAll();
    }


    @Override
    public void modifierSolde(
            String idCompte,
            double nouveauSolde
    ) {

        Compte compte = compteDAO.findById(idCompte)
                .orElseThrow(() ->
                        new RuntimeException("Compte introuvable")
                );

        compte.setSolde(nouveauSolde);

        compteDAO.update(compte);
    }



    @Override
    public Optional<Compte> compteSoldeMaximum() {

        return compteDAO.findAll()
                .stream()
                .max(
                        Comparator.comparingDouble(
                                Compte::getSolde
                        )
                );
    }



    @Override
    public Optional<Compte> compteSoldeMinimum() {

        return compteDAO.findAll()
                .stream()
                .min(
                        Comparator.comparingDouble(
                                Compte::getSolde
                        )
                );
    }


    @Override
    public void supprimerCompte(String id) {

        compteDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Compte introuvable")
                );

        compteDAO.delete(id);
    }
}