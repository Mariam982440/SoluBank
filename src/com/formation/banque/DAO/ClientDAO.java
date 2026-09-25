package com.formation.banque.DAO;

import com.formation.banque.Entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {

    void create(Client client);

    Optional<Client> findById(String id);

    List<Client> findAll();

    void update(Client client);

    void delete(String id);
}