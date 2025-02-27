package org.lebastudios.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
            var config = new Configuration().configure();

            return config.buildSessionFactory();
        }
        catch (Throwable ex)
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void connectTransaction(Consumer<Session> action)
    {
        connectTransactionWithBool(action);
    }
    
    public boolean connectTransactionWithBool(Consumer<Session> action)
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
            return false;
        }
        finally
        {
            session.close();
        }
        
        return true;
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

    public <R> R connectQuery(Function<Session, R> action)
    {
        if (sessionFactory == null) throw new IllegalStateException("Database not initialized");

        Session session = sessionFactory.openSession();
        
        // Ejemplo de uso de la sesión para interactuar con la base de datos
        try 
        {
            return action.apply(session);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            session.close();
        }
    }
}
