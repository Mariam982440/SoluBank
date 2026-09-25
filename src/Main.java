import com.formation.banque.DAO.ClientDAO;
import com.formation.banque.DAO.impl.ClientDAOImpl;
import com.formation.banque.Entity.Client;
import com.formation.banque.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try (Connection connection = DataBaseConnection.getConnection()){
            System.out.println("connexion réussite");
        } catch (SQLException e){
            System.out.println("erreur de connexion : " + e.getMessage());
        }

        ClientDAO clientDAO = new ClientDAOImpl();

        Client client = new Client(
                UUID.randomUUID().toString(),
                "Mariam",
                "mariam@test.com"
        );

        clientDAO.create(client);

        clientDAO.findAll()
                .forEach(System.out::println);


    }
}