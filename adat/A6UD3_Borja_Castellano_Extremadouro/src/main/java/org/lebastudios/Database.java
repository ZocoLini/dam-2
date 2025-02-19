package org.lebastudios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database implements AutoCloseable
{
    private static Database instance;
    
    public static Database getInstance()
    {
        if (instance == null) instance = new Database();
        
        return instance;
    }
    
    private final List<Connection> connections = new ArrayList<>();
    
    private Database() 
    {
        try
        {
            connections.add(
                    DriverManager.getConnection("jdbc:sqlserver://10.0.2.4\\MV-PROG:1433;database=BDEmpresa;" +
                            "trustServerCertificate=true", "sa", "abc123.")
            );
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public List<Connection> getConnections()
    {
        return connections;
    }
    
    @Override
    public void close()
    {
        for (Connection connection : connections)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e) {}
        }
        
        instance = null;
    }
}
