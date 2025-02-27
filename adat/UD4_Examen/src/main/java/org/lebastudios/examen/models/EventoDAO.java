package org.lebastudios.examen.models;

import org.lebastudios.examen.database.Database;

public class EventoDAO
{
    public static void suscribirFotogrado(String pseudonimo, int codEvento)
    {
        boolean encontrado = Database.getInstance().connectQuery(session ->
        {
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

            return found;
        });

        if (encontrado) return;

        Database.getInstance().connectTransaction(session ->
        {
            Evento evento = (Evento) session.get(Evento.class, codEvento);
            Fotografo fotografo = FotografoDAO.getByPseudonimo(pseudonimo, session);

            if (fotografo == null)
            {
                System.out.println("No existe ningun fotografo con pseudonimo " + pseudonimo);
                session.getTransaction().rollback();
                return;
            }

            evento.getFotografos().add(fotografo);

            System.out.printf(
                    "Se ha insertado el fotografo con pseudonimo %s en el evento con id %d\n", pseudonimo, codEvento
            );
        });
    }
}
