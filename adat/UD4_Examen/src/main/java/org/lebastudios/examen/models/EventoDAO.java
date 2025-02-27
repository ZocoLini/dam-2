package org.lebastudios.examen.models;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.lebastudios.examen.database.Database;

public class EventoDAO
{
    public static void suscribirFotografo(String pseudonimo, int codEvento, Session session)
    {
        // Obtenemos 0 si el fotografo no se encuentra en el evento o 1 si si se encuentra
        long count = (Long) Database.getInstance().getSession().createQuery(
                        "select count(*) from Evento e join e.fotografos f " +
                                "where f.seudonimo = :pseudonimo and e.idEvento = :codEvento"
                ).setString("pseudonimo", pseudonimo)
                .setInteger("codEvento", codEvento)
                .uniqueResult();

        if (count != 0)
        {
            System.out.printf("El fotografo %s ya se encuentra en el evento con codigo %d\n", pseudonimo,
                    codEvento);
            return;
        }

        Fotografo fotografo = FotografoDAO.getByPseudonimo(pseudonimo, session);
        Evento evento = (Evento) session.get(Evento.class, codEvento);

        if (fotografo == null)
        {
            System.out.println("No existe ningun fotografo con pseudonimo " + pseudonimo);
            return;
        }
        
        try
        {
            // Añadimos el fotografo al evento. El commit se ejecuta automaticamente
            evento.getFotografos().add(fotografo);
            System.out.printf(
                    "Se ha insertado el fotografo con pseudonimo %s en el evento con id %d\n", pseudonimo, codEvento
            );
        }
        catch (HibernateException exception)
        {
            System.err.println("no se ha podido insertar el fotografo en el evento");
            session.getTransaction().rollback();
        }
    }
}
