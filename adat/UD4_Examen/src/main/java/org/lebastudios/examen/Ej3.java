package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;
import org.lebastudios.examen.models.EventoDAO;

public class Ej3
{
    public static void main(String[] args)
    {
        Database.getInstance().init();
        
        String pseudonimoFotografo = "Carlios";
        int idEvento = 4;

        EventoDAO.suscribirFotogrado(pseudonimoFotografo, idEvento);
        
        Database.getInstance().close();
    }
}
