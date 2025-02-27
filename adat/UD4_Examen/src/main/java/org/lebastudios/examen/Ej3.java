package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;
import org.lebastudios.examen.models.EventoDAO;

public class Ej3
{
    public static void main(String[] args)
    {
        Database.getInstance().init();
        
        String pseudonimoFotografo = "Carlios";
        int idEvento = 8;
        
        Database.getInstance().connectTransaction(session ->
        {
            EventoDAO.suscribirFotografo(pseudonimoFotografo, idEvento, session);
        });
        
        Database.getInstance().close();
    }
}
