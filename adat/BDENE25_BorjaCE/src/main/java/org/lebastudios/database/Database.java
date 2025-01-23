package org.lebastudios.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class Database
{
    private static final String DB_URL =
            "jdbc:sqlserver://localhost:1433;database=BDALOJAMIENTOS25;trustServerCertificate=true";
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

    /// Este metodo crea una onexion que pone en modo de commit manual y ejecuta la interfaz funcional que se le pasa 
    /// por parametro pasandole esa misma conexion. Si la interfaz funcional se ejecuta sin problemas se hará commit 
    /// a la conexion. En caso de que ocurra una excepción se hara rollback de la transaccion. Finalmente se cerrará la
    /// conexión abierta
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
                if (connection != null) {connection.rollback();}
                else { System.err.println("No se ha podido establecer la conexion con la base de datos"); }
            }
            catch (Exception ex)
            {
                System.err.println("Error al realizar el rollback. Probablemente se ha cortado la conexion con la BD");
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
                System.err.println("Error al realizar el rollback. Probablemente se ha cortado la conexion con la BD");
                e.printStackTrace();
            }
        }

        return false;
    }
}
