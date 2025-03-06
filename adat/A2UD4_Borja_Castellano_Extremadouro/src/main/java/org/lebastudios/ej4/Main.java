package org.lebastudios.ej4;

import org.hibernate.Session;
import org.lebastudios.Database;
import org.lebastudios.entities.Empregado;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Session session = Database.getInstance().getSession();

        List<Empregado> results = session.createQuery("""
                FROM Empregado e
                WHERE e.telefonos.size = 0
                """, Empregado.class).list();
        for (Empregado row : results)
        {
            System.out.printf(
                    "NSS:%-9s %-30s \n",
                    row.getNss(), row.getNome()
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

    }
}
