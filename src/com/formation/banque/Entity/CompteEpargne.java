package com.formation.banque.Entity;

public final class CompteEpargne extends Compte {

    private double tauxInteret;

    public CompteEpargne(
            String id,
            String numero,
            double solde,
            String idClient,
            double tauxInteret) {

        super(id, numero, solde, idClient);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
}
