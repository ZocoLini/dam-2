package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database
{
    private static Database instance;
    private final SessionFactory sessionFactory;

    private Database()
    {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Database getInstance()
    {
        if (instance == null) instance = new Database();

        return instance;
    }

    public Session getSession()
    {
        return sessionFactory.openSession();
    }
}
