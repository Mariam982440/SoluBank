package com.formation.banque.UI;

import com.formation.banque.Service.*;

import com.formation.banque.Entity.Client;
import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;
import com.formation.banque.Service.ClientService;
import com.formation.banque.Service.CompteService;
import com.formation.banque.Service.RapportService;
import com.formation.banque.Service.TransactionService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class Menu {

    private final ClientService clientService;
    private final CompteService compteService;
    private final TransactionService transactionService;
    private final RapportService rapportService;

    private final Scanner scanner = new Scanner(System.in);


    public Menu(
            ClientService clientService,
            CompteService compteService,
            TransactionService transactionService,
            RapportService rapportService
    ) {
        this.clientService = clientService;
        this.compteService = compteService;
        this.transactionService = transactionService;
        this.rapportService = rapportService;
    }



    public void demarrer() {

        int choix;

        do {

            afficherMenuPrincipal();

            try {

                System.out.print("Votre choix : ");
                choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {

                    case 1 -> menuClients();

                    case 2 -> menuComptes();

                    case 3 -> menuTransactions();

                    case 4 -> menuRapports();

                    case 0 ->
                            System.out.println("Au revoir !");

                    default ->
                            System.out.println("Choix invalide.");
                }

            } catch (Exception e) {

                System.out.println(
                        "Erreur : " + e.getMessage()
                );

                choix = -1;
            }

        } while (choix != 0);
    }


    private void afficherMenuPrincipal() {

        System.out.println("""
                
                ===== SOLUBANK =====
                
                1. Gestion clients
                2. Gestion comptes
                3. Gestion transactions
                4. Rapports et analyses
                0. Quitter
                
                """);
    }

    private void menuClients() {

        int choix;

        do {

            System.out.println("""
                    
                    ===== GESTION CLIENTS =====
                    
                    1. Ajouter client
                    2. Lister clients
                    3. Rechercher client par ID
                    4. Modifier client
                    5. Supprimer client
                    0. Retour
                    
                    """);

            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            try {

                switch (choix) {

                    case 1 -> ajouterClient();

                    case 2 -> listerClients();

                    case 3 -> rechercherClient();

                    case 4 -> modifierClient();

                    case 5 -> supprimerClient();

                    case 0 -> {
                    }

                    default ->
                            System.out.println("Choix invalide.");
                }

            } catch (Exception e) {
                System.out.println(
                        "Erreur : " + e.getMessage()
                );
            }

        } while (choix != 0);
    }


    private void ajouterClient() {

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        Client client =
                clientService.ajouterClient(
                        nom,
                        email
                );

        System.out.println(
                "Client créé avec succès :"
        );

        System.out.println(client);
    }


    private void listerClients() {

        var clients =
                clientService.listerClients();

        if (clients.isEmpty()) {

            System.out.println(
                    "Aucun client."
            );

            return;
        }

        clients.forEach(
                System.out::println
        );
    }


    private void rechercherClient() {

        System.out.print("ID client : ");
        String id = scanner.nextLine();

        clientService
                .rechercherParId(id)
                .ifPresentOrElse(

                        System.out::println,

                        () ->
                                System.out.println(
                                        "Client introuvable."
                                )
                );
    }


    private void modifierClient() {

        System.out.print("ID client : ");
        String id = scanner.nextLine();

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();

        System.out.print("Nouvel email : ");
        String email = scanner.nextLine();

        clientService.modifierClient(
                id,
                nom,
                email
        );

        System.out.println(
                "Client modifié avec succès."
        );
    }


    private void supprimerClient() {

        System.out.print("ID client : ");
        String id = scanner.nextLine();

        clientService.supprimerClient(id);

        System.out.println(
                "Client supprimé avec succès."
        );
    }




    // COMPTES


    private void menuComptes() {

        int choix;

        do {

            System.out.println("""
                    
                    ===== GESTION COMPTES =====
                    
                    1. Créer compte courant
                    2. Créer compte épargne
                    3. Lister comptes
                    4. Rechercher par numéro
                    5. Rechercher comptes d'un client
                    6. Modifier solde
                    7. Compte avec solde maximum
                    8. Compte avec solde minimum
                    9. Supprimer compte
                    0. Retour
                    
                    """);

            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            try {

                switch (choix) {

                    case 1 -> creerCompteCourant();

                    case 2 -> creerCompteEpargne();

                    case 3 -> listerComptes();

                    case 4 -> rechercherCompteNumero();

                    case 5 -> rechercherComptesClient();

                    case 6 -> modifierSolde();

                    case 7 -> afficherSoldeMaximum();

                    case 8 -> afficherSoldeMinimum();

                    case 9 -> supprimerCompte();

                    case 0 -> {
                    }

                    default ->
                            System.out.println(
                                    "Choix invalide."
                            );
                }

            } catch (Exception e) {

                System.out.println(
                        "Erreur : " + e.getMessage()
                );
            }

        } while (choix != 0);
    }


    private void creerCompteCourant() {

        System.out.print("Numéro compte : ");
        String numero = scanner.nextLine();

        System.out.print("Solde initial : ");
        double solde =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.print("ID client : ");
        String idClient = scanner.nextLine();

        System.out.print(
                "Découvert autorisé : "
        );

        double decouvert =
                Double.parseDouble(
                        scanner.nextLine()
                );

        var compte =
                compteService.creerCompteCourant(
                        numero,
                        solde,
                        idClient,
                        decouvert
                );

        System.out.println(
                "Compte courant créé :"
        );

        System.out.println(compte);
    }


    private void creerCompteEpargne() {

        System.out.print("Numéro compte : ");
        String numero = scanner.nextLine();

        System.out.print("Solde initial : ");
        double solde =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.print("ID client : ");
        String idClient = scanner.nextLine();

        System.out.print(
                "Taux d'intérêt : "
        );

        double taux =
                Double.parseDouble(
                        scanner.nextLine()
                );

        var compte =
                compteService.creerCompteEpargne(
                        numero,
                        solde,
                        idClient,
                        taux
                );

        System.out.println(
                "Compte épargne créé :"
        );

        System.out.println(compte);
    }


    private void listerComptes() {

        var comptes =
                compteService.listerComptes();

        if (comptes.isEmpty()) {

            System.out.println(
                    "Aucun compte."
            );

            return;
        }

        comptes.forEach(
                System.out::println
        );
    }


    private void rechercherCompteNumero() {

        System.out.print(
                "Numéro du compte : "
        );

        String numero =
                scanner.nextLine();

        compteService
                .rechercherParNumero(numero)
                .ifPresentOrElse(

                        System.out::println,

                        () ->
                                System.out.println(
                                        "Compte introuvable."
                                )
                );
    }


    private void rechercherComptesClient() {

        System.out.print("ID client : ");
        String idClient =
                scanner.nextLine();

        var comptes =
                compteService
                        .rechercherParClient(
                                idClient
                        );

        if (comptes.isEmpty()) {

            System.out.println(
                    "Aucun compte pour ce client."
            );

            return;
        }

        comptes.forEach(
                System.out::println
        );
    }


    private void modifierSolde() {

        System.out.print("ID compte : ");
        String id =
                scanner.nextLine();

        System.out.print(
                "Nouveau solde : "
        );

        double solde =
                Double.parseDouble(
                        scanner.nextLine()
                );

        compteService.modifierSolde(
                id,
                solde
        );

        System.out.println(
                "Solde modifié avec succès."
        );
    }


    private void afficherSoldeMaximum() {

        compteService
                .compteSoldeMaximum()
                .ifPresentOrElse(

                        compte ->
                                System.out.println(
                                        "Compte avec solde maximum : "
                                                + compte
                                ),

                        () ->
                                System.out.println(
                                        "Aucun compte."
                                )
                );
    }


    private void afficherSoldeMinimum() {

        compteService
                .compteSoldeMinimum()
                .ifPresentOrElse(

                        compte ->
                                System.out.println(
                                        "Compte avec solde minimum : "
                                                + compte
                                ),

                        () ->
                                System.out.println(
                                        "Aucun compte."
                                )
                );
    }


    private void supprimerCompte() {

        System.out.print("ID compte : ");
        String id =
                scanner.nextLine();

        compteService.supprimerCompte(id);

        System.out.println(
                "Compte supprimé avec succès."
        );
    }



    // TRANSACTIONS


    private void menuTransactions() {

        int choix;

        do {

            System.out.println("""
                    
                    ===== GESTION TRANSACTIONS =====
                    
                    1. Ajouter transaction
                    2. Lister transactions
                    3. Transactions d'un compte
                    4. Filtrer par type
                    5. Filtrer par montant minimum
                    6. Filtrer par date
                    7. Filtrer par lieu
                    8. Grouper par type
                    9. Montant total
                    10. Montant moyen
                    11. Transactions suspectes
                    12. Supprimer transaction
                    13. Transactions d'un client
                    0. Retour
                    
                    """);

            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            try {

                switch (choix) {

                    case 1 -> ajouterTransaction();

                    case 2 ->
                            transactionService
                                    .listerTransactions()
                                    .forEach(
                                            System.out::println
                                    );

                    case 3 ->
                            transactionsCompte();

                    case 4 ->
                            filtrerTransactionType();

                    case 5 ->
                            filtrerTransactionMontant();

                    case 6 ->
                            filtrerTransactionDate();

                    case 7 ->
                            filtrerTransactionLieu();

                    case 8 ->
                            System.out.println(
                                    transactionService
                                            .grouperParType()
                            );

                    case 9 ->
                            System.out.println(
                                    "Montant total : "
                                            + transactionService
                                            .montantTotal()
                            );

                    case 10 ->
                            System.out.println(
                                    "Montant moyen : "
                                            + transactionService
                                            .montantMoyen()
                            );

                    case 11 ->
                            transactionsSuspectes();

                    case 12 ->
                            supprimerTransaction();
                    case 13 -> transactionsClient();

                    case 0 -> {
                    }

                    default ->
                            System.out.println(
                                    "Choix invalide."
                            );
                }

            } catch (Exception e) {

                System.out.println(
                        "Erreur : " + e.getMessage()
                );
            }

        } while (choix != 0);
    }

    private void transactionsClient() {

        System.out.print("ID client : ");
        String idClient = scanner.nextLine();

        var transactions =
                transactionService
                        .transactionsParClient(idClient);

        if (transactions.isEmpty()) {

            System.out.println(
                    "Aucune transaction pour ce client."
            );

            return;
        }

        transactions.forEach(
                System.out::println
        );
    }

    private void ajouterTransaction() {

        System.out.print("ID compte : ");
        String idCompte =
                scanner.nextLine();

        System.out.print("Montant : ");
        double montant =
                Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println("""
                Type :
                1. VERSEMENT
                2. RETRAIT
                3. VIREMENT
                """);

        System.out.print("Choix : ");

        int choixType =
                Integer.parseInt(
                        scanner.nextLine()
                );

        TypeTransaction type =
                switch (choixType) {

                    case 1 ->
                            TypeTransaction.VERSEMENT;

                    case 2 ->
                            TypeTransaction.RETRAIT;

                    case 3 ->
                            TypeTransaction.VIREMENT;

                    default ->
                            throw new IllegalArgumentException(
                                    "Type invalide."
                            );
                };

        System.out.print("Lieu : ");
        String lieu =
                scanner.nextLine();

        Transaction transaction =
                transactionService
                        .ajouterTransaction(
                                montant,
                                type,
                                lieu,
                                idCompte
                        );

        System.out.println(
                "Transaction ajoutée :"
        );

        System.out.println(transaction);
    }


    private void transactionsCompte() {

        System.out.print("ID compte : ");

        String idCompte =
                scanner.nextLine();

        transactionService
                .transactionsParCompte(
                        idCompte
                )
                .forEach(
                        System.out::println
                );
    }


    private void filtrerTransactionType() {

        System.out.print(
                "Type (VERSEMENT/RETRAIT/VIREMENT) : "
        );

        TypeTransaction type =
                TypeTransaction.valueOf(
                        scanner.nextLine()
                                .toUpperCase()
                );

        transactionService
                .filtrerParType(type)
                .forEach(
                        System.out::println
                );
    }


    private void filtrerTransactionMontant() {

        System.out.print(
                "Montant minimum : "
        );

        double montant =
                Double.parseDouble(
                        scanner.nextLine()
                );

        transactionService
                .filtrerParMontant(montant)
                .forEach(
                        System.out::println
                );
    }


    private void filtrerTransactionDate() {

        System.out.print(
                "Date (AAAA-MM-JJ) : "
        );

        LocalDate date =
                LocalDate.parse(
                        scanner.nextLine()
                );

        transactionService
                .filtrerParDate(date)
                .forEach(
                        System.out::println
                );
    }


    private void filtrerTransactionLieu() {

        System.out.print("Lieu : ");
        String lieu =
                scanner.nextLine();

        transactionService
                .filtrerParLieu(lieu)
                .forEach(
                        System.out::println
                );
    }


    private void transactionsSuspectes() {

        System.out.print(
                "Seuil de détection : "
        );

        double seuil =
                Double.parseDouble(
                        scanner.nextLine()
                );

        transactionService
                .detecterTransactionsSuspectes(
                        seuil
                )
                .forEach(
                        System.out::println
                );
    }


    private void supprimerTransaction() {

        System.out.print(
                "ID transaction : "
        );

        String id =
                scanner.nextLine();

        transactionService
                .supprimerTransaction(id);

        System.out.println(
                "Transaction supprimée."
        );
    }


    // RAPPORTS

    private void menuRapports() {

        int choix;

        do {

            System.out.println("""
                    
                    ===== RAPPORTS ET ANALYSES =====
                    
                    1. Top 5 clients par solde
                    2. Nombre transactions par type / mois
                    3. Volume total mensuel
                    4. Transactions à montant élevé
                    5. Comptes inactifs
                    6. Fréquence anormale
                    7. Lieux inhabituels
                    0. Retour
                    
                    """);

            System.out.print("Choix : ");
            choix =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

            try {

                switch (choix) {

                    case 1 ->
                            rapportService
                                    .top5ClientsParSolde()
                                    .forEach(
                                            System.out::println
                                    );

                    case 2 ->
                            rapportTransactionsParType();

                    case 3 ->
                            rapportVolumeMensuel();

                    case 4 ->
                            rapportMontantEleve();

                    case 5 ->
                            rapportComptesInactifs();

                    case 6 ->
                            rapportService
                                    .transactionsFrequenceAnormale()
                                    .forEach(
                                            System.out::println
                                    );
                    case 7 ->
                            rapportService
                                    .transactionsLieuInhabituel()
                                    .forEach(System.out::println);

                    case 0 -> {
                    }

                    default ->
                            System.out.println(
                                    "Choix invalide."
                            );
                }

            } catch (Exception e) {

                System.out.println(
                        "Erreur : " + e.getMessage()
                );
            }

        } while (choix != 0);
    }


    private void rapportTransactionsParType() {

        System.out.print(
                "Mois (AAAA-MM) : "
        );

        YearMonth mois =
                YearMonth.parse(
                        scanner.nextLine()
                );

        var rapport =
                rapportService
                        .nombreTransactionsParType(
                                mois
                        );

        rapport.forEach(
                (type, nombre) ->
                        System.out.println(
                                type + " : " + nombre
                        )
        );
    }


    private void rapportVolumeMensuel() {

        System.out.print(
                "Mois (AAAA-MM) : "
        );

        YearMonth mois =
                YearMonth.parse(
                        scanner.nextLine()
                );

        double total =
                rapportService
                        .volumeTotalMensuel(
                                mois
                        );

        System.out.println(
                "Volume total : " + total
        );
    }


    private void rapportMontantEleve() {

        System.out.print("Seuil : ");

        double seuil =
                Double.parseDouble(
                        scanner.nextLine()
                );

        rapportService
                .transactionsMontantEleve(
                        seuil
                )
                .forEach(
                        System.out::println
                );
    }


    private void rapportComptesInactifs() {

        System.out.print(
                "Nombre de mois d'inactivité : "
        );

        int mois =
                Integer.parseInt(
                        scanner.nextLine()
                );

        rapportService
                .comptesInactifs(mois)
                .forEach(numero ->
                        System.out.println(
                                "Compte inactif : "
                                        + numero
                        )
                );
    }




}