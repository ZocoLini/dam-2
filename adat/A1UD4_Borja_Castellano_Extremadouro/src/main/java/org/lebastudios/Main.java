package org.lebastudios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lebastudios.entities.Departamento;
import org.lebastudios.entities.Empregado;

public class Main
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        insertDepartamento("PrubeDepa", session);
        visualizarEmpleado("77482884R", session);
        cambiarNombreDepartamento(1, "PepeDepa", session);
        //borrarDepartamento(1, session);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    private static void insertDepartamento(String nombreDepartamento, Session session)
    {
        Departamento departamento = new Departamento();
        departamento.setNomeDepartamento(nombreDepartamento);
        departamento.setNumDepartamento(1);
        session.save(departamento);
    }

    private static void borrarDepartamento(Integer departamentoId, Session session)
    {
        session.delete(session.get(Departamento.class, departamentoId));
    }
    
    private static void visualizarEmpleado(String nss, Session session)
    {
        Empregado empregado = (Empregado) session.get(Empregado.class, nss);
        if (empregado == null)
        {
            System.out.println("Empregado null");
        }
        else
        {
            System.out.println("Empregado: " + empregado.getNome());
        }
    }

    private static void cambiarNombreDepartamento(Integer departamentoId, String nuevoNombre, Session session)
    {
        Departamento departamento = (Departamento) session.get(Departamento.class, departamentoId);
        departamento.setNomeDepartamento(nuevoNombre);
    }
}
