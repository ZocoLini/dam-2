package org.lebastudios;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "abc123.");
            System.out.println("Conectado a MySQL");
            DriverManager.getConnection("jdbc:sqlite:dbempresa.sqlite");
            System.out.println("Conectado a SQLite");
            DriverManager.getConnection("jdbc:sqlserver://localhost\\MV-PROG:1433;trustServerCertificate=true", "sa", 
                    "abc123.");
            System.out.println("Conectado a SQLServer");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}