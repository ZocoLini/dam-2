package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;
import org.lebastudios.examen.models.FotografoDAO;

public class Ej2
{
    public static void main(String[] args)
    {
        Database.getInstance().init();

        Database.getInstance().connectTransaction(session ->
        {
            FotografoDAO.eliminarMaterial(1, "CAM-1001", session);
        });
        
        Database.getInstance().close();
    }
}
