package org.lebastudios.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Database
{
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;database=BDALOJAMIENTOS25;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "abc123.";
    
    private static Database instance;
    
    public static Database getInstance()
    {
        if (instance == null) instance = new Database();
        
        return instance;
    }
    
    private Database() {}

    public Connection getConnection() throws SQLException
    {
        return java.sql.DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    public boolean connectTransaction(Consumer<Connection> consumer)
    {
        Connection connection = null;

        try
        {
            connection = getConnection();
            connection.setAutoCommit(false);
            consumer.accept(connection);
            connection.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                if (connection != null) connection.rollback();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (connection != null) connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    public <T> T connectQuery(Function<Connection, T> query)
    {
        Connection connection = null;

        try
        {
            connection = getConnection();
            return query.apply(connection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                if (connection != null) connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void connectQuery(Consumer<Connection> query)
    {
        Connection connection = null;

        try
        {
            connection = getConnection();
            query.accept(connection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (connection != null) connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
