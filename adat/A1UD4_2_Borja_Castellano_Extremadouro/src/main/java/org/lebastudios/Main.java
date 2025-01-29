package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lebastudios.entities.Departamento;
import org.lebastudios.entities.Empregado;

import java.util.Date;
import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Set<String> telefonos = Set.of("981123456", "981654321");
        Empregado empregado = new Empregado(
                "PEpe",
                "Juanillo",
                "Chico",
                "77482884R",
                1.234f,
                new Date(1234556677),
                'H',
                telefonos
        );
        
        session.persist(empregado);
        
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
