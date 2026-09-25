package com.formation.banque.Service.Impl;


import com.formation.banque.DAO.ClientDAO;
import com.formation.banque.Entity.Client;
import com.formation.banque.Service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Client ajouterClient(String nom, String email) {

        String id = UUID.randomUUID().toString();

        Client client = new Client(
                id,
                nom,
                email
        );

        clientDAO.create(client);

        return client;
    }

    @Override
    public Optional<Client> rechercherParId(String id) {
        return clientDAO.findById(id);
    }

    @Override
    public List<Client> listerClients() {
        return clientDAO.findAll();
    }

    @Override
    public void modifierClient(
            String id,
            String nom,
            String email
    ) {

        Client client = clientDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable")
                );

        Client clientModifie = new Client(
                client.id(),
                nom,
                email
        );

        clientDAO.update(clientModifie);
    }

    @Override
    public void supprimerClient(String id) {

        Client client = clientDAO.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable")
                );

        clientDAO.delete(client.id());
    }
}