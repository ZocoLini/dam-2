/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author usuario
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Fallo al crear la conexión" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
