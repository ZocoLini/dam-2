package org.lebastudios.EJ1_A5UD3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        String db = args[0];
        String user = args[1];
        String pass = args[2];

        var consulta = Arrays.copyOfRange(args, 3, args.length);

        var consultas = String.join(" ", consulta).split(";");

        try
        {
            Connection con = DriverManager.getConnection(
                    String.format("jdbc:sqlserver://10.0.2.4\\MV-PROG:1433;database=%s;trustServerCertificate=true",
                            db), 
                    user, pass
            );

            Statement statement = con.createStatement();

            for (String s : consultas)
            {
                statement.addBatch(s);
            }
            
            statement.executeBatch();
            statement.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}