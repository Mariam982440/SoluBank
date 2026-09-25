package com.formation.banque.DAO.impl;



import com.formation.banque.DAO.TransactionDAO;
import com.formation.banque.Entity.Transaction;
import com.formation.banque.Entity.TypeTransaction;
import com.formation.banque.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAOImpl implements TransactionDAO {



    @Override
    public void create(Transaction transaction) {

        String sql = """
                INSERT INTO transaction_bancaire
                (id, date_transaction, montant, type, lieu, id_compte)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, transaction.id());

            statement.setTimestamp(
                    2,
                    Timestamp.valueOf(transaction.date())
            );

            statement.setDouble(3, transaction.montant());

            statement.setString(
                    4,
                    transaction.type().name()
            );

            statement.setString(5, transaction.lieu());
            statement.setString(6, transaction.idCompte());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la création de la transaction",
                    e
            );
        }
    }


    @Override
    public Optional<Transaction> findById(String id) {

        String sql =
                "SELECT * FROM transaction_bancaire WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapTransaction(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la recherche de la transaction",
                    e
            );
        }
    }



    @Override
    public List<Transaction> findByCompte(String idCompte) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT *
                FROM transaction_bancaire
                WHERE id_compte = ?
                """;

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, idCompte);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    transactions.add(mapTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la recherche des transactions du compte",
                    e
            );
        }

        return transactions;
    }



    @Override
    public List<Transaction> findAll() {

        List<Transaction> transactions = new ArrayList<>();

        String sql =
                "SELECT * FROM transaction_bancaire";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()
        ) {

            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des transactions",
                    e
            );
        }

        return transactions;
    }



    @Override
    public void update(Transaction transaction) {

        String sql = """
                UPDATE transaction_bancaire
                SET date_transaction = ?,
                    montant = ?,
                    type = ?,
                    lieu = ?,
                    id_compte = ?
                WHERE id = ?
                """;

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setTimestamp(1,Timestamp.valueOf(transaction.date()));

            statement.setDouble(2, transaction.montant());

            statement.setString(3,transaction.type().name());

            statement.setString(4, transaction.lieu());
            statement.setString(5, transaction.idCompte());
            statement.setString(6, transaction.id());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la modification de la transaction",
                    e
            );
        }
    }



    @Override
    public void delete(String id) {

        String sql =
                "DELETE FROM transaction_bancaire WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la suppression de la transaction",
                    e
            );
        }
    }



    private Transaction mapTransaction(ResultSet rs)
            throws SQLException {

        return new Transaction(

                rs.getString("id"),

                rs.getTimestamp("date_transaction")
                        .toLocalDateTime(),

                rs.getDouble("montant"),

                TypeTransaction.valueOf(
                        rs.getString("type")
                ),

                rs.getString("lieu"),

                rs.getString("id_compte")
        );
    }
}
