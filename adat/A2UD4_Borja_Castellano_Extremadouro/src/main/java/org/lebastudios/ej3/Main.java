package org.lebastudios.ej3;

import org.hibernate.Session;
import org.lebastudios.Database;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Session session = Database.getInstance().getSession();

        List<Object[]> results = session.createQuery("""
                SELECT d.empregados.size, d.nomeDepartamento, COUNT(e.id)
                FROM Departamento d
                LEFT JOIN d.empregados e
                GROUP BY d.numDepartamento, d.nomeDepartamento
                ORDER BY COUNT(e.id) DESC
                """, Object[].class).list();
        for (Object[] row : results)
        {
            System.out.printf(
                    "NSS:%-9s %-30s %d\n",
                    row[0], row[1], row[2]
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

    }
}
