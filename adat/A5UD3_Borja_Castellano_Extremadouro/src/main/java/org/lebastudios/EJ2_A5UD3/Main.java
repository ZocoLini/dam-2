package org.lebastudios.EJ2_A5UD3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main
{
    private static final String DB_NAME = "BDEmpresa";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "abc123.";

    public static void main(String[] args)
    {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://10.0.2.15:3306",
                DB_USER, DB_PASS))
        {
            System.out.println("lhbjvsa");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
