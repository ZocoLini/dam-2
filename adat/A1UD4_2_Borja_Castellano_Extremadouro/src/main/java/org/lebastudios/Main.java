package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lebastudios.entities.*;

import java.time.LocalDate;
import java.util.*;

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

        Map<String, String> telefonos = Map.of("98112356", "Num1", "91654321", "Num2");
        Familiar familiar = new Familiar(
                "sdfgsdfg",
                "PEpe",
                "JIJI",
                "Pepe",
                "Pepe",
                new Date(LocalDate.now().toEpochDay()),
                'M'
        );
        Empregado empregado = new Empregado(
                "PEpe",
                "Juanillo",
                "Chico",
                "77482884R",
                1.234f,
                new Date(1234556677),
                'H',
                telefonos,
                List.of(familiar),
                List.of(),
                List.of()
        );
        
        session.persist(empregado);
        
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
