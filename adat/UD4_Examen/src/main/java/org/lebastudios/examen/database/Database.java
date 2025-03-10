package org.lebastudios.examen.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class Database
{
    private static Database instance;
    private SessionFactory sessionFactory;

    private Database() {}

    public void init()
    {
        if (sessionFactory != null) return;

        sessionFactory = getInstance().buildSessionFactory();
    }

    public void close()
    {
        if (sessionFactory == null) return;

        sessionFactory.close();
        sessionFactory = null;
    }
    
    public static Database getInstance()
    {
        if (instance == null) instance = new Database();

        return instance;
    }

    private SessionFactory buildSessionFactory()
    {
        try
        {
            Configuration configuration = new Configuration().configure(); 
            return configuration.buildSessionFactory(); // Usar la misma configuración para construir la SessionFactory
        }
        catch (Throwable ex)
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession() { return sessionFactory.openSession(); }
    
    // Inicia una transaccion, ejecuta una interfaz funcional que use la session y posteriormente 
    // hace commit o rollback si no se ha hecho ya
    public void connectTransaction(Consumer<Session> action)
    {
        if (sessionFactory == null) throw new IllegalStateException("Database not initialized");

        Session session = sessionFactory.openSession();

        try
        {
            session.getTransaction().begin();

            action.accept(session);

            if (session.getTransaction().isActive())
            {
                session.getTransaction().commit();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (session.getTransaction().isActive())
            {
                session.getTransaction().rollback();
            }
        }
        finally
        {
            session.close();
        }
    }

    public void connectQuery(Consumer<Session> action)
    {
        if (sessionFactory == null) throw new IllegalStateException("Database not initialized");

        Session session = sessionFactory.openSession();
        
        try 
        {
            action.accept(session);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }
}
