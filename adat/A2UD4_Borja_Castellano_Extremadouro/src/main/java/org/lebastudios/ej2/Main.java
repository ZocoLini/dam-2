package org.lebastudios.ej2;

import org.hibernate.Session;
import org.lebastudios.Database;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Session session = Database.getInstance().getSession();

        List<Object[]> results = session.createQuery("""
                select d.numDepartamento, d.nomeDepartamento, d.empregados.size as num_empleados
                from Departamento d
                order by num_empleados desc
                """, Object[].class).list();

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        System.out.printf("%-10s %-30s %-15s%n",
                "Número", "Nombre", "Núm. de empleados");
        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        for (Object[] row : results)
        {
            System.out.printf(
                    "%-10s %-30s %-15s%n",
                    row[0], row[1], row[2]
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

    }
}
