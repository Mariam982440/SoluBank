package com.formation.banque.DAO.impl;


import com.formation.banque.DAO.CompteDAO;
import com.formation.banque.Entity.Compte;
import com.formation.banque.Entity.CompteCourant;
import com.formation.banque.Entity.CompteEpargne;
import com.formation.banque.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompteDAOImpl implements CompteDAO {


    @Override
    public void create(Compte compte) {

        String sql = """
                INSERT INTO compte
                (id, numero, solde, id_client, type_compte,
                 decouvert_autorise, taux_interet)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, compte.getId());
            statement.setString(2, compte.getNumero());
            statement.setDouble(3, compte.getSolde());
            statement.setString(4, compte.getIdClient());


            if (compte instanceof CompteCourant courant) {

                statement.setString(5, "COURANT");

                statement.setDouble(
                        6,
                        courant.getDecouvertAutorise()
                );

                statement.setNull(
                        7,
                        Types.NUMERIC
                );
            }


            else if (compte instanceof CompteEpargne epargne) {

                statement.setString(5, "EPARGNE");

                statement.setNull(
                        6,
                        Types.NUMERIC
                );

                statement.setDouble(
                        7,
                        epargne.getTauxInteret()
                );
            }


            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la création du compte",
                    e
            );
        }
    }


    @Override
    public Optional<Compte> findById(String id) {

        String sql =
                "SELECT * FROM compte WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapCompte(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la recherche du compte",
                    e
            );
        }
    }



    @Override
    public Optional<Compte> findByNumero(String numero) {

        String sql =
                "SELECT * FROM compte WHERE numero = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, numero);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapCompte(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la recherche par numéro",
                    e
            );
        }
    }


    @Override
    public List<Compte> findByClient(String idClient) {

        List<Compte> comptes = new ArrayList<>();

        String sql =
                "SELECT * FROM compte WHERE id_client = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, idClient);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    comptes.add(
                            mapCompte(rs)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la recherche des comptes du client",
                    e
            );
        }

        return comptes;
    }



    @Override
    public List<Compte> findAll() {

        List<Compte> comptes = new ArrayList<>();

        String sql =
                "SELECT * FROM compte";

        try (
                Connection connection =
                        DataBaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet rs =
                        statement.executeQuery()
        ) {

            while (rs.next()) {

                comptes.add(
                        mapCompte(rs)
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la récupération des comptes",
                    e
            );
        }

        return comptes;
    }



    @Override
    public void update(Compte compte) {

        String sql = """
                UPDATE compte
                SET numero = ?,
                    solde = ?,
                    id_client = ?,
                    type_compte = ?,
                    decouvert_autorise = ?,
                    taux_interet = ?
                WHERE id = ?
                """;

        try (
                Connection connection = DataBaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    compte.getNumero()
            );

            statement.setDouble(
                    2,
                    compte.getSolde()
            );

            statement.setString(
                    3,
                    compte.getIdClient()
            );


            if (compte instanceof CompteCourant courant) {

                statement.setString(
                        4,
                        "COURANT"
                );

                statement.setDouble(
                        5,
                        courant.getDecouvertAutorise()
                );

                statement.setNull(
                        6,
                        Types.NUMERIC
                );
            }

            else if (compte instanceof CompteEpargne epargne) {

                statement.setString(
                        4,
                        "EPARGNE"
                );

                statement.setNull(
                        5,
                        Types.NUMERIC
                );

                statement.setDouble(
                        6,
                        epargne.getTauxInteret()
                );
            }


            statement.setString(
                    7,
                    compte.getId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la modification du compte",
                    e
            );
        }
    }



    @Override
    public void delete(String id) {

        String sql =
                "DELETE FROM compte WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erreur lors de la suppression du compte",
                    e
            );
        }
    }



    private Compte mapCompte(ResultSet rs)
            throws SQLException {

        String type =
                rs.getString("type_compte");

        return switch (type) {

            case "COURANT" -> new CompteCourant(

                    rs.getString("id"),

                    rs.getString("numero"),

                    rs.getDouble("solde"),

                    rs.getString("id_client"),

                    rs.getDouble("decouvert_autorise")
            );


            case "EPARGNE" -> new CompteEpargne(

                    rs.getString("id"),

                    rs.getString("numero"),

                    rs.getDouble("solde"),

                    rs.getString("id_client"),

                    rs.getDouble("taux_interet")
            );


            default -> throw new SQLException(
                    "Type de compte inconnu : " + type
            );
        };
    }
}
