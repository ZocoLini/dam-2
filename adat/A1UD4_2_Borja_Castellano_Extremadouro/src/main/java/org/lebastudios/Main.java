package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lebastudios.entities.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration().configure();
        
        // configuration.addAnnotatedClass(Empregado.class);
        // configuration.addAnnotatedClass(Proxecto.class);
        // configuration.addAnnotatedClass(Departamento.class);
        
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
        
        TreeMap<Date, Float> horasExtras = new TreeMap<>(
                Map.of(
                        Date.valueOf(LocalDate.now().minusDays(1)),
                        1.2f,
                        Date.valueOf(LocalDate.now().minusDays(2)),
                        2.3f
                )
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
                horasExtras
        );
        
        session.persist(empregado);
        
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
