package com.formation.banque.Entity;


public sealed abstract class Compte
        permits CompteCourant, CompteEpargne {

    private String id;
    private String numero;
    private double solde;
    private String idClient;

    public Compte(String id, String numero, double solde, String idClient) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.idClient = idClient;
    }

    public String getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public double getSolde() {
        return solde;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}