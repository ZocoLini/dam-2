package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lebastudios.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration().configure();
        
        configuration.addAnnotatedClass(Empregado.class);
        configuration.addAnnotatedClass(Proxecto.class);
        configuration.addAnnotatedClass(Departamento.class);
        
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Map<String, String> telefonos = Map.of("981123456", "Num1", "981654321", "Num2");
        Empregado empregado = new Empregado(
                "PEpe",
                "Juanillo",
                "Chico",
                "77482884R",
                1.234f,
                new Date(1234556677),
                'H',
                telefonos,
                new ArrayList<>()
        );
        
        session.persist(empregado);
        
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
