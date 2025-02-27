package org.lebastudios.examen.models;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.lebastudios.examen.database.Database;

public class EventoDAO
{
    public static void suscribirFotogrado(String pseudonimo, int codEvento)
    {
        Session session = Database.getInstance().getSession();

        Evento evento = (Evento) session.get(Evento.class, codEvento);

        boolean found = false;
        for (var fotografo : evento.getFotografos())
        {
            if (fotografo.getSeudonimo().equals(pseudonimo))
            {
                System.out.printf("El fotografo %s ya se encuentra en el evento %s\n", pseudonimo,
                        evento.getNombreEvento());

                found = true;
                break;
            }
        }

        if (found) return;

        Fotografo fotografo = FotografoDAO.getByPseudonimo(pseudonimo, session);

        if (fotografo == null)
        {
            System.out.println("No existe ningun fotografo con pseudonimo " + pseudonimo);
            return;
        }

        try
        {
            session.beginTransaction();

            evento.getFotografos().add(fotografo);
            
            session.getTransaction().commit();
            System.out.printf(
                    "Se ha insertado el fotografo con pseudonimo %s en el evento con id %d\n", pseudonimo, codEvento
            );
        }
        catch (HibernateException exception)
        {
            System.err.println("no se ha podido insertar el fotografo en el evento");
            session.getTransaction().rollback();
        }
        
        session.close();
    }
}
