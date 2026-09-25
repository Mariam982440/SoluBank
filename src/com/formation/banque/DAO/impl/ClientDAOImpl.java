package com.formation.banque.DAO.impl;

import com.formation.banque.DAO.ClientDAO;
import com.formation.banque.Entity.Client;
import com.formation.banque.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public void create(Client client) {

        String sql =
                "INSERT INTO client (id, nom, email) VALUES (?, ?, ?)";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, client.id());
            statement.setString(2, client.nom());
            statement.setString(3, client.email());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> findById(String id) {

        String sql = "SELECT * FROM client WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Client client = new Client(
                        result.getString("id"),
                        result.getString("nom"),
                        result.getString("email")
                );

                return Optional.of(client);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> findAll() {

        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM client";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                clients.add(new Client(
                        result.getString("id"),
                        result.getString("nom"),
                        result.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

    @Override
    public void update(Client client) {

        String sql =
                "UPDATE client SET nom = ?, email = ? WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, client.nom());
            statement.setString(2, client.email());
            statement.setString(3, client.id());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {

        String sql = "DELETE FROM client WHERE id = ?";

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}