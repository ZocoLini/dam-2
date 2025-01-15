package org.lebastudios;

import lombok.SneakyThrows;
import org.lebastudios.examples.Lugar;
import org.lebastudios.sqlx.SQLx;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main
{
    @SneakyThrows
    public static void main(String[] args)
    {
        Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;database=BDEmpresa;trustServerCertificate=true",
                "sa",
                "abc123."
        );

        var lugar = SQLx.queryAll("select * from LUGAR", conn, Lugar.class);
        
        lugar.forEach(lugar1 ->
        {
            System.out.println(lugar1.getNomeLugar());
            System.out.println(lugar1.getNumDepartamento());
            System.out.println();
        });
    }
}
