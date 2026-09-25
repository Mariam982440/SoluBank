package com.formation.banque.DAO;

import com.formation.banque.Entity.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteDAO {

    void create(Compte compte);

    Optional<Compte> findById(String id);

    Optional<Compte> findByNumero(String numero);

    List<Compte> findByClient(String idClient);

    List<Compte> findAll();

    void update(Compte compte);

    void delete(String id);
}