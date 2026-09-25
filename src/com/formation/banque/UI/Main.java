package com.formation.banque.UI;


import com.formation.banque.DAO.*;
import com.formation.banque.DAO.impl.ClientDAOImpl;
import com.formation.banque.DAO.impl.CompteDAOImpl;
import com.formation.banque.DAO.impl.TransactionDAOImpl;
import com.formation.banque.Service.*;
import com.formation.banque.Service.Impl.ClientServiceImpl;
import com.formation.banque.Service.Impl.CompteServiceImpl;
import com.formation.banque.Service.Impl.RapportServiceImpl;
import com.formation.banque.Service.Impl.TransactionServiceImpl;
import com.formation.banque.UI.Menu;

public class Main {

    public static void main(String[] args) {


        ClientDAO clientDAO =
                new ClientDAOImpl();

        CompteDAO compteDAO =
                new CompteDAOImpl();

        TransactionDAO transactionDAO =
                new TransactionDAOImpl();


        ClientService clientService =
                new ClientServiceImpl(
                        clientDAO
                );

        CompteService compteService =
                new CompteServiceImpl(
                        compteDAO,
                        clientDAO
                );

        TransactionService transactionService =
                new TransactionServiceImpl(
                        transactionDAO,
                        compteDAO
                );

        RapportService rapportService =
                new RapportServiceImpl(
                        clientDAO,
                        compteDAO,
                        transactionDAO
                );



        Menu menu = new Menu(
                clientService,
                compteService,
                transactionService,
                rapportService
        );

        menu.demarrer();
    }
}