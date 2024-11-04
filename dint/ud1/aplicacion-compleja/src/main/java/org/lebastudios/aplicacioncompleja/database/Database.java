package org.lebastudios.aplicacioncompleja.database;

import lombok.SneakyThrows;
import org.lebastudios.aplicacioncompleja.events.AppLifeCicleEvents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class Database
{
    private static final int MIN_CONNECTIONS = 2;

    private static Database instance;
    private static String dbURL; ;
    private static String connectionUser;
    private static String connectionPassword;

    public static Database getInstance()
    {
        if (instance == null) throw new IllegalStateException("DB not initialized");

        return instance;
    }

    public static void init(String user, String password, String ip, String puerto)
    {
        dbURL = "jdbc:mysql://" + ip + ":" + puerto + "/clinica"; // Cambia el nombre de tu BD
        connectionUser = user; // Usuario de MySQL
        connectionPassword = password; // Contraseña de MySQL
        
        instance = new Database();
    }

    private Connection generateDBConnection() throws SQLException
    {
        return DriverManager.getConnection(dbURL, connectionUser, connectionPassword);
    }

    private final Stack<Connection> connections = new Stack<>();
    private final List<Connection> createdConnections = new ArrayList<>(MIN_CONNECTIONS);

    private Database()
    {
        try
        {
            for (int i = 0; i < MIN_CONNECTIONS; i++)
            {
                final var connection = generateDBConnection();

                connections.push(connection);
                createdConnections.add(connection);
            }
        }
        catch (SQLException e)
        {
            connections.clear();
            createdConnections.clear();
            throw new RuntimeException(e);
        }

        AppLifeCicleEvents.OnAppCloseRequest.addListener(() -> createdConnections.forEach(connection ->
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }));
    }

    public void connectTransaction(Consumer<Connection> consumer)
    {
        // TODO
    }

    @SneakyThrows
    public void connect(Consumer<Connection> consumer)
    {
        Connection connection = getAvailableConnection();

        consumer.accept(connection);

        connections.add(connection);
    }

    @SneakyThrows
    private Connection getAvailableConnection()
    {
        Connection connection;

        if (connections.isEmpty())
        {
            connection = generateDBConnection();
            createdConnections.add(connection);
        }
        else
        {
            connection = connections.pop();
        }

        return connection;
    }
}
