package org.example.domain.client;

import org.example.connectionUtil.DbManager;
import org.example.domain.GenericDao;
import org.example.exceptions.DBComunicationException;
import org.example.exceptions.MultipleOrNoEntityException;
import org.example.exceptions.NoTheSameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLoader implements GenericDao<Client> {
    private final DbManager dbManager;

    public ClientLoader(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Client> getAll() throws DBComunicationException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT idClient, nume, prenume, email, telefon, userName, parola FROM clients";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clients.add(createClient(resultSet));
            }
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error", e);
        }
        return clients;
    }

    private Client createClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("idClient"),
                resultSet.getString("nume"),
                resultSet.getString("prenume"),
                resultSet.getString("email"),
                resultSet.getString("telefon"),
                resultSet.getString("userName"),
                resultSet.getString("parola")
        );
    }

    @Override
    public List<Client> findByName(String name) throws DBComunicationException {
        List<Client> foundClients = new ArrayList<>();
        String query = "SELECT idClient, nume, prenume, email, telefon, userName, parola FROM clients WHERE nume = ?";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundClients.add(createClient(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error", e);
        }
        return foundClients;
    }

    @Override
    public Client saveObject(Client client) throws DBComunicationException, MultipleOrNoEntityException {
        String insertQuery = "INSERT INTO clients (nume, prenume, email, telefon, userName, parola) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, client.getNume());
            statement.setString(2, client.getPrenume());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getTelefon());
            statement.setString(5, client.getUserName());
            statement.setString(6, client.getParola());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DBComunicationException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client = new Client(
                            generatedKeys.getInt(1),
                            client.getNume(),
                            client.getPrenume(),
                            client.getEmail(),
                            client.getTelefon(),
                            client.getUserName(),
                            client.getParola()
                    );
                    return client;
                } else {
                    throw new DBComunicationException("Creating client failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error", e);
        }
    }

    @Override
    public Client updateSingleObject(Client oldClient, Client newClient) throws DBComunicationException {
        String updateQuery = "UPDATE clients SET nume = ?, prenume = ?, email = ?, telefon = ?, userName = ?, parola = ? WHERE idClient = ?";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, newClient.getNume());
            statement.setString(2, newClient.getPrenume());
            statement.setString(3, newClient.getEmail());
            statement.setString(4, newClient.getTelefon());
            statement.setString(5, newClient.getUserName());
            statement.setString(6, newClient.getParola());
            statement.setInt(7, oldClient.getIdClient());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DBComunicationException("Updating client failed, no rows affected.");
            }
            return newClient;
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error", e);
        }
    }

    @Override
    public Client deleteSingleObjectFromDb(Client client) throws DBComunicationException, NoTheSameException {
        String deleteQuery = "DELETE FROM clients WHERE idClient = ?";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, client.getIdClient());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DBComunicationException("Deleting client failed, no rows affected.");
            }
            return client;
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error", e);
        }
    }
}
