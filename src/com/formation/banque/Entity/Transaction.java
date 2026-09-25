package com.formation.banque.Entity;

import java.time.LocalDateTime;

public record Transaction(
        String id,
        LocalDateTime date,
        double montant,
        TypeTransaction type,
        String lieu,
        String idCompte
) {}