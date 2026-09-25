package com.formation.banque.Service;

import com.formation.banque.Entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client ajouterClient(String nom, String email);

    void modifierClient(String id, String nom, String email);

    void supprimerClient(String id);

    Optional<Client> rechercherParId(String id);

    List<Client> listerClients();
}