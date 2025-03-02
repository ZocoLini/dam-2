package org.lebastudios.ej6;

import org.hibernate.Session;
import org.lebastudios.Database;
import org.lebastudios.entities.Empregado;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Session session = Database.getInstance().getSession();

        List<Object[]> results = session.createQuery("SELECT d.director.nss, " +
                "       CONCAT(s.nome, ' ', s.apelido1), " +
                "       CONCAT(d.director.nome, ' ', d.director.apelido1), " +
                "       d.director.salario, " +
                "       d.nomeDepartamento " +
                "FROM Departamento d " +
                "LEFT JOIN d.director.supervisor s " +
                "ORDER BY d.director.salario, d.director.nss", Object[].class).list();
        
        for (Object[] row : results)
        {
            System.out.printf(
                    "NSS:%-9s %-30s %-30s %.00f %-30s",
                    row[0], row[1], row[2], row[3], row[4]
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

    }
}
