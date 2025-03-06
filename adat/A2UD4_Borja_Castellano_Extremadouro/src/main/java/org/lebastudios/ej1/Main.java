package org.lebastudios.ej1;

import org.hibernate.Session;
import org.lebastudios.Database;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Session session = Database.getInstance().getSession();
        List<Object[]> results = session.createQuery("""
                select e.nss, concat(e.apelido1, ' ', isnull (e.apelido2,''), ', ', e.nome) as nome_completo, e.departamento.nomeDepartamento,
                case type(e)
                    when Empregadofixo then 'fijo'
                    when Empregadotemporal then 'temporal'
                    end as tipo_empregado,
                e.telefonos.size
                from Empregado e
                order by e.apelido1, e.apelido2, e.nome
                """, Object[].class).list();

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        System.out.printf("%-10s %-30s %-15s %-10s %-5s%n", "NSS", "Nombre completo", "Departamento", "Tipo",
                "Num. de teléfonos");
        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

        results.forEach(row -> System.out.printf(
                "%-10s %-30s %-15s %-10s %-5s%n",
                row[0], row[1], row[2], row[3], row[4])
        );

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        results = session.createQuery("""
                select e.nss, concat(e.apelido1, ' ', isnull (e.apelido2,''), ', ', e.nome) as nome_completo, e.departamento.nomeDepartamento,
                       case type(e)
                           when Empregadofixo then 'fijo'
                           when Empregadotemporal then 'temporal'
                           end as tipo_empregado,
                       e.telefonos.size
                from Empregado e """
                + " where e.class in (Empregadofixo)"
                + "order by e.apelido1, e.apelido2, e.nome", Object[].class).list();
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s %-10s %-5s%n",
                "NSS", "Nombre completo", "Departamento", "Tipo", "Num. de teléfonos");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        for (Object[] row : results)
        {
            System.out.printf("%-10s %-30s %-15s %-10s %-5s%n",
                    row[0], row[1], row[2], row[3], row[4]);
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------");
        results = session.createQuery("""
                select e.nss, concat(e.apelido1, ' ', isnull (e.apelido2,''), ', ', e.nome) as nome_completo, e.departamento.nomeDepartamento, 
                e.dataNacemento
                from Empregado e """
                + " where year(e.dataNacemento) > (2000)"
                + "order by e.apelido1, e.apelido2, e.nome", Object[].class).list();
        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        System.out.printf("%-10s %-30s %-15s %-10s%n",
                "NSS", "Nombre completo", "Departamento", "Fecha Nacimiento");
        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
        for (Object[] row : results)
        {
            System.out.printf(
                    "%-10s %-30s %-15s %-10s%n",
                    row[0], row[1], row[2], row[3]
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );
    }
}
